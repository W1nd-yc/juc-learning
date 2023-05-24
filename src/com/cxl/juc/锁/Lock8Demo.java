package com.cxl.juc.锁;

import java.util.concurrent.TimeUnit;

/**
 * @Author cxl
 * @Date 21/5/2023 16:27
 * @ClassReference: com.cxl.juc.锁.Lock8Demo
 * @Description:
 *  * 1.标准访问，先打印短信还是邮件 (锁当前对象)
 *  * --------sendEmail
 *  * --------sendSMS
 *  *
 *  * 2.停3秒在短信方法内，先打印短信还是邮件 (锁当前对象)
 *  * --------sendEmail
 *  * --------sendSMS
 *  *
 *  * 3.新增普通的hello方法，是先打邮件还是hello
 *  * --------getHello
 *  * --------sendEmail
 *  *
 *  * 4.现在有两部手机,先打印短信还是邮件 (两个对象不是同一把锁)
 *  * --------sendSMS
 *  * --------sendEmail
 *  *
 *  * 5.两个静态同步方法,1部手机,先打印短信还是邮件 (锁当前类)
 *  * --------sendEmail
 *  * --------sendSMS
 *  *
 *  * **6.两个静态同步方法,2部手机,先打印短信还是邮件 (锁当前类)
 *  * --------sendEmail
 *  * --------sendSMS
 *  *
 *  * 7.1个静态同步方法,1普通同步方法,1部手机,先打印短信还是邮件
 *  * --------sendSMS
 *  * --------sendEmail
 *  *
 *  * 8.1个静态同步方法,1普通同步方法,2部手机,先打印短信还是邮件
 *  * --------sendSMS
 *  * --------sendEmail
 *
 *  1-2
 *  一个对象里面如果有多个synchronized方法，某一个时刻内，只有一个吸纳成去调用其中的一个synchronized方法了
 *  其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一的一个线程去访问这些synchronized方法
 *  锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法
 *
 *  3-4
 *  加个普通方法后发现和同步锁无关
 *  换成两个对象后，不是同一把锁了，情况立刻变化
 *
 *  5-6
 *  对于普通同步方法，锁的是当前实例对象，通常指this，具体的一部部手机，所有的普通同步方法用的都是同一把锁 -> 实例对象本身
 *  对于静态同步方法，锁的是当前类的Class对象，如Phone，class唯一的一个模板
 *  对于同步方法块，锁的是 synchronized 括号中的对象
 *
 *  7-8
 *  当一个线程试图访问同步代码时它首先必须得到锁工正常退出或抛出异常时必须释放锁。
 *  所有的普通同步方法用的都是同一把锁一实例对象本身，就是new出来的具体实例对象本身，本类this
 *  也就是说如果一个实例对象的普通同步方法获取锁后，该实例对象的其他普通同步方法必须等待获取锁的方法释放锁后才能获取锁。
 *
 *  所有的静态同步方法用的也是同一把锁一类对象本身，就是我们说过的唯一模板cLass
 *  具体实例对象this和唯一模板Class，这两把锁是两个不同的对象，所以静态同步方法与普通同步方法之间是不会有竟态条件的
 *  但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁。
 */
class Phone {

    public static synchronized void sendEmail() {
        // 暂停3秒钟线程
//        try { TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }
        System.out.println("----sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("----sendSMS");
    }

    public void hello() {
        System.out.println("------hello");
    }
}

public class Lock8Demo {

    public static void main(String[] args) {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            phone.sendEmail();
        }, "a").start();

        try { TimeUnit.MILLISECONDS.sleep(200); }catch (InterruptedException e){ e.printStackTrace(); }

        new Thread(() -> {
            phone.sendSMS();
//            phone.hello();
        }, "b").start();
    }
}
