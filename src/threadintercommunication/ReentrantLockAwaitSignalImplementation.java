package src.threadintercommunication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class Worker {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void producer() throws InterruptedException {
        lock.lock();
        System.out.println("Producer method");
        condition.await();
        System.out.println("Producer method again");
        lock.unlock();
    }

    public void consumer() throws InterruptedException {
        lock.lock();
        Thread.sleep(2000);
        System.out.println("Executed consumer");
        condition.signal();
        lock.unlock();
    }
}

public class ReentrantLockAwaitSignalImplementation {

    public static void main(String[] args) {
        Worker worker = new Worker();

        Thread t1 = new Thread(() -> {
            try {
                worker.producer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                worker.consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("All process done");
    }
}
