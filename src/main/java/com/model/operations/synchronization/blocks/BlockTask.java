package com.model.operations.synchronization.blocks;

import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;

public class BlockTask implements Runnable {


    private final ConcurrencyManager context;
    private final DenseMatrix matrix1;
    private final DenseMatrix matrix2;
    private final DenseMatrixBuilder builder;

    public BlockTask(ConcurrencyManager context, DenseMatrix matrix1, DenseMatrix matrix2, DenseMatrixBuilder builder) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null");
        }
        this.context = context;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.builder = builder;
    }

    @Override
    public void run() {
        while (true) {
            int row;
            synchronized (context) {
                if (context.isFullyProcessed()) {
                    break;
                }
                row = context.nextRowNum();
            }
            System.out.println(Thread.currentThread().getName() + " is going to process row " + row);
            for (int j = 0; j < matrix1.size(); j++) {
                double sum = 0.0;
                for (int k = 0; k < matrix2.size(); k++) {
                    sum += matrix1.get(row,k) * matrix2.get(k,j);
                }
                builder.set(row,j, sum);
            }
        }
    }
}
