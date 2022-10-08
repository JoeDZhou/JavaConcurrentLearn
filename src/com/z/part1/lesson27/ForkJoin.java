package com.z.part1.lesson27;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);

        Fibonacci fib = new Fibonacci(30);

        Integer result = pool.invoke(fib);
        System.out.println(result);

    }

    static class Fibonacci extends RecursiveTask<Integer> {
        final int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        protected Integer compute() {
            if (n <= 1) {
                return n;
            }

            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();

            Fibonacci f2 = new Fibonacci(n - 2);

            return f2.compute() + f1.join();
        }
    }
}
