package com.cxl.juc.rwlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author cxl
 * @Date 4/6/2023 20:00
 * @ClassReference: com.cxl.juc.rwlock.LockDownGradingDemo
 * @Description:
 */
public class LockDownGradingDemo {

    public static void main(String[] args) {

        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();

/*        // 正常A B两个线程
        // A
        writeLock.lock();
        System.out.println("---写入");
        writeLock.unlock();

        // B
        readLock.lock();
        System.out.println("---读取");*/

        // 本例，only one 同一个线程
        writeLock.lock();
        System.out.println("---写入");

        readLock.lock();
        System.out.println("---读取");

        writeLock.unlock();
        readLock.unlock();
    }
}
