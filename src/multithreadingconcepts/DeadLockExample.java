package src.multithreadingconcepts;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockExample {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void worker1() {
        lock1.lock();
        System.out.println("Worker1 using lock1");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock2.lock();
        System.out.println("Worker1 using lock2");
        lock1.unlock();
        lock2.unlock();
    }

    public void worker2() {
        lock1.lock();
        System.out.println("Worker2 using lock1");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        lock2.lock();
        System.out.println("Worker2 using lock2");
        lock1.unlock();
        lock2.unlock();
    }

    public static void main(String[] args) {
        DeadLockExample deadLockExample = new DeadLockExample();

        new Thread(deadLockExample::worker1, "worker1").start();
        new Thread(deadLockExample::worker2, "worker2").start();
    }
}
