package com.z.part1.practice.elevator.utilclass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {
    private static final StampedLock stampedLock = new StampedLock();
    private static List<Integer> numbers = new ArrayList<>();

    public static void main(String[] args) {
        StampedLockTest test = new StampedLockTest();
        ExecutorService pool = Executors.newFixedThreadPool(3);
        Runnable runnable = () -> {
            int action = new Random().nextInt(10);
            //读多写少
            if (action == 0) {
                int number = new Random().nextInt(100);
                test.addNumber(number);
            } else if (action == 1) {
                test.removeNumber();
            } else {
                test.readNumber();
            }
        };

        for (int i = 0; i < 10; i++) {
            test.addNumber(i + 1);
        }

        for (int i = 0; i < 100; i++) {
            pool.execute(runnable);
        }
    }

    void addNumber(Integer number) {
        long stamp = stampedLock.writeLock();
        try {
            System.out.println("准备添加元素, 当前列表: " + numbers + " 计划添加值: " + number);
            numbers.add(number);
            System.out.println("添加元素成功, 当前列表: " + numbers + "计划添加值: " + number);
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    void removeNumber() {
        long stamp = stampedLock.writeLock();
        try {
            if (numbers.isEmpty()) {
                System.out.println("当前列表为空，无法移除数字");
                return;
            }
            System.out.println("准备移除元素, 当前列表: " + numbers);
            int removed = numbers.remove(numbers.size() - 1);
            System.out.println("移除元素成功, 当前列表: " + numbers + " 被移除元素：" + removed);
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    void readNumber() {
        try {
            long stamp = stampedLock.tryOptimisticRead();
            if (numbers.isEmpty()) {
                System.out.println("当前列表为空，无法读取数字");
                return;
            }
            int index = new Random().nextInt(0, numbers.size());
            System.out.println("准备读取元素, 当前列表: " + numbers + "计划读取下标: " + index);
            int read = numbers.get(numbers.size() - 1);
            TimeUnit.SECONDS.sleep(3);
            if (stampedLock.validate(stamp)){
                System.out.println("读取元素成功, 当前列表: " + numbers + " 读取到元素: " + read);
            } else {
                System.out.println("乐观读失败，开始悲观读");
                long readLockStamp = stampedLock.readLock();
                try {
                    if (numbers.isEmpty()) {
                        System.out.println("当前列表已经为空，无法读取数字");
                        return;
                    }
                    int lockIndex = new Random().nextInt(0, numbers.size());
                    System.out.println("准备悲观读元素, 当前列表: " + numbers + "计划读取下标: " + lockIndex);
                    int lockRead = numbers.get(lockIndex);
                    System.out.println("悲观读元素成功, 当前列表: " + numbers + " 读取到元素: " + lockRead);
                } finally {
                    stampedLock.unlockRead(readLockStamp);
                }



            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
