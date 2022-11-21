package com.model.operations.sequential;

import com.model.MatrixMultiplication;
import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;

public class TransposedMatrixMultiplication implements MatrixMultiplication<DenseMatrix, DenseMatrix, DenseMatrix> {
    @Override
    public DenseMatrix multiply(DenseMatrix matrix1, DenseMatrix matrix2) {

        DenseMatrixBuilder builder = new DenseMatrixBuilder(matrix1.size(), matrix2.size());

        double[][] transposed = transpose(matrix2);

        for (int i = 0; i < matrix1.size(); i++) {
            for (int j = 0; j < matrix1.size(); j++) {
                double sum = 0.0;
                for (int k = 0; k < matrix1.size(); k++) {
                    sum += matrix1.get(i,k) * transposed[j][k];
                }
            }
        }
        return builder.toMatrix();
    }

    private static double[][] transpose(DenseMatrix matrix2) {
        double[][] transposed = new double[matrix2.size()][matrix2.size()];
        for (int i = 0; i < matrix2.size(); i++) {
            for (int j = 0; j < matrix2.size(); j++) {
                transposed[j][i] = matrix2.get(i,j);
            }
        }
        return transposed;
    }
}