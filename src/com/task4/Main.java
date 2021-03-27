package com.task4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static void findFiles(String from){}
    static void findWords(String out,String word){}
    public static void main(String[] args) {
        String from="D:\\Desktop\\Test";
        String word="Lorem";
        String out="D:\\Desktop\\Test\\out.txt";
        ExecutorService exec = Executors.newFixedThreadPool(2);
        exec.submit(()->findFiles(from));
        exec.submit(()->findWords(out,word));
        exec.shutdown();
    }
}
