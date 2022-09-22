package basejava.webapp;

public class TestSingleton {
    private static TestSingleton instance;

    public static TestSingleton getInstance(){
        if (instance == null){
            instance = new TestSingleton();
        }
        return instance;
    }
    private TestSingleton() {
    }

    public static void main(String[] args) {
        TestSingleton.getInstance();
        Singleton singleton = Singleton.valueOf("INSTANCE");
        System.out.println(singleton.ordinal());
    }
    public enum Singleton{
        INSTANCE;
    }
}
