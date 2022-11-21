package com.model.operations.parallelism.streams;

import com.model.MatrixMultiplication;
import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;

import java.util.stream.IntStream;

public class StreamDenseMatrixMultiplication implements MatrixMultiplication <DenseMatrix, DenseMatrix, DenseMatrix>  {

    public DenseMatrix multiply(DenseMatrix matrix1, DenseMatrix matrix2) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(matrix1.size(), matrix1.size());
        IntStream.range(0, matrix2.size()).parallel()
                .forEach(i -> {
                    for (int j = 0; j < matrix2.size(); j++) {
                        double sum = 0.0;
                        for (int k = 0; k < matrix2.size(); k++) {
                            sum += matrix1.get(i, k) * matrix2.get(j, k);
                        }
                        builder.set(i, j, sum);
                    }
                });
        return builder.toMatrix();
    }
}
