package org.zmj.springbootdemo.demo;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThread {
    public static Thread thread1  ;
    public static Thread thread2  ;
    public static RTread rTread = new RTread();
    public static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    public static void main(String[] args) throws InterruptedException {
        thread1 = new Thread(rTread,"rTread");
        thread1.start();
        singleThreadExecutor.execute(rTread);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showThread();
        closeit();
        showThread();
    }

    public static void closeit() throws InterruptedException {
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showThread(){
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数加倍
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // 获取根线程组的所有线程
        int actualSize = topGroup.enumerate(slackList);
        // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        System.out.println("Thread list size == " + list.length);
        for (Thread thread : list) {
            System.out.println(thread.getName());
        }
    }
}
