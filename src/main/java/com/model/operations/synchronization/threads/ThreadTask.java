package com.model.operations.synchronization.threads;

import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;

public class ThreadTask implements Runnable {

    public DenseMatrix matrix2;
    public DenseMatrix matrix1;
    public DenseMatrixBuilder builder;
    public int row;


    public ThreadTask(DenseMatrix matrix1, DenseMatrix matrix2, int row, DenseMatrixBuilder builder) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.builder = builder;
        this.row = row;
    }

    @Override
    public void run() {
        for (int i = 0; i < matrix1.size(); i++) {
            double sum = 0.0;
            for (int j = 0; j < this.matrix2.size(); j++) {
                sum += this.matrix1.get(row, j) * this.matrix2.get(j, i);
            }
            this.builder.set(this.row, i, sum);
        }
    }
}
