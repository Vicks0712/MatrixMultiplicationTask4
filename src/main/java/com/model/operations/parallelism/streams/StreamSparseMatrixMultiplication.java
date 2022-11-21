package com.model.operations.parallelism.streams;

import com.model.MatrixMultiplication;
import com.model.builders.SparseMatrixCOOBuilder;
import com.model.matrixes.*;

import java.util.stream.IntStream;

public class StreamSparseMatrixMultiplication implements MatrixMultiplication<SparseMatrixCOO, SparseMatrixCRS, SparseMatrixCCS> {
    @Override
    public SparseMatrixCOO multiply(SparseMatrixCRS matrix1, SparseMatrixCCS matrix2) {
        SparseMatrixCOOBuilder builder = new SparseMatrixCOOBuilder(matrix1.size());
        IntStream.range(0, matrix1.size() * matrix2.size())
                .parallel().forEach(id -> {
                    int rowA = id / matrix2.size();
                    int colB = id % matrix2.size();

                    double sum = 0.0;
                    for (int i = matrix1.rowPointers[rowA]; i < matrix1.rowPointers[rowA+1]; i++) {
                        for (int j = matrix2.colPointers[colB]; j < matrix2.colPointers[colB+1]; j++) {
                            if (matrix1.columns.get(i) == matrix2.rows.get(j)) {
                                sum += matrix1.values.get(i) * matrix2.values.get(j);
                                break;
                            }
                        }
                    }
                    if (sum != 0) {
                        builder.set(rowA,colB,sum);
                    }
                });
        return builder.toMatrix();
    }
}

