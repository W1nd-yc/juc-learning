package com.cxl.juc.LockSupport与线程中断.LockSupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author cxl
 * @Date 23/5/2023 09:13
 * @ClassReference: com.cxl.juc.LockSupport与线程中断.LockSupport.LockSupportDemo
 * @Description:
 *
 *
 */
public class LockSupportDemo {


    /**
     * 正常+无锁块要求
     * 之前错误的先唤醒后等待，LockSupport照样支持
     * @param args
     */
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t ----come in");
            LockSupport.park();
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t ----被唤醒");
        }, "t1");

        t1.start();


        new Thread(() -> {
            LockSupport.unpark(t1);
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t ----发出通知");
        }, "t2").start();
    }

    /**
     * 异常1：如果去掉lock和unlock也会抛IllegalMonitorStateException
     *
     * 异常2：将signal放在await前面，程序无法执行，无法唤醒
     */
    private static void lockAwaitSignal() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            // 暂停1秒钟线程
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
             lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ---come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t ---被唤醒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                 lock.unlock();
            }
        }, "t1").start();

        // 暂停1秒钟线程
//        try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }

        new Thread(() -> {
             lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t ---发出通知");

            } finally {
                 lock.unlock();
            }
        }, "t2").start();
    }

    /**
     * 异常1：wait和notify方法如果都去掉同步代码块会抛IllegalMonitorStateException
     *
     * 异常2：将notify放在wait前面，程序无法执行，无法唤醒
     */
    private static void syncWaitNotify() {
        Object objectLock = new Object();

        new Thread(() -> {
            // 暂停1秒钟线程
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t ----come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t ----被唤醒");

            }
        }, "t1").start();

//        try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }

        new Thread(() -> {
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t ----发出通知");
            }
        }, "t2").start();
    }
}
