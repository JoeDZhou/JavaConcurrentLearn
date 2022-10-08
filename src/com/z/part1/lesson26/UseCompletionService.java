package com.z.part1.lesson26;

import java.util.Random;
import java.util.concurrent.*;

public class UseCompletionService {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletionService<String> cs = new ExecutorCompletionService<>(executor);

        cs.submit(() -> getData("Database1"));
        cs.submit(() -> getData("Database2"));
        cs.submit(() -> getData("Database3"));

        for (int i = 0; i < 3; i++) {
            String result = cs.take().get();
            saveResult(result);
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
