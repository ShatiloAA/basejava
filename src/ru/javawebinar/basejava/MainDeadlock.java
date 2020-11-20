package ru.javawebinar.basejava;

public class MainDeadlock {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        Thread thread0 = getThread(LOCK1, LOCK2);
        Thread thread1 = getThread(LOCK2, LOCK1);

        thread0.start();
        thread1.start();
    }

    private static Thread getThread(Object lock1, Object lock2) {
        return new Thread(() -> {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + ", locked " + lock1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName()  + ", locked " + lock2);
                }
            }

        });
    }


}
