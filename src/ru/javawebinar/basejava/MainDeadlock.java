package ru.javawebinar.basejava;

public class MainDeadlock {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        getThread(LOCK1, LOCK2);
        getThread(LOCK2, LOCK1);
    }

    private static void getThread(Object lock1, Object lock2) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ", wait " + lock1);
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + ", locked " + lock1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ", wait " + lock2);
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName()  + ", locked " + lock2);
                }
            }
        }).start();
    }
}
