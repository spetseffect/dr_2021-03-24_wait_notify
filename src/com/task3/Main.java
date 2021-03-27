package com.task3;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String from="D:\\Desktop\\Test\\from";
        String to="D:\\Desktop\\Test\\to";
        File dirFrom=new File(from);
        File dirTo=new File(to);
        if (dirFrom.exists()) {
            if (!dirTo.exists()) dirTo.mkdir();
            //
        } else {
            System.out.println("Folder \""+from+"\" does not exists.");
        }
    }
}
