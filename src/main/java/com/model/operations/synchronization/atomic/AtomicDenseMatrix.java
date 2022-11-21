package com.model.operations.synchronization.atomic;

import com.google.common.util.concurrent.AtomicDouble;

public class AtomicDenseMatrix {
    public AtomicDouble[][] createEmptyAtomicDenseMatrix(int size) {
        AtomicDouble[][] atomicDoubles = new AtomicDouble[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                atomicDoubles[i][j] = new AtomicDouble();
        return atomicDoubles;
    }
}
