package com.model.operations.synchronization.threads;

import com.model.MatrixMultiplication;
import com.model.builders.DenseMatrixBuilder;
import com.model.matrixes.DenseMatrix;

import java.util.LinkedList;
import java.util.List;

public class ParallelMatrixMultiplication implements MatrixMultiplication<DenseMatrix, DenseMatrix, DenseMatrix> {

    public DenseMatrix multiply(DenseMatrix matrix1, DenseMatrix matrix2) {

        DenseMatrixBuilder builder = new DenseMatrixBuilder(matrix1.size(), matrix1.size());
        LinkedList<Thread> threads = new LinkedList<Thread>();
        for (int i = 0; i < matrix1.size(); i++) {
            Thread thread = new Thread( new ThreadTask(matrix1,matrix2,i, builder));
            thread.start();
            threads.add(thread);
            if (threads.size() % Runtime.getRuntime().availableProcessors() == 0) {
                waitForThreads(threads);
            }
        }
        return builder.toMatrix();
    }
    private static void waitForThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threads.clear();
    }
}
