package com.z.part1.lesson25;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureException {
    public static void main(String[] args) {
        CompletableFuture<Integer> f0 = CompletableFuture
                .supplyAsync(() -> (7 / 0))
                .thenApply(r -> r * 10)
                .exceptionally(e -> 0);

        System.out.println(f0.join());
    }

}
