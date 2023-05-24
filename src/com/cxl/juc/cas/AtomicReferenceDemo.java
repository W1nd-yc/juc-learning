package com.cxl.juc.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author cxl
 * @Date 24/5/2023 15:43
 * @ClassReference: com.cxl.juc.cas.AtomicReferenceDemo
 * @Description:
 */
class User {

    String userName;
    int age;

    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();
        User z3 = new User("z3", 22);
        User li4 = new User("li", 23);

        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());
    }
}
