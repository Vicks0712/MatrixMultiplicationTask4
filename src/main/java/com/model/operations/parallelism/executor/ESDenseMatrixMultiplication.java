package com.model.operations.parallelism.executor;

import com.model.MatrixMultiplication;
import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ESDenseMatrixMultiplication implements MatrixMultiplication<DenseMatrix, DenseMatrix, DenseMatrix> {
    int availableThreads = Runtime.getRuntime().availableProcessors();

    ExecutorService executorService = Executors.newFixedThreadPool(availableThreads);

    public DenseMatrix multiply(DenseMatrix matrix1, DenseMatrix matrix2) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(matrix1.size(), matrix1.size());
        for (int i = 0; i < matrix1.size(); i++) submit(matrix1, matrix2, i, builder);
        executorService.shutdown();
        try {
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return builder.toMatrix();
    }

    private void submit(DenseMatrix matrix1, DenseMatrix matrix2, int i, DenseMatrixBuilder builder) {
        executorService.submit(() -> {
            for (int k = 0; k < matrix1.size(); k++) {
                double sum = 0.0;
                for (int j = 0; j < matrix1.size(); j++) {
                    sum += matrix1.get(i, k) * matrix2.get(k, j);
                }
                builder.set(i,k,sum);
            }
        });
    }
}
