package com.z.part1.lesson1;

public class Test {
    private int count = 0;

    private void add10K() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Test test = new Test();

        Thread th1 = new Thread(() -> {
           test.add10K();
        });
        Thread th2 = new Thread(() -> {
            test.add10K();
        });

        th1.start();
        th2.start();

        th1.join();
        th2.join();

        System.out.println(test.count);
    }
}
