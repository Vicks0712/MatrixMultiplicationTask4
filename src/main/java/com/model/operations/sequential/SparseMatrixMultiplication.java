package com.model.operations.sequential;

import com.model.MatrixMultiplication;
import com.model.builders.SparseMatrixCOOBuilder;
import com.model.matrixes.*;
import com.model.matrixes.SparseMatrixCOO;

public class SparseMatrixMultiplication implements MatrixMultiplication<SparseMatrixCOO, SparseMatrixCRS, SparseMatrixCCS> {

    @Override
    public SparseMatrixCOO multiply(SparseMatrixCRS matrix1, SparseMatrixCCS matrix2) {
        SparseMatrixCOOBuilder builder = new SparseMatrixCOOBuilder(matrix1.size);
        for (int i = 0; i < matrix1.size; i++) {
            double sum = 0.0;
            int rowA = i / matrix2.size;
            int colB = i % matrix2.size;
            for (int k = matrix1.rowPointers[rowA]; k < matrix1.rowPointers[rowA + 1]; k++) {
                for (int j = matrix2.colPointers[colB]; j < matrix2.colPointers[colB + 1]; j++) {
                    if (matrix1.columns.get(k)  == matrix2.rows.get(j)) {
                        sum += matrix1.values.get(k) * matrix2.values.get(j);
                        break;
                    }
                }
            }
            if (sum != 0) {
                builder.set(rowA, colB, sum);
            }
        }
        return builder.toMatrix();
    }
}
