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
        System.out.print("Array: ");
        for (int i = 0; i < this.capacity; i++) {
            var tmp=rnd.nextInt(this.maxF)+1;
            this.arr.add(tmp);
            System.out.print(tmp+" ");
        }
        System.out.println();
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
        System.out.print("simpleNums: ");
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < this.capacity; i++) {
            if(isSimpleNum(this.arr.get(i))){
                str.append(this.arr.get(i));
                str.append("\n");
                System.out.print(this.arr.get(i)+" ");
            }
        }
        System.out.println();
        saveToFile(fileName, str.toString());
    }
    public synchronized void factorials(String fileName) throws InterruptedException {
        while (!isFilled) {
            wait();
        }
        System.out.println("factorials:");
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < this.capacity; i++) {
            var tmp=factorial(this.arr.get(i).longValue());
            str.append(tmp);
            str.append("\n");
            System.out.println(tmp);
        }
        saveToFile(fileName, str.toString());
    }
}
