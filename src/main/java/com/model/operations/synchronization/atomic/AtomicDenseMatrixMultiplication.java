package com.model.operations.synchronization.atomic;

import com.google.common.util.concurrent.AtomicDouble;
import com.model.MatrixMultiplication;
import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AtomicDenseMatrixMultiplication implements MatrixMultiplication<DenseMatrix, DenseMatrix, DenseMatrix> {

    int availableThreads = Runtime.getRuntime().availableProcessors();

    ExecutorService executorService = Executors.newFixedThreadPool(availableThreads);

    @Override
    public DenseMatrix multiply(DenseMatrix matrix1, DenseMatrix matrix2) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(matrix1.size(), matrix1.size());
        AtomicDouble[][] atomicDoubles = new AtomicDenseMatrix().createEmptyAtomicDenseMatrix(matrix1.size());
        for (int i = 0; i < matrix1.size(); i++) submit(matrix1, matrix2, i, builder, atomicDoubles);
        executorService.shutdown();
        try {
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return builder.toMatrix();
    }

    private void submit(DenseMatrix matrix1, DenseMatrix matrix2, int i, DenseMatrixBuilder builder, AtomicDouble[][] atomicDoubles) {
        executorService.submit(() -> {
            for (int k = 0; k < matrix1.size(); k++) {
                double sum = 0.0;
                for (int j = 0; j < matrix1.size(); j++) {
                    atomicDoubles[i][k].set(matrix1.get(i,j)*matrix2.get(j,k));
                }
                builder.set(i,k,sum);
            }
        });
    }
}
