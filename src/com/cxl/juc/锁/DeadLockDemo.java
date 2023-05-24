package com.cxl.juc.锁;

import java.util.concurrent.TimeUnit;

/**
 * @Author cxl
 * @Date 22/5/2023 17:22
 * @ClassReference: com.cxl.juc.锁.DeadLockDemo
 * @Description:
 */
public class DeadLockDemo {

    public static void main(String[] args) {

        final Object objectA = new Object();
        final Object objectB = new Object();

        new Thread(() -> {
            synchronized (objectA) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有A锁，希望获得B锁");
                // 暂停1秒钟线程
                try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
                synchronized (objectB) {
                    System.out.println(Thread.currentThread().getName() + "\t 成功获得B锁");
                }
            }
        }, "A").start();

        new Thread(() -> {
            synchronized (objectB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有B锁，希望获得A锁");
                // 暂停1秒钟线程
                try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
                synchronized (objectA) {
                    System.out.println(Thread.currentThread().getName() + "\t 成功获得A锁");
                }
            }
        }, "B").start();
    }
}
