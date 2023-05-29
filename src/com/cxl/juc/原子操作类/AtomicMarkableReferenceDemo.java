package com.cxl.juc.原子操作类;

import java.rmi.MarshalException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @Author cxl
 * @Date 24/5/2023 19:11
 * @ClassReference: com.cxl.juc.原子操作类.AtomicMarkableReferenceDemo
 * @Description:
 *
 * AtomicStampedReference,version号 ，+1
 * AtomicMarkableReference，一次，false，true
 *
 * 输出结果：
 * t1	 默认标识false
 * t2	 默认标识false
 * t2	 t2线程CASresult:false
 * t2	true
 * t2	1000
 */
public class AtomicMarkableReferenceDemo {

    static AtomicMarkableReference markableReference = new AtomicMarkableReference(100, false);

    public static void main(String[] args) {

        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t 默认标识" + marked);
            // 暂停1秒钟线程，等待后面的t2线程和t1拿到一样的标识
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
            markableReference.compareAndSet(100, 1000, marked, true);
        }, "t1").start();

        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t 默认标识" + marked);
            // 暂停1秒钟线程
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
            boolean b = markableReference.compareAndSet(100, 1000, marked, !marked);
            System.out.println(Thread.currentThread().getName() + "\t t2线程CASresult:" + b);
            System.out.println(Thread.currentThread().getName() + "\t" + markableReference.isMarked());
            System.out.println(Thread.currentThread().getName() + "\t" + markableReference.getReference());
        }, "t2").start();
    }
}
