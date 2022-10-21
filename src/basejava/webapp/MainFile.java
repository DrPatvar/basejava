package basejava.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {
    private static int count = 0;

    public static   void listFile(File dir) {
        int count = 0;
       final String level = new String(new char[count]).replace("\0", "  ");
        File[] list = dir.listFiles();
        if (list != null) {
            for (File file : list
            ) {
                if (file.isFile()) {
                    System.out.println ("File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println (level + "Directory: " + file.getName());
                    listFile(file);
                    count++;
                }
            }
        }
    }


    public static void main(String[] args) {
        File path = new File("./src/");
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        listFile(path);
    }
}
