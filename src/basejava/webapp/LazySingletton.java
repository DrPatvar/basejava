package basejava.webapp;

public class LazySingletton {
    volatile private static LazySingletton INSTANCE;

    private LazySingletton () {
    }
    private static class LazySingletonHolder{
        private static final LazySingletton INSTANCE = new LazySingletton();
    }
    public static LazySingletton getInstance(){
        return LazySingletonHolder.INSTANCE;
    }
}
