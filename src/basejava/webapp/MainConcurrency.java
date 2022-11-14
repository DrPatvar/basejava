package basejava.webapp;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int a = 5;
    private  static int b =7;

    public static int minus(){
        return a + sum();
    }
    public static int sum(){
        return b + minus();
    }


    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
                minus();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
                sum();
            }
        });
        thread1.start();
        thread2.start();

    }

}
