package com.cxl.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author cxl
 * @Date 24/5/2023 15:51
 * @ClassReference: com.cxl.juc.cas.SpinLockDemo
 * @Description:
 * 题日:实现一个自旋锁，复习CAS思想
 * 自旋锁好处: 循环比较获取没有类似wait的阳塞。
 *
 * 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自已持有锁5秒钟，B随后进来后发现
 * 当前有线程持有锁，所以只能通过自旋等待，直到A 释放锁后B 随后抢到。
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t ----come in");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t ----task over");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.lock();
            try { TimeUnit.SECONDS.sleep(5); }catch (InterruptedException e){ e.printStackTrace(); }
            spinLockDemo.unlock();
        }, "A").start();

        // 线程A先于B启动
        try { TimeUnit.MILLISECONDS.sleep(500); }catch (InterruptedException e){ e.printStackTrace(); }

        new Thread(() -> {
            spinLockDemo.lock();
            spinLockDemo.unlock();
        }, "B").start();
    }
}
