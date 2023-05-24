package com.cxl.juc.volatile与JMM;

import java.util.concurrent.TimeUnit;

/**
 * @Author cxl
 * @Date 23/5/2023 20:59
 * @ClassReference: com.cxl.juc.volatile与JMM.VolatileNoAtomicDemo
 * @Description:
 */
class MyNumber {

    volatile int number;

    public void addPlusPlus() {
        number++;
    }
}
public class VolatileNoAtomicDemo {

    public static void main(String[] args) {

        MyNumber myNumber = new MyNumber();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myNumber.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        // 暂停1秒钟线程
        try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }

        System.out.println(myNumber.number);
    }
}
