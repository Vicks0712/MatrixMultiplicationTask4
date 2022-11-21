package com.model.operations.sequential;

import com.model.MatrixMultiplication;
import com.model.matrixes.DenseMatrix;
import com.model.builders.DenseMatrixBuilder;

public class RowMatrixMultiplication implements MatrixMultiplication<DenseMatrix, DenseMatrix, DenseMatrix> {

    @Override
    public DenseMatrix multiply(DenseMatrix matrix1, DenseMatrix matrix2) {
        DenseMatrixBuilder matrixBuilder = new DenseMatrixBuilder(matrix1.size(), matrix2.size());

        for (int i = 0; i < matrix1.size(); i++) {
            for (int k = 0; k < matrix1.size(); k++) {
                double suma = 0;
                for (int j = 0; j < matrix1.size(); j++) {
                    suma += matrix1.get(i, j) * matrix2.get(j, k);
                }
                matrixBuilder.set(i, k, suma);
            }
        }
        return matrixBuilder.toMatrix();
    }
}
