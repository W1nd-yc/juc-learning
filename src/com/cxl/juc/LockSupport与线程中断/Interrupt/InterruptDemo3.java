package com.cxl.juc.LockSupport与线程中断.Interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @Author cxl
 * @Date 22/5/2023 20:04
 * @ClassReference: com.cxl.juc.LockSupport与线程中断.Interrupt.InterruptDemo3
 * @Description:
 *
 * 1 中断标志位默认false
 * 2 t2 ---> t1 发出了中断协商，t2调用t1.interrupt()，中断标志位true
 * 3 中断标志位true，正常情况，程序停止
 * 4 中断标志位true，异常情况，InterruptedException，中断状态将被清除，并将收到InterruptedException。中断标志位false
 * 导致死循环
 * 5 在catch中，需要再次给中断标志位设置为true，2此调用停止程序才OK
 */
public class InterruptDemo3 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t 中断标志位：" +
                            Thread.currentThread().isInterrupted() + " 程序停止");
                    break;
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 为什么要在异常处再调用一次？
                    e.printStackTrace();
                }
                System.out.println("----------hello InterruptDemo3");
            }
        }, "t1");

        t1.start();

        // 暂停1秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            t1.interrupt();
        }, "t2").start();
    }
}
