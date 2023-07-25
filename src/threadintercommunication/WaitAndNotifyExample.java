package src.threadintercommunication;

import java.util.ArrayList;

class Processor {
    private ArrayList<Integer> list = new ArrayList<>();
    private static final int UPPER_LIMIT = 5;
    private static final int LOWER_LIMIT = 0;
    private int counter = 0;
    private final Object lock = new Object();

    public void producer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == UPPER_LIMIT) {
                    System.out.println("Removing items ...");
                    lock.wait();
                } else {
                    System.out.println(counter);
                    list.add(counter);
                    counter++;
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == LOWER_LIMIT) {
                    counter = 0;
                    System.out.println("Adding items ...");
                    lock.wait();
                } else {
                    System.out.println(list.remove(list.size() - 1));
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }
}

public class WaitAndNotifyExample {
    public static void main(String[] args) {
        Processor processor = new Processor();

        Thread t1 = new Thread(() -> {
            try {
                processor.producer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                processor.consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
    }
}
