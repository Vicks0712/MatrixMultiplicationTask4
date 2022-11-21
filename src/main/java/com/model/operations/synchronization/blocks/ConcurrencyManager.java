package com.model.operations.synchronization.blocks;

public class ConcurrencyManager {
    private final int rowCount;
    private int nextRow = 0;

    public ConcurrencyManager(int rowCount) {
        this.rowCount = rowCount;
    }

    public synchronized int nextRowNum() {
        if (isFullyProcessed()) {
            throw new IllegalStateException("Already fully processed");
        }
        return nextRow++;
    }

    public synchronized boolean isFullyProcessed() {
        return nextRow == rowCount;
    }
}
