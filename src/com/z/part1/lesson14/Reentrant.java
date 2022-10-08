package com.z.part1.lesson14;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Reentrant {
    private final Lock rtl = new ReentrantLock();

    int value;

    public int get() {
        //获取锁
        rtl.lock();
        try {
            return value;
        } finally {
            //保证锁能释放
            rtl.unlock();
        }
    }

    public void addOne() {
        //获取锁
        rtl.lock();
        try {
            value = 1 + get();
        } finally {
            //保证锁能释放
            rtl.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Reentrant reentrant = new Reentrant();
        reentrant.value = 0;

        Runnable runnable = () -> {
            for (int i = 0; i < 1000000; i++) {
                reentrant.addOne();
                System.out.println(Thread.currentThread().getName());
            }
        };

        Thread thread1 = new Thread(runnable, "Thread1");
        Thread thread2 = new Thread(runnable, "Thread2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(reentrant.value);
    }
}
