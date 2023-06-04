package com.cxl.juc.syncup;

/**
 * @Author cxl
 * @Date 1/6/2023 21:21
 * @ClassReference: com.cxl.juc.syncup.LockBigDemo
 * @Description: 锁粗化
 */
public class LockBigDemo {

    static Object objectLock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println("1111");
            }

            synchronized (objectLock) {
                System.out.println("2222");
            }

            synchronized (objectLock) {
                System.out.println("3333");
            }

            synchronized (objectLock) {
                System.out.println("4444");
            }

            // 上面四个synchronized会被编译器合并这个下面这个
            synchronized (objectLock) {
                System.out.println("1111");
                System.out.println("2222");
                System.out.println("3333");
                System.out.println("4444");
            }
        }, "t1").start();
    }
}
