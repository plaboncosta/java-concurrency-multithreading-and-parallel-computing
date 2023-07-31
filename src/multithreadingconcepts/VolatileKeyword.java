package src.multithreadingconcepts;


class Worker implements Runnable {
    // It will be stored in the main memory
    private volatile boolean isTerminated = false;

    @Override
    public void run() {
        while (!isTerminated) {
            System.out.println("Worker is running ....");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setTerminated(boolean isTerminated) { // this parameter example
        this.isTerminated = true;
    }
}

public class VolatileKeyword {
    public static void main(String[] args) {
        Worker worker = new Worker();

        Thread t1 = new Thread(worker);

        t1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        worker.setTerminated(true); // this is argument example
        System.out.println("Process is finished!");
    }
}
