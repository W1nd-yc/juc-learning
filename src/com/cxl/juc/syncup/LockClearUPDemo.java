package com.cxl.juc.syncup;

/**
 * @Author cxl
 * @Date 1/6/2023 21:16
 * @ClassReference: com.cxl.juc.syncup.LockClearUPDemo
 * @Description: 锁消除
 * 从JIT角度看相当于无视它，synchronized(o)不存在了
 * 这个锁对象并灭有被共用扩散到其它线程使用，
 * 极端的说就是根本没有加这个锁对象的底层机器码，消除了锁的使用
 *
 * 输出结果：
 * -----hello LockClearUPDemo	127935703	157801043
 * -----hello LockClearUPDemo	1333410369	157801043
 * -----hello LockClearUPDemo	39096934	157801043
 * -----hello LockClearUPDemo	1835179342	157801043
 * -----hello LockClearUPDemo	593800070	157801043
 * -----hello LockClearUPDemo	1857211517	157801043
 * -----hello LockClearUPDemo	847910085	157801043
 * -----hello LockClearUPDemo	1878133438	157801043
 * -----hello LockClearUPDemo	543589342	157801043
 * -----hello LockClearUPDemo	1923132015	157801043
 */
public class LockClearUPDemo {

    static Object objectLock = new Object();

    public void m1() {
        /*synchronized (objectLock) {
            System.out.println("-----hello LockClearUPDemo");
        }*/

        // 锁消除问题，JIT编译器会无视它，synchronized(o)，每次new出来的，不存在了，非正常的
        Object o = new Object();
        synchronized (o) {
            System.out.println("-----hello LockClearUPDemo\t" + o.hashCode() + "\t" + objectLock.hashCode());
        }
    }

    public static void main(String[] args) {
        LockClearUPDemo lockClearUPDemo = new LockClearUPDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                lockClearUPDemo.m1();
            }, ""+i).start();
        }
    }
}
