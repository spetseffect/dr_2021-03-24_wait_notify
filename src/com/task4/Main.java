package com.task4;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        String from="D:\\Desktop\\Test"; //где ищем файлы
        String word="Lorem"; //слово на наличие которого проверяем найденные файлы
        String out="D:\\Desktop\\Test\\out.txt";//этот файл будет создан первым потоком. !!!если файл существует, он будет перезаписан
        String badWords="D:\\Desktop\\Test\\badWords.txt";//список слов для замены
        String outChecked="D:\\Desktop\\Test\\outChecked.txt";//этот файл будет создан вторым потоком. !!!если файл существует, он будет перезаписан
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
