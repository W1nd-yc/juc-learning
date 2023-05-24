package com.cxl.juc.LockSupport与线程中断.Interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author cxl
 * @Date 22/5/2023 19:12
 * @ClassReference: com.cxl.juc.LockSupport与线程中断.Interrupt.InterruptDemo
 * @Description:
 */
public class InterruptDemo {

    static volatile boolean isStop = false;

    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t isInterrupted()被修改为true，程序停止");
                    break;
                }
                System.out.println("----hello interrupt api");
            }
        }, "t1");

        t1.start();

        System.out.println("--------t1的默认中断标志位：" + t1.isInterrupted());

        try { TimeUnit.MILLISECONDS.sleep(20); }catch (InterruptedException e){ e.printStackTrace(); }

        // t2向t1发出协商，将t1的中断标志位设为true希望t1停下来
        new Thread(() -> {
            t1.interrupt();
        }, "t2").start();
    }

    private static void m2_atomicBoolean() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop被修改为true，程序停止");
                    break;
                }
                System.out.println("----hello volatile");
            }
        }, "t1").start();

        try { TimeUnit.MILLISECONDS.sleep(20); }catch (InterruptedException e){ e.printStackTrace(); }

        new Thread(() -> {
            atomicBoolean.set(true);
        }, "t2").start();
    }

    private static void m1_volatile() {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop被修改为true，程序停止");
                    break;
                }
                System.out.println("----hello volatile");
            }
        }, "t1").start();

        try { TimeUnit.MILLISECONDS.sleep(20); }catch (InterruptedException e){ e.printStackTrace(); }

        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }
}
