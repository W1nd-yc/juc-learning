package com.cxl.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author cxl
 * @Date 24/5/2023 16:17
 * @ClassReference: com.cxl.juc.cas.ABADemo
 * @Description:
 */
public class ABADemo {

    static AtomicInteger atomicInteger = new AtomicInteger(100);

    static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100 ,1);

    public static void main(String[] args) {

        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 首次版本号：" + stamp);

            try { TimeUnit.MILLISECONDS.sleep(500); }catch (InterruptedException e){ e.printStackTrace(); }

            stampedReference.compareAndSet(100, 101, stampedReference.getStamp(), stampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 2次流水号：" + stamp);

            stampedReference.compareAndSet(101, 100, stampedReference.getStamp(), stampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 3次流水号：" + stamp);
        }, "t3").start();

        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 首次版本号：" + stamp);

            // 等待t3线程发生ABA问题
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }

            boolean b = stampedReference.compareAndSet(100, 2022, stamp, stamp + 1);

            System.out.println(b + "\t" +stampedReference.getReference() + "\t" + stampedReference.getStamp());
        }, "t4").start();
    }

    private static void abaHappen() {
        new Thread(() -> {
            atomicInteger.compareAndSet(100, 101);
            try { TimeUnit.MILLISECONDS.sleep(10); }catch (InterruptedException e){ e.printStackTrace(); }
            atomicInteger.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
            try { TimeUnit.MILLISECONDS.sleep(200); }catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println(atomicInteger.compareAndSet(100, 2022) + "\t" + atomicInteger.get());
        }, "t1").start();
    }
}
