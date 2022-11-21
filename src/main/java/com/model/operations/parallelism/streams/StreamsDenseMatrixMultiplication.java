package com.model.operations.parallelism.streams;

import com.model.MatrixMultiplication;
import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;

import java.util.stream.IntStream;

public class StreamsDenseMatrixMultiplication implements MatrixMultiplication <DenseMatrix, DenseMatrix, DenseMatrix> {

    @Override
    public DenseMatrix multiply(DenseMatrix matrix1, DenseMatrix matrix2) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(matrix1.size(), matrix1.size());

        double[][] matrix1_ = matrix1.getMatrix();
        double[][] matrix2_ = matrix2.getMatrix();
        IntStream.range(0, matrix2.size()).parallel()
                .forEach(row -> IntStream.range(0, matrix2.size())
                        .forEach(col -> builder.set(row, col, IntStream.range(0, matrix2.size())
                                .mapToDouble(j -> matrix1_[row][j] * matrix2_[col][j]).sum())));
        return builder.toMatrix();
    }

}
