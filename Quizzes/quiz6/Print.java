package multithreading;

// What is wrong with the program below?  How would you fix it?

public class Print {

    Object printer = new Object();
    Object paper = new Object();
    Object pp = new Object();
    public Print() {
        Thread t1 = new Thread(new Task1());
        Thread t2 = new Thread(new Task2());
        t1.start();
        t2.start();
        try {
            t1.join();
            Thread.sleep(10000);
            t2.join();
            System.out.println("There are " + Thread.activeCount() +
                    " thread(s) still running.  Should be only 1.");
        } catch (InterruptedException e) {
        }
    }

    class Task1 implements Runnable {
        @Override
        public void run() {
            synchronized (pp) {
                System.out.println("Got printer");
                System.out.println("Got paper also; now I can print.");

            }
        }
    }

    class Task2 implements Runnable {
        @Override
        public void run() {
            synchronized (pp) {
                System.out.println("Got paper");
                System.out.println("Got printer also; now I can print.");

            }
        }
    }

    public static void main(String[] args) {
        new Print();
    }
}
