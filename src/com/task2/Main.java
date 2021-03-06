package com.task2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        String fname1 = "D:\\Desktop\\Test\\27.txt";//сюда будет записан набор случайных чисел
        String fname2 = "D:\\Desktop\\Test\\27s.txt";//сюда будут записаны простые числа
        String fname3 = "D:\\Desktop\\Test\\27f.txt";//сюда будут записаны факториалы
        var act = new Actions();
        ExecutorService exec = Executors.newFixedThreadPool(3);
        Runnable task1 = () -> act.fill(fname1);
        Runnable task2 = () -> {
            try {
                act.simpleNums(fname2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable task3 = () -> {
            try {
                act.factorials(fname3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        exec.submit(task1);
        exec.submit(task2);
        exec.submit(task3);
        exec.shutdown();
    }
}
