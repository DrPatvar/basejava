package basejava.webapp;

public class MainConcurrency {

    public static void main(String[] args) throws InterruptedException {
        final String resource1 = "resource1";
        final String resource2 = "resource2";
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
                synchronized (resource1) {
                    System.out.println("Thread0: занял ресурс_1");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (resource2) {
                        System.out.println("Thread0: занял ресурс_2");
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
                synchronized (resource2) {
                    System.out.println("Thread1: занял ресурс_1");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (resource1) {
                        System.out.println("Thread1: занял ресурс_2");
                    }
                }
            }
        });
        thread1.start();
        //thread1.join();
        thread2.start();
    }
}
