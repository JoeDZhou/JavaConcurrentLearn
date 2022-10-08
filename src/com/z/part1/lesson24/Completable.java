package com.z.part1.lesson24;

import java.awt.geom.Path2D;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Completable {
    public static void main(String[] args) {
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("T1洗水壶--1min");
                TimeUnit.SECONDS.sleep(1);

                System.out.println("T1烧开水--15min");
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("T2洗茶壶--1min");
                TimeUnit.SECONDS.sleep(1);

                System.out.println("T2洗茶杯--2min");
                TimeUnit.SECONDS.sleep(2);

                System.out.println("T2取茶叶--1min");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                return "龙井";
            }
        });

        CompletableFuture<String> f3 = f1.thenCombine(f2, (p1, p2) -> {
            System.out.println("T1拿到茶叶: " + p2);
            System.out.println("泡茶...");

            return "上茶: " + p2;
        });

        System.out.println(f3.join());
    }
}
