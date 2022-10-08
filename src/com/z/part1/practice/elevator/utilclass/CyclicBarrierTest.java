package com.z.part1.practice.elevator.utilclass;

import java.util.Random;
import java.util.concurrent.*;

public class CyclicBarrierTest {
    private static int productA = 0;
    private static int productB = 0;
    private static int productC = 0;

    private static final CyclicBarrier barrier = new CyclicBarrier(3, () -> {
        System.out.println("产品组件全部准备就绪");
        System.out.println("A: " + productA + " B: " + productB + " C: " + productC);
        System.out.println("组装结果" + productA + productB + productC);
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("开始恢复生产");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });

    public static void main(String[] args) {
        Runnable productLineA = () -> {
            productA = new Random().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(productA);
                System.out.println("组件A生产完毕：" + productA);
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        Runnable productLineB = () -> {
            productB = new Random().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(productB);
                System.out.println("组件B生产完毕：" + productB);
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        Runnable productLineC = () -> {
            productC = new Random().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(productC);
                System.out.println("组件C生产完毕：" + productC);
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 100; i++) {
            pool.submit(productLineA);
            pool.submit(productLineB);
            pool.submit(productLineC);
        }

    }
}
