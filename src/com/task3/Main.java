package com.task3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    static void copylist(String from,String to,String[] list){
        for (String f : list) {
            var item=new File(from+"\\"+f);
            var d=new File(to+"\\"+f);
            if(item.isDirectory()){
                d.mkdir();
                System.out.println(d.getPath());
                copylist(item.getPath(), d.getPath(), item.list());
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
            boolean isExists=false;
            if (!dirTo.exists()) {
                if(dirTo.mkdir()) isExists=true;
                else{
                    System.out.println("Error. The folder \""+dirTo+"\" was not created.");
                    return;
                }
            }
            if(isExists){
                copylist(from, to, Objects.requireNonNull(dirFrom.list()));
            }
        } else {
            System.out.println("Folder \""+from+"\" does not exists.");
        }
    }
}
