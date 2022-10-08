package com.z.part1.lesson14;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantTransferAccount {
    private int balance;
    private final Lock lock = new ReentrantLock();

    //转账
    void transfer(ReentrantTransferAccount target, int amount) {
        while (true) {
            if (this.lock.tryLock()) {
                try {
                    if (target.lock.tryLock()) {
                        try {
                            this.balance -= amount;
                            target.balance += amount;
                            break;
                        } finally {
                            target.lock.unlock();
                        }
                    }
                } finally {
                    this.lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantTransferAccount acc1 = new ReentrantTransferAccount();
        acc1.balance = 10000;

        ReentrantTransferAccount acc2 = new ReentrantTransferAccount();
        acc2.balance = 10000;

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                acc1.transfer(acc2, 10);
            }
            System.out.println(Thread.currentThread().getName());
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                acc2.transfer(acc1, 10);
            }
            System.out.println(Thread.currentThread().getName());
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(acc1.balance);
        System.out.println(acc2.balance);
    }
}
