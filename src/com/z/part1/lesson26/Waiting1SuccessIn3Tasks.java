package com.z.part1.lesson26;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Waiting1SuccessIn3Tasks {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletionService<String> cs = new ExecutorCompletionService<>(executor);

        List<Future<String>> futures = new ArrayList<>(3);

        futures.add(cs.submit(() -> getData("地图")));
        futures.add(cs.submit(() -> getData("地图")));
        futures.add(cs.submit(() -> getData("地图")));

        try {
            for (int i = 0; i < 3; i++) {
                String result = cs.take().get();
                if (result != null) {
                    System.out.println("获取数据成功, 结果: " + result);
                    break;
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            for (Future<String> f : futures) {
                f.cancel(true);
            }
        }

        System.out.println("整个程序执行结束");
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
