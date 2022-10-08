package com.z.part1.lesson26;

import com.z.part1.lesson15.BlockedQueue;

import java.util.Random;
import java.util.concurrent.*;

public class UseBlockingQueue {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<String> f1 = executor.submit(() -> getData("Database1"));
        Future<String> f2 = executor.submit(() -> getData("Database2"));
        Future<String> f3 = executor.submit(() -> getData("Database3"));

        BlockingQueue<String> bq = new LinkedBlockingQueue<>();

        executor.execute(() -> {
            try {
                bq.put(f1.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            try {
                bq.put(f2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            try {
                bq.put(f3.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        for (int i = 0; i < 3; i++) {
            String result = bq.take();
            executor.execute(() -> saveResult(result));
        }
    }

    static String getData(String databaseName) throws InterruptedException {
        Integer randomTime = new Random().nextInt(5, 11);

        System.out.println("从数据库: " + databaseName + " 获取数据， 预计耗时: " + randomTime + "s");

        TimeUnit.SECONDS.sleep(randomTime);

        return "data: " + databaseName;
    }

    static void saveResult(String data) {
        System.out.println("向本地数据库写入数据: " + data);
    }
}
