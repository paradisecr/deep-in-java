package me.rui.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by cr on 2017/3/24.
 */
public class Latch {

    public static void main(String args[]) {
        Runnable task = new Task();
        Latch latch = new Latch();
        try {
            System.out.println(latch.timeTasks(5, task));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException{
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i =0; i< nThreads; i++) {
            Thread t = new Thread(){
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {}
                }
            };
            t.start();
        }
        long startTime = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    static class Task implements Runnable {
        public void run() {
            System.out.println("New task working  ....");
        }
    }
}
