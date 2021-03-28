package com.task4;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static boolean startThread2=false;
    static byte[] readBytes(String filename) throws IOException {
        InputStream inputStream = new FileInputStream(filename);
        byte[] buffer = new byte[inputStream.available()];
//        int count = 0;
        while (inputStream.available() > 0)
        {
//            count = inputStream.read(buffer);
            inputStream.read(buffer);
        }
        inputStream.close();
        return buffer;
    }
    synchronized static void findFiles(String from,String out,String word,String badWords) throws IOException {
        File dir=new File(from);
        OutputStream outputStream = new FileOutputStream(out);
        for (String item :
                Objects.requireNonNull(dir.list())) {
            File f=new File(from+"\\"+item);
            if(f.isFile() && !(from+"\\"+item).equals(out) && !(from+"\\"+item).equals(badWords)){
                System.out.println(item);
                Scanner fs = new Scanner(f);
                fs.useDelimiter("[^a-zA-Z0-9а-яА-ЯёЁ]+");
                boolean toOut=false;
                while (fs.hasNext() && !toOut) {
                    var w=fs.next();
                    if(w.equals(word)){
                        toOut=true;
                        var buffer=readBytes(from+"\\"+item);
                        System.out.println("Скопировано байтов: "+buffer.length);
                        outputStream.write(buffer);
                        outputStream.write("\n\n".getBytes());
                    }
                }
                fs.close();
            }
        }
        outputStream.close();
        notify();
    }
    static String stars(String str){
        String s="";
        for (int i = 0; i < str.length(); i++) s+="*";
        return s;
    }
    synchronized static void findWords(String out,String badWords,String outChecked) throws IOException {
        File Out=new File(out);
        File BadWords=new File(badWords);
        File OutChecked=new File(outChecked);
        Scanner fs = new Scanner(BadWords);
        ArrayList<String> arr=new ArrayList<>();
        while (fs.hasNext()){
            arr.add(fs.next());
        }
        var lines= Files.readAllLines(Out.toPath());
        StringBuilder contentb=new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            contentb.append(lines.get(i));
        }
        String content=contentb.toString();
        for (String str :
                arr) {
            System.out.print(str+stars(str));
            content=content.replaceAll(str,stars(str));
        }
        OutputStream outputStream = new FileOutputStream(outChecked);
        outputStream.write(content.getBytes());
        outputStream.close();
    }
    public static void main(String[] args) {
        String from="D:\\Desktop\\Test";
        String word="Lorem";
        String out="D:\\Desktop\\Test\\out.txt";
        String badWords="D:\\Desktop\\Test\\badWords.txt";
        String outChecked="D:\\Desktop\\Test\\outChecked.txt";
        ExecutorService exec = Executors.newFixedThreadPool(2);
        exec.submit(()-> {
            try {
                findFiles(from,out,word,badWords);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exec.submit(()-> {
            try {
                findWords(out,badWords,outChecked);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exec.shutdown();
    }
}
