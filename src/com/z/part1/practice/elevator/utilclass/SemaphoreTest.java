package com.z.part1.practice.elevator.utilclass;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    private static final Semaphore productionLine = new Semaphore(3);

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        Runnable runnable = () -> {
            try {
                Thread.currentThread().setName("Thread-" + new Random().nextInt(10000, 99999));
                productionLine.acquire();
                String name = Thread.currentThread().getName();
                System.out.println(name + " 开始占用生产线生产");
                TimeUnit.SECONDS.sleep(new Random().nextInt(5, 10));
                System.out.println(name + " 生产完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                productionLine.release();
            }

        };

        for (int i = 0; i < 100; i++) {
            pool.execute(runnable);
        }
    }


//    static final Semaphore semaphore = new Semaphore(3);
//    static int number = 0;
//
//    public static void main(String[] args) {
//        ExecutorService pool = Executors.newFixedThreadPool(10);
//
//        Runnable runnable = () -> {
//            try {
//                semaphore.acquire();
//                SemaphoreTest.number++;
//                System.out.println("获取信号量, 当前number: " + SemaphoreTest.number);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                System.out.println("Interrupted");
//            } finally {
//                SemaphoreTest.number--;
//                System.out.println("释放信号量,当前number: " + SemaphoreTest.number);
//                semaphore.release();
//            }
//        };
//
//        for (int i = 0; i < 100; i++) {
//            pool.execute(runnable);
//        }
//
//    }
}
