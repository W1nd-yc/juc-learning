package com.cxl.juc.cas;


import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author cxl
 * @Date 24/5/2023 16:10
 * @ClassReference: com.cxl.juc.cas.AtomicStampdDemo
 * @Description:
 */
class Book {
    private int id;
    private String bookName;

    public Book(int id, String bookName) {
        this.id = id;
        this.bookName = bookName;
    }
}

public class AtomicStampedDemo {

    public static void main(String[] args) {
        Book javaBook = new Book(1, "javaBook");

        AtomicStampedReference<Book> stampedReference = new AtomicStampedReference<>(javaBook, 1);

        System.out.println(stampedReference.getReference() + "\t" + stampedReference.getStamp());

        Book mysqlBook = new Book(2, "mysqlBook");

        boolean b;
        b = stampedReference.compareAndSet(javaBook, mysqlBook, stampedReference.getStamp(), stampedReference.getStamp() + 1);
        System.out.println(b + "\t" + stampedReference.getReference() + "\t" + stampedReference.getStamp());

        b = stampedReference.compareAndSet(mysqlBook, javaBook, stampedReference.getStamp(), stampedReference.getStamp() + 1);
        System.out.println(b + "\t" + stampedReference.getReference() + "\t" + stampedReference.getStamp());
    }
}
