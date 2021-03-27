package com.task3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static void copylist(String from,String to,String[] list){
        for (String f : list) {
            var item=new File(from+"\\"+f);
            var d=new File(to+"\\"+f);
            if(item.isDirectory()){
                if(d.mkdir()) {
                    System.out.println(d.getPath());
                    copylist(item.getPath(), d.getPath(), Objects.requireNonNull(item.list()));
                }
            }
            else{
                try {
                    Files.copy(item.toPath(),d.toPath());
                    System.out.println(d.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        String from="D:\\Desktop\\Test\\from";
        String to="D:\\Desktop\\Test\\to";
        File dirFrom=new File(from);
        File dirTo=new File(to);
        if (dirFrom.exists()) {
            if (!dirTo.exists()) {
                if(!dirTo.mkdir()){
                    System.out.println("Error. The folder \""+dirTo+"\" was not created.");
                    return;
                }
            }
            ExecutorService exec = Executors.newSingleThreadExecutor();
            exec.submit(()-> copylist(from, to, Objects.requireNonNull(dirFrom.list())));
            exec.shutdown();
        } else {
            System.out.println("Folder \""+from+"\" does not exists.");
        }
    }
}
