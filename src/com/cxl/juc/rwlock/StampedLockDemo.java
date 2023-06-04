package com.cxl.juc.rwlock;

import java.security.PublicKey;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @Author cxl
 * @Date 4/6/2023 20:25
 * @ClassReference: com.cxl.juc.rwlock.StampedLockDemo
 * @Description: StampedLock = ReentrantReadWriteLock + 读的过程中也允许获取写锁介入
 */
public class StampedLockDemo {

    static int number = 37;

    static StampedLock stampedLock = new StampedLock();

    public void write() {
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName() + "\t写线程准备修改");
        try {
            number = number + 13;
        } finally {
            stampedLock.unlock(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t写线程结束修改");

    }

    // 悲观读，读没有完成时候写锁无法获得锁
    public void read() {
        long stamp = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName() + "\t come in readlock code block, 4seconds continue...");
        for (int i = 0; i < 4; i++) {
            // 暂停1秒钟线程
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t正在读取中...");
        }
        try {
            int result = number;
            System.out.println(Thread.currentThread().getName() + "\t获得成员变量值:" + result);
            System.out.println("写线程没有修改成功，读锁时候写锁无法接入，传统的读写互斥");
        } finally {
            stampedLock.unlock(stamp);
        }
    }

    public void tryOptimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        // 故意间隔4秒钟，很乐观认为读取中没有其它吸纳成修改过number值，具体靠判断
        System.out.println("4秒前stampedLock.validate方法值(true无修改，false有修改)" + "\t" + stampedLock.validate(stamp));

        for (int i = 0; i < 4; i++) {
            try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t正在读取..." + i +
                    "秒后stampedLock.validate方法值(true无修改，false有修改)" + "\t" + stampedLock.validate(stamp));
        }

        if (!stampedLock.validate(stamp)) {
            System.out.println("有人修改------有写操作");
            stamp = stampedLock.readLock();
            try {
                System.out.println("从乐观读 升级为 悲观读");
                result = number;
                System.out.println("重新悲观读后result：" + result);
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }

        System.out.println(Thread.currentThread().getName() + "\t finally value:" + result);
    }

    public static void main(String[] args) {
        StampedLockDemo resource = new StampedLockDemo();

        // 传统版
        /*new Thread(() -> {
            resource.read();
        }, "readThread").start();

        // 暂停1秒钟线程
        try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t---come in");
            resource.write();
        }, "writeThread").start();

        try { TimeUnit.SECONDS.sleep(4); }catch (InterruptedException e){ e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName() + "\t number:" + number);*/

        new Thread(() -> {
            resource.tryOptimisticRead();
        }, "readThread").start();

        try { TimeUnit.SECONDS.sleep(6); }catch (InterruptedException e){ e.printStackTrace(); }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t-----come in");
            resource.write();
        }, "writeThread").start();
    }
}
