package com.z.part1.lesson21;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongIncrease {
    AtomicLong count = new AtomicLong(0);
    Long num = 0L;

    synchronized void increase() {
        num++;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicLongIncrease increase = new AtomicLongIncrease();

        Runnable runnable = () -> {
            for (int i = 0; i < 100000000; i++) {
                increase.count.getAndIncrement();
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        Long atomicNumStartTime = System.currentTimeMillis();

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        Long atomicNumEndTime = System.currentTimeMillis();

        System.out.println("Result: " + increase.count + " Used time: " + (atomicNumEndTime - atomicNumStartTime));

        Runnable runnable2 = () -> {
            for (int i = 0; i < 100000000; i++) {
                increase.increase();
            }
        };

        Thread thread3 = new Thread(runnable2);
        Thread thread4 = new Thread(runnable2);

        Long syncNumStartTime = System.currentTimeMillis();

        thread3.start();
        thread4.start();

        thread3.join();
        thread4.join();

        Long syncNumEndTime = System.currentTimeMillis();

        System.out.println("Result: " + increase.num + " Used time: " + (syncNumEndTime - syncNumStartTime));
    }
}
