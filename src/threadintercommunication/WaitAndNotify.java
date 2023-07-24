package src.threadintercommunication;

class Process {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Running the produce method");
            wait();
            System.out.println("Again running the produce method");
        }
    }

    public void consume() throws InterruptedException {
        synchronized (this) {
            System.out.println("Executed the consumer");
            notify();
            Thread.sleep(5000);
        }
    }
}

public class WaitAndNotify {
    public static void main(String[] args) {
        Process process = new Process();

        Thread t1 = new Thread(() -> {
            try {
                process.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                process.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
    }
}
