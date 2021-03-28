package com.task4;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Actions {
    private boolean startThread2=false;
    private byte[] readBytes(String filename) throws IOException {
        InputStream inputStream = new FileInputStream(filename);
        byte[] buffer = new byte[inputStream.available()];
        while (inputStream.available() > 0){
            inputStream.read(buffer);
        }
        inputStream.close();
        return buffer;
    }
    private String stars(String str){
        String s="";
        for (int i = 0; i < str.length(); i++) s+="*";
        return s;
    }
    public synchronized void findFiles(String from,String out,String word,String badWords) throws IOException {
        File dir=new File(from);
        OutputStream outputStream = new FileOutputStream(out);
        int count=0;
        System.out.println("Файлы в которых найдено слово \""+word+"\":");
        for (String item :
                Objects.requireNonNull(dir.list())) {
            File f=new File(from+"\\"+item);
            if(f.isFile() && !(from+"\\"+item).equals(out) && !(from+"\\"+item).equals(badWords)){
                Scanner fs = new Scanner(f);
                fs.useDelimiter("[^a-zA-Z0-9а-яА-ЯёЁ]+");
                boolean toOut=false;
                while (fs.hasNext() && !toOut) {
//                    var w=fs.next();
                    if(fs.next().equals(word)){
                        toOut=true;
                        var buffer=readBytes(from+"\\"+item);
                        System.out.println(item);
                        outputStream.write(buffer);
                        outputStream.write("\n\n".getBytes());
                    }
                }
                fs.close();
            }
        }
        if(count>0) System.out.println("подходящие файлы не найдены.");
        outputStream.close();
        startThread2=true;
        notify();
    }
    public synchronized void findWords(
            String inFile,
            String badWords,
            String outFile) throws IOException, InterruptedException {
        while (!this.startThread2){
            wait();
        }
        File InFile=new File(inFile);
        var lines= Files.readAllLines(InFile.toPath());
        if(lines.size()>0) {
            StringBuilder contentb = new StringBuilder();
            for (int i = 0; i < lines.size(); i++) {
                contentb.append(lines.get(i));
                contentb.append("\n\n");
            }
            String content = contentb.toString();
            File BadWords = new File(badWords);
            Scanner fs = new Scanner(BadWords);
            ArrayList<String> arr = new ArrayList<>();
            while (fs.hasNext()) {
                arr.add(fs.next());
            }
            for (String str :
                    arr) {
                System.out.print("Замена слов \""+str+"\" на \""+ stars(str)+"\". Совпадений: ");
                int before=content.length();
                int after=(content.replaceAll(str, "")).length();
                System.out.println((before-after)/str.length());
                content = content.replaceAll(str, stars(str));
            }
            OutputStream outputStream = new FileOutputStream(outFile);
            outputStream.write(content.getBytes());
            outputStream.close();
        }
    }
}
