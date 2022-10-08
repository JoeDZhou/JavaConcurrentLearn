package com.z.part1.lesson23;

import java.sql.Time;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class Tea {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> ft2 = new FutureTask<>(new T2Task());
        FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));

        Thread thread1 = new Thread(ft1);
        Thread thread2 = new Thread(ft2);

        thread1.start();
        thread2.start();

        System.out.println(ft1.get());
    }

    static class T1Task implements Callable<String> {
        FutureTask<String> ft2;

        public T1Task(FutureTask ft2) {
            this.ft2 = ft2;
        }

        @Override
        public String call() throws Exception {
            System.out.println("T1洗水壶--1min");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T1烧水--15min");
            TimeUnit.SECONDS.sleep(15);

            //获取线程2提供的茶叶
            String teaName = ft2.get();

            System.out.println("T1拿到茶叶: " + teaName);

            System.out.println("T1泡茶...");

            return "上茶: " + teaName;
        }
    }

    static class T2Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("T2洗茶壶--1min");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T2洗茶杯--2min");
            TimeUnit.SECONDS.sleep(2);

            System.out.println("T2拿茶叶--1min");
            TimeUnit.SECONDS.sleep(1);

            return "龙井";
        }
    }
}
