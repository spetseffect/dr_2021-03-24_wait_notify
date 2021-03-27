package com.task2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class Actions {
    public Random rnd = new Random(System.currentTimeMillis());
    public int capacity = 20;
    public int maxF=20;//максимальное случайное число (много не брать, а то факториал повесится)
    ArrayList<Integer> arr = new ArrayList<>(capacity);
    boolean isFilled = false;
    public static void saveToFile(String fileName, String data) {
        try {
            OutputStream out = new FileOutputStream(fileName);
            out.write(data.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isSimpleNum(Integer value){
        for (int i = 2; i <=value/2 ; i++) {
            if(value%i==0) return false;
        }
        return true;
    }
    public static Long factorial(Long value){
        long result=1L;
        for (long i = 1L; i <= value ; i++) {
            result*=i;
        }
        return result;
    }
    public synchronized void fill(String fileName) {
        for (int i = 0; i < this.capacity; i++) {
            this.arr.add(rnd.nextInt(this.maxF)+1);
        }
        this.isFilled = true;
        notify();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < this.capacity; i++) {
            str.append(this.arr.get(i));
            str.append("\n");
        }
        saveToFile(fileName, str.toString());
    }
    public synchronized void simpleNums(String fileName) throws InterruptedException {
        while (!isFilled) {
            wait();
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < this.capacity; i++) {
            if(isSimpleNum(this.arr.get(i))){
                str.append(this.arr.get(i));
                str.append("\n");
            }
        }
        saveToFile(fileName, str.toString());
    }
    public synchronized void factorials(String fileName) throws InterruptedException {
        while (!isFilled) {
            wait();
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < this.capacity; i++) {
            str.append(factorial(this.arr.get(i).longValue()));
            str.append("\n");
        }
        saveToFile(fileName, str.toString());
    }
}
