package com.z.part1.lesson23;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CalcPriceSync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> ft1 = new FutureTask<>(new GetPriceTask());
        FutureTask<Integer> ft2 = new FutureTask<>(new GetPriceTask());
        FutureTask<Integer> ft3 = new FutureTask<>(new GetPriceTask());

        Long startTime = System.currentTimeMillis();

        Thread thread1 = new Thread(ft1);
        Thread thread2 = new Thread(ft2);
        Thread thread3 = new Thread(ft3);

        thread1.start();
        thread2.start();
        thread3.start();

        System.out.println("结果: " + ft1.get() + " " + ft2.get() + " " + ft3.get());

        //如果此行命令写在前面的话，会导致时间计算的是异步任务发起的时间，所以应该放在此处
        Long endTime = System.currentTimeMillis();
        System.out.println("查询结束, 总耗时: " + (endTime - startTime) / 1000 + "秒");
    }

    static class GetPriceTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Integer waitingTime = new Random().nextInt(30);
            System.out.println("获取商品价格中, 预计耗时: " + waitingTime + "秒");
            TimeUnit.SECONDS.sleep(waitingTime);

            Integer price = new Random().nextInt(10, 50);
            System.out.println("获取价格成功, 结果: " + price);

            return price;
        }
    }
}
