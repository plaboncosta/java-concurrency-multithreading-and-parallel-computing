package src.threadintercommunication;

public class SynchronizationProblems {
    private static int counter1 = 0;
    private static int counter2 = 0;

    // usually it is not a good practice to use synchronize keyword
    public static void increment1() {
        // class level intrinsic lock
        // rule of thumb : we synchronize block that are 100% necessary
        synchronized (SynchronizationProblems.class){
            counter1++;
        }
    }

    public static void increment2() {
        synchronized (SynchronizationProblems.class) {
            counter2++;
        }
    }

    public static void process() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 20000; i++) {
                increment1();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 20000; i++) {
                increment2();
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

        System.out.println("Counter1 value : " + counter1);
        System.out.println("Counter2 value : " + counter2);
    }

    public static void main(String[] args) {
        process();
    }
}
