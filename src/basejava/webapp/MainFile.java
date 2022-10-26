package basejava.webapp;

import java.io.File;

public class MainFile {
    static String repeat(int n, String value) {
        return new String(new char[n]).replace("\0", value);
    }

    public static void listFile(File file, int count) {
        final String space = repeat(count, "    ");
        File[] files = file.listFiles();
        for (File f : files
        ) {
            if (f.isDirectory()) {
                System.out.println( space + "Directory: " + f.getName());
                listFile(f,count +1);
            } else {
                System.out.println(space + "File: " + f.getName());
            }
        }
    }


    public static void main(String[] args) {
        File path = new File("./src/");
        listFile(path,0);

    }
}
