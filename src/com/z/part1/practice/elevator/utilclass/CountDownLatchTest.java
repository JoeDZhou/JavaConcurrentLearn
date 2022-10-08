package com.z.part1.practice.elevator.utilclass;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
    private static final CountDownLatch countDown = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int number = new Random().nextInt();
            System.out.println("开始准备部件， 编号: " + number);
            try {
                TimeUnit.SECONDS.sleep(new Random().nextInt(5, 20));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDown.countDown();
            System.out.println("部件准备完毕，编号：" + number + " 已完成:" + (3 - countDown.getCount()) + "/3");
        };

        for (int i = 0; i < 3; i++) {
            new Thread(runnable).start();
        }

        System.out.println("等待三个部件全部准备完成");
        countDown.await();
        System.out.println("三个组件已经全部完成，开始进行组装");
    }
}
