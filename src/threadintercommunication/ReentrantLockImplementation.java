package src.threadintercommunication;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockImplementation {
    private static int counter = 0;
    private static final ReentrantLock lock = new ReentrantLock(false);

    // ReentrantLock can re lock the method again
    // Has a fairness boolean property which prioritize the thread starvation
    // Is a thread lock it can be checked
    // Get list of waiting threads

    public static void increment() {
        lock.lock();

        try {
            for (int i = 0; i < 20000; i++) {
                counter++;
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(ReentrantLockImplementation::increment);
        Thread t2 = new Thread(ReentrantLockImplementation::increment);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(counter);
    }
}
