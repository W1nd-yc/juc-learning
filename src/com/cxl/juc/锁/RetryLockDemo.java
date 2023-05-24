package com.cxl.juc.锁;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author cxl
 * @Date 22/5/2023 16:38
 * @ClassReference: com.cxl.juc.锁.RetryLockDemo
 * @Description:
 */
public class RetryLockDemo {

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + "\t ----come in");
        m2();
        System.out.println(Thread.currentThread().getName() + "\t ----come end");
    }

    public synchronized void m2() {
        System.out.println(Thread.currentThread().getName() + "\t ----come in");
        m3();
    }

    public synchronized void m3() {
        System.out.println(Thread.currentThread().getName() + "\t ----come in");
    }

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        /*RetryLockDemo retryLockDemo = new RetryLockDemo();

        new Thread(() -> {
            retryLockDemo.m1();
        }, "t1").start();*/

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ---come in外层调用");
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "\t ---come in内层调用");
                } finally {
                     lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ---come in外层调用");
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    private static void reEntryM1(Object object) {
        new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + "\t ----外层调用");
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + "\t ----中层调用");
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + "\t ----内层调用");
                    }
                }
            }

        }, "t1").start();
    }
}
