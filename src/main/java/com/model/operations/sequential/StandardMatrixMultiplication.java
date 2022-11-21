package com.model.operations.sequential;

import com.model.MatrixMultiplication;
import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;

public class StandardMatrixMultiplication implements MatrixMultiplication<DenseMatrix, DenseMatrix, DenseMatrix> {


    public DenseMatrix multiply(DenseMatrix matrix1, DenseMatrix matrix2) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(matrix1.size(), matrix1.size());
        for (int i = 0; i < matrix1.size(); i++) {
            for (int j = 0; j < matrix1.size(); j++) {
                double sum = 0;
                for (int k = 0; k < matrix1.size(); k++) {
                    sum += matrix1.get(i, k) * matrix2.get(k, j);
                }
                builder.set(i, j, sum);
            }
        }
        return builder.toMatrix();
    }
}
