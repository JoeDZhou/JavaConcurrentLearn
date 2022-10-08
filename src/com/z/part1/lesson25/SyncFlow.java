package com.z.part1.lesson25;

import java.util.concurrent.CompletableFuture;

public class SyncFlow {
    public static void main(String[] args) {
        CompletableFuture<String> f0 = CompletableFuture
                .supplyAsync(() -> "Hello World")
                .thenApply(s -> s + "QQ")
                .thenApply(String::toString);

        System.out.println(f0.join());
    }
}
