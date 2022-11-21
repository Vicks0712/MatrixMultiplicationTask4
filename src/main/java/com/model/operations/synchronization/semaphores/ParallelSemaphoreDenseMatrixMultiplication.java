package com.model.operations.synchronization.semaphores;

import com.model.MatrixMultiplication;
import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ParallelSemaphoreDenseMatrixMultiplication implements MatrixMultiplication<DenseMatrix, DenseMatrix, DenseMatrix> {
    int availableThreads = Runtime.getRuntime().availableProcessors();

    ExecutorService executorService = Executors.newFixedThreadPool(availableThreads);

    Semaphore semaphore = new Semaphore(1);

    @Override
    public DenseMatrix multiply(DenseMatrix matrix1, DenseMatrix matrix2) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(matrix1.size(), matrix2.size());
        for (int i = 0; i < matrix1.size(); i++) submit(matrix1, matrix2, i, builder);
        executorService.shutdown();
        try {
            executorService.awaitTermination(50, TimeUnit.SECONDS);
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
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                builder.set(i, k, sum);
                semaphore.release();
            }
        });
    }
}
