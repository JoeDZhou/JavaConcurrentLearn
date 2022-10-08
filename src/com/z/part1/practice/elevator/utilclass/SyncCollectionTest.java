package com.z.part1.practice.elevator.utilclass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncCollectionTest {
    public static void main(String[] args) {
        List<Integer> syncList = Collections.synchronizedList(new ArrayList<Integer>());
        for (int i = 0; i < 10; i++) {
            syncList.add(i + 1);
        }

        Runnable runnable = () -> {
            int action = new Random().nextInt(2);
            if (action == 0) {
                syncList.add(new Random().nextInt(100));
            } else {
                if (syncList.isEmpty()) {
                    System.out.println("List为空，无法获取元素");
                } else {
                    int index = new Random().nextInt(syncList.size());
                    System.out.println("当前List：" + syncList + ", 将要获取下标: " + index);
                    int result = syncList.remove(index);
                    System.out.println("获取成功，结果：" + result + "， 当前List：" + syncList);
                }
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 100; i++) {
            pool.submit(runnable);
        }
    }
}
