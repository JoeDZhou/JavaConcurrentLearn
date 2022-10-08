package com.z.part1.practice.elevator.utilclass;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    private static final Lock firstRoundInterviewLock = new ReentrantLock();
    private static final Lock secondRoundInterviewLock = new ReentrantLock();

    public static void main(String[] args) {
        Runnable runnable = () -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + "开始等待第一轮面试");
            firstRoundInterviewLock.lock();
            try {
                System.out.println(name + "开始进行第一轮面试");
                TimeUnit.SECONDS.sleep((new Random().nextInt(5, 10)));
                System.out.println(name + "完成第一轮面试");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstRoundInterviewLock.unlock();
            }
            System.out.println(name + "开始等待第二轮面试");

            secondRoundInterviewLock.lock();
            try {
                System.out.println(name + "开始进行第二轮面试");
                TimeUnit.SECONDS.sleep((new Random().nextInt(5, 10)));
                System.out.println(name + "完成第二轮面试");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                secondRoundInterviewLock.unlock();
            }

            System.out.println(name + "面试全部完成");
        };

        ExecutorService pool = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 20; i++) {
            pool.execute(runnable);
        }
    }
}
