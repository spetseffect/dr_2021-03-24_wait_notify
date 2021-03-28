package com.task4;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        String from="D:\\Desktop\\Test";
        String word="Lorem";
        String out="D:\\Desktop\\Test\\out.txt";
        String badWords="D:\\Desktop\\Test\\badWords.txt";
        String outChecked="D:\\Desktop\\Test\\outChecked.txt";
        ExecutorService exec = Executors.newFixedThreadPool(2);
        Actions act=new Actions();
        exec.submit(()-> {
            try {
                act.findFiles(from,out,word,badWords);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exec.submit(()-> {
            try {
                act.findWords(out,badWords,outChecked);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        exec.shutdown();
    }
}
