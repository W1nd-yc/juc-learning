package com.cxl.juc.LockSupport与线程中断.Interrupt;

/**
 * @Author cxl
 * @Date 23/5/2023 08:54
 * @ClassReference: com.cxl.juc.LockSupport与线程中断.Interrupt.InterruptDemo4
 * @Description:
 * main    false
 * main	false
 * ----1
 * ----2
 * main	true
 * main	false
 */
public class InterruptDemo4 {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println("----1");
        Thread.currentThread().interrupt(); // true
        System.out.println("----2");
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());

    }
}
