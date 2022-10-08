package com.z.part1.lesson26;

import java.util.Random;
import java.util.concurrent.*;

public class DoNotUseCompletionService {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<String> f1 = executor.submit(() -> getData("Database1"));
        Future<String> f2 = executor.submit(() -> getData("Database2"));
        Future<String> f3 = executor.submit(() -> getData("Database3"));

        String result1 = f1.get();
        System.out.println("获取到数据库1的数据");
        saveResult(result1);

        String result2 = f2.get();
        System.out.println("获取到数据库2的数据");
        saveResult(result2);

        String result3 = f3.get();
        System.out.println("获取到数据库3的数据");
        saveResult(result3);
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
