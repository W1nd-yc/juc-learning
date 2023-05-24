package com.cxl.juc.LockSupport与线程中断.Interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @Author cxl
 * @Date 22/5/2023 19:54
 * @ClassReference: com.cxl.juc.LockSupport与线程中断.Interrupt.InterruptDemo2
 * @Description:
 */
public class InterruptDemo2 {

    public static void main(String[] args) {
        // 实例方法interrupt()仅仅是设置线程的中断状态位为true，不会停止线程

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println("----" + i);
            }
            System.out.println("t1线程调用interrupt()后的中断标识02：" + Thread.currentThread().isInterrupted()); // true
        }, "t1");

        t1.start();

        System.out.println("t1线程默认的中断标志：" + t1.isInterrupted()); // false

        try { TimeUnit.MILLISECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }

        t1.interrupt(); // true
        System.out.println("t1线程调用interrupt()后的中断标识01：" + t1.isInterrupted()); // false

        // 暂停1秒钟线程
        try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }
        System.out.println("t1线程调用interrupt()后的中断标识03：" + t1.isInterrupted()); // false
    }
}
