package com.model.operations.synchronization.blocks;

public class ThreadManager {

    public ThreadManager parallelThreadCreator(Thread[] threads, Runnable task) {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(task, "Worker-"+i);
        }
        for (int i = 0; i < threads.length; i++) {
            Thread worker = threads[i];
            worker.start();
        }
        for (int i = 0; i < threads.length; i++) {
            Thread worker = threads[i];
            try {
                worker.join();
            } catch (InterruptedException ex) {
            }
        }
        return null;
    }
}
