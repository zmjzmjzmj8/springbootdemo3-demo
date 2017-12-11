package org.zmj.springbootdemo.demo;


public class RTread implements Runnable {
    @Override
    public void run() {
        synchronized (this){
                for (int i = 0; i <30 ; i++) {
                    System.out.println(Thread.currentThread().getName() + ":" +i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
        }
    }
}
