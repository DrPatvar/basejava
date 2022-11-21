package basejava.webapp;

public class MainConcurrency {
    private void methodDeadLock(String str, String str2) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            synchronized (str) {
                System.out.println(Thread.currentThread().getName() + " занял ресурс_1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (str2) {
                    System.out.println(Thread.currentThread().getName() + " занял ресурс_2");
                }
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        final String resource1 = "resource1";
        final String resource2 = "resource2";
        MainConcurrency mainConcurrency = new MainConcurrency();
        mainConcurrency.methodDeadLock(resource1, resource2);
        mainConcurrency.methodDeadLock(resource2, resource1);
    }

}
