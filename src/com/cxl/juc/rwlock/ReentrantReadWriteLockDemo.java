package com.cxl.juc.rwlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author cxl
 * @Date 4/6/2023 19:43
 * @ClassReference: com.cxl.juc.rwlock.ReentrantReadWriteLockDemo
 * @Description:
 */
// 资源类，模拟一个简单的缓存
class MyResource {

    Map<String, String> map = new HashMap<>();

    Lock lock = new ReentrantLock();

    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void write(String key, String value) {
//        lock.lock();
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在写入");
            map.put(key, value);
            try { TimeUnit.MILLISECONDS.sleep(500); }catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t完成写入");
        } finally {
//            lock.unlock();
            rwLock.writeLock().unlock();
        }
    }

    public void read(String key) {
//        lock.lock();
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在读取");
            String result = map.get(key);
            //try { TimeUnit.MILLISECONDS.sleep(200); }catch (InterruptedException e){ e.printStackTrace(); }
            // 暂停2秒，演示读锁没有完成之前，写锁无法获得
            try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t完成读取" + result);
        } finally {
//            lock.unlock();
            rwLock.readLock().unlock();
        }
    }

}

public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        MyResource myResource = new MyResource();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read(finalI + "");
            }, String.valueOf(i)).start();
        }

        // 暂停1秒钟线程
        try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }

        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write("新写锁=>" + finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }
    }
}
