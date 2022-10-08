package com.z.part1.practice.elevator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Elevator {
    private final Lock lock = new ReentrantLock();


    private Integer targetLevel;
    private Integer currentLevel;
    private Boolean isRising;
    private Boolean hasRider;


    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        while (true) {

        }

    }

//    Runnable runnable = () -> {
//        while (true) {
//            if ()
//        }
//    }


    void callElevator(Integer currentLevel, Integer targetLevel) {

    }
}
