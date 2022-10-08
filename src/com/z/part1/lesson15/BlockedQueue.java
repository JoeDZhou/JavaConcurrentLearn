package com.z.part1.lesson15;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockedQueue<T> {
    final Lock lock = new ReentrantLock();

    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    public final static Integer MAX_LENGTH = 20;

    List<T> elementList = new ArrayList<>();

    void enq(T x) throws InterruptedException {
        lock.lock();
        try {
            while (elementList.size() == MAX_LENGTH) {
                notFull.await();
            }
            elementList.add(x);
            System.out.println("Enq: " + x);
            if (elementList.size() > 15) {
                notEmpty.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    void deq() throws InterruptedException {
        lock.lock();
        try {
            while (elementList.isEmpty()){
                notEmpty.await();
            }
            Double ele = (Double) elementList.remove(elementList.size() - 1);
            System.out.println("Deq: " + ele);
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }

    Integer size() {
        return elementList.size();
    }

    @Override
    public String toString() {
        return "BlockedQueue{" +
                "elementList=" + elementList +
                '}';
    }

    public static void main(String[] args) throws InterruptedException {
        BlockedQueue<Double> doubles = new BlockedQueue<>();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    doubles.enq(Math.random());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    doubles.deq();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Finished");
        System.out.println("Size: " + doubles.size());
        System.out.println("Result: " + doubles);
    }
}
