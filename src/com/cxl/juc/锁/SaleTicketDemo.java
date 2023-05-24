package com.cxl.juc.锁;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author cxl
 * @Date 21/5/2023 17:39
 * @ClassReference: com.cxl.juc.锁.SaleTicketDemo
 * @Description:
 */
class Ticket {

    private int number = 50;

    ReentrantLock lock = new ReentrantLock(true);

    public void sale() {
        lock.lock();
        try {
            // 判断是否有票
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + " : 卖出：" + (number--) + " 剩余： " + number);
            }
        } finally {
            // 解锁
            lock.unlock();
        }

    }
}
public class SaleTicketDemo {
    public static void main(String[] args) {
        Ticket lTicket = new Ticket();
        new Thread(()-> {
            for (int i = 0; i < 55; i++) {
                lTicket.sale();
            }
        }, "AA").start();

        new Thread(()-> {
            for (int i = 0; i < 55; i++) {
                lTicket.sale();
            }
        }, "BB").start();

        new Thread(()-> {
            for (int i = 0; i < 55; i++) {
                lTicket.sale();
            }
        }, "CC").start();
    }
}

