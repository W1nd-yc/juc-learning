package com.cxl.juc.syncup;


/**
 * @Author cxl
 * @Date 31/5/2023 14:42
 * @ClassReference: com.cxl.juc.syncup.SaleTicketDemo
 * @Description:
 */
class Ticket {

    private int number = 50;

    Object lockObject = new Object();

    public void sale() {
        synchronized (lockObject) {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + " : 卖出：" + (number--) + " 剩余： " + number);
            }
        }
    }
}
public class SaleTicketDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(()-> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "a").start();

        new Thread(()-> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "b").start();

        new Thread(()-> {
            for (int i = 0; i < 55; i++) {
                ticket.sale();
            }
        }, "c").start();
    }
}