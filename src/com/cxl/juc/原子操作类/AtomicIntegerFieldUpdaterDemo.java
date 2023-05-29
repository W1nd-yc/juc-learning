package com.cxl.juc.原子操作类;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author cxl
 * @Date 25/5/2023 09:01
 * @ClassReference: com.cxl.juc.原子操作类.AtomicIntegerFieldUpdaterDemo
 * @Description:
 * 需求：
 * 10个线程
 * 每个线程转账1000
 * 不使用synchronized，尝试使用AtomicIntegerFieldUpdater来实现
 */
class BankAccount {

    String bankName = "CCB";

    // 更新对象的属性必须使用 public volatile 修饰符
    public volatile int money = 0;

    public void add() {
        money++;
    }

    // 因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须
    // 使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性
    AtomicIntegerFieldUpdater fieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    // 不加synchronized，保证高性能原子性
    public void transMoney(BankAccount bankAccount) {
        fieldUpdater.getAndIncrement(bankAccount);
    }
}

public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
//                        bankAccount.add();
                        bankAccount.transMoney(bankAccount);
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t result:" + bankAccount.money);
    }
}
