package com.model.operations.synchronization.blocks;

import com.model.MatrixMultiplication;
import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;



public class SBMatrixMultiplication implements MatrixMultiplication<DenseMatrix, DenseMatrix, DenseMatrix>  {

    public DenseMatrix multiply(DenseMatrix matrix1, DenseMatrix matrix2) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(matrix1.size(), matrix2.size());
        ConcurrencyManager context = new ConcurrencyManager(matrix1.size());
        Runnable task = new BlockTask(context, matrix1, matrix2, builder);
        Thread[] workers = new Thread[Runtime.getRuntime().availableProcessors()];
        new ThreadManager().parallelThreadCreator(workers, task);
        return builder.toMatrix();
    }

}
