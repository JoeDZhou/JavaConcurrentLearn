package com.z.part1.practice.elevator.utilclass;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
    private static final Lock conditionLock = new ReentrantLock();
    private static final Condition theNumberIsTooHigh = conditionLock.newCondition();
    private static final Condition theNumberISTooLow = conditionLock.newCondition();
    private static final Condition handleShakeFinished = conditionLock.newCondition();

    private static Integer number = 100;

    public static void main(String[] args) {
        Runnable tooHighMonitor = () -> {
            while (true) {
                conditionLock.lock();
                try {
                    while (number < 110) {
                        System.out.println("TooHigh检测器监测中");
                        theNumberIsTooHigh.await();
                        System.out.println("TooHigh检测器监测到事件");
                    }
                    System.out.println("监测到数字大于110，开始对其进行恢复，当前：" + number);
                    number -= 5;
                    System.out.println("恢复完毕，当前：" + number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    conditionLock.unlock();
                }
            }
        };

        Runnable tooLowMonitor = () -> {
            while (true) {
                conditionLock.lock();
                try {
                    while (number > 90) {
                        System.out.println("TooLow检测器监测中");
                        theNumberISTooLow.await();
                        System.out.println("TooLow检测器监测到事件");
                    }
                    System.out.println("监测到数字小于90，开始对其进行恢复，当前：" + number);
                    number += 5;
                    System.out.println("恢复完毕，当前：" + number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    conditionLock.unlock();
                }
            }
        };

        Runnable shake = () -> {
            try {
                conditionLock.lock();
                TimeUnit.SECONDS.sleep(new Random().nextInt(1, 5));
                int shakeNumber = new Random().nextInt(-8, 8);
                System.out.println("开始发生抖动, 抖动值：" + shakeNumber);
                number += shakeNumber;
                System.out.println("当前值 :" + number);
                theNumberISTooLow.signal();
                theNumberIsTooHigh.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                conditionLock.unlock();
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.execute(tooHighMonitor);
        pool.execute(tooLowMonitor);
        while (true) {
            pool.execute(shake);
        }
    }
}
