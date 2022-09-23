package basejava.webapp;

import basejava.webapp.model.SectionType;

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
       // System.out.println(singleton.ordinal());
        for (SectionType type: SectionType.values()){
            System.out.println(type.getTitle());
        }
    }
    public enum Singleton{
        INSTANCE;
    }
}
