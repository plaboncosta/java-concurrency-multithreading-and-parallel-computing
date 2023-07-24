package src.threadintercommunication;

public class Synchronization {
    private static int counter = 0;

    public static synchronized void increment() {
        counter++;
    }

    public static void process() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 20000; i++) {
                increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 20000; i++) {
                increment();
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

        System.out.println("Counter value : " + counter);
    }

    public static void main(String[] args) {
        process();
    }
}
