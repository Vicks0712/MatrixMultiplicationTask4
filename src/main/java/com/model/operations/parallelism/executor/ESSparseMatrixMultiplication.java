package com.model.operations.parallelism.executor;

import com.model.MatrixMultiplication;
import com.model.builders.SparseMatrixCOOBuilder;
import com.model.matrixes.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ESSparseMatrixMultiplication implements MatrixMultiplication<SparseMatrixCOO, SparseMatrixCRS, SparseMatrixCCS> {
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public SparseMatrixCOO multiply(SparseMatrixCRS matrix1, SparseMatrixCCS matrix2) {
        SparseMatrixCOOBuilder builder = new SparseMatrixCOOBuilder(matrix1.size());
        for (int i = 0; i < matrix1.size(); i++) submit(matrix1, matrix2, i, builder);
        executorService.shutdown();
        try {
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return builder.toMatrix();    }

    private void submit(SparseMatrixCRS matrix1, SparseMatrixCCS matrix2, int i, SparseMatrixCOOBuilder builder) {
        executorService.submit(() -> {
            double sum = 0.0;
            int rowA = i / matrix2.size();
            int colB = i % matrix2.size();
            for (int k = matrix1.rowPointers[rowA]; k < matrix1.rowPointers[rowA+1]; k++) {
                for (int j = matrix2.colPointers[colB]; j < matrix2.colPointers[colB+1]; j++) {
                    if (matrix1.columns.get(k) == matrix2.rows.get(j)) {
                        sum += matrix1.values.get(k) * matrix2.values.get(j);
                        break;
                    }
                }
            }
            if (sum != 0) {
                builder.set(rowA,colB,sum);
            }
        });
    }



}
