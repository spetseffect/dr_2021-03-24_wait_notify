package com.task1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        String fname1 = "D:\\Desktop\\Test\\27.txt";
        String fname2 = "D:\\Desktop\\Test\\27s.txt";
        String fname3 = "D:\\Desktop\\Test\\27f.txt";
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
