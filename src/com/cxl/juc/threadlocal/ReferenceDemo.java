package com.cxl.juc.threadlocal;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author cxl
 * @Date 29/5/2023 17:28
 * @ClassReference: com.cxl.juc.threadlocal.ReferenceDemo
 * @Description:
 */
class MyObject {

    /**
     * 这个方法一般不要复写
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        // finalized的通常目的是在对象被不可撤销地丢弃之前执行清理操作
        System.out.println("-----------invoke finalize method!");
    }
}

public class ReferenceDemo {

    public static void main(String[] args) {
        phantomReference();
    }



    /**
     * 输出结果：
     * null	 list add ok
     * -----------invoke finalize method!
     * null	 list add ok
     * null	 list add ok
     * null	 list add ok
     * null	 list add ok
     * null	 list add ok
     * null	 list add ok
     * Exception in thread "t1" java.lang.OutOfMemoryError: Java heap space
     * 	at com.cxl.juc.threadlocal.ReferenceDemo.lambda$main$0(ReferenceDemo.java:39)
     * 	at com.cxl.juc.threadlocal.ReferenceDemo$$Lambda$1/1324119927.run(Unknown Source)
     * 	at java.lang.Thread.run(Thread.java:748)
     * ----有虚对象回收加入了队列
     *
     * Process finished with exit code 0
     */
    private static void phantomReference() {
        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);
//        System.out.println(phantomReference.get());

        List<byte[]> list = new ArrayList<>();

        new Thread(() -> {
            while (true) {
                list.add(new byte[1 * 1024 * 1024]);
                try { TimeUnit.MILLISECONDS.sleep(500); }catch (InterruptedException e){ e.printStackTrace(); }
                System.out.println(phantomReference.get() + "\t list add ok");
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                Reference<? extends MyObject> reference = referenceQueue.poll();
                if (reference != null) {
                    System.out.println("----有虚对象回收加入了队列");
                    break;
                }
            }
        }, "t1").start();
    }

    /**
     * 输出结果：
     * -----gc before 内存够用：com.cxl.juc.threadlocal.MyObject@1b6d3586
     * -----------invoke finalize method!
     * -----gc after内存够用：null
     */
    private static void weakReference() {
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
        System.out.println("-----gc before 内存够用：" + weakReference.get());

        System.gc();
        // 暂停1秒钟线程
        try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }

        System.out.println("-----gc after内存够用：" + weakReference.get());
    }

    /**
     * 输出结果：
     * ------gc after 内存够用：com.cxl.juc.threadlocal.MyObject@1b6d3586
     * -----gc after内存不够用：null
     * -----------invoke finalize method!
     * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
     * 	at com.cxl.juc.threadlocal.ReferenceDemo.main(ReferenceDemo.java:36)
     */
    private static void softReference() {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
//        System.out.println("------softReference" + softReference.get());
        System.gc();
        try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
        System.out.println("------gc after 内存够用：" + softReference.get());

        try {
            byte[] bytes = new byte[20 * 1024 * 1024];// 20MB对象
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("-----gc after内存不够用：" + softReference.get());
        }
    }

    /**
     * 输出结果：
     * gc before:com.cxl.juc.threadlocal.MyObject@1b6d3586
     * -----------invoke finalize method!
     * gc after:null
     */
    private static void strongReference() {
        MyObject myObject = new MyObject();
        System.out.println("gc before:" + myObject);

        myObject = null;
        System.gc(); // 人工开启GC

        // 暂停1秒钟线程
        try { TimeUnit.MILLISECONDS.sleep(500); }catch (InterruptedException e){ e.printStackTrace(); }

        System.out.println("gc after:" + myObject);
    }
}
