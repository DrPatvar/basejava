package basejava.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public void listFile(File dir) {
        if (dir.isDirectory()){
            File[] list = dir.listFiles();
            for (File file: list
                 ) {
                if (file.isDirectory()){
                    listFile(file);
                }
                System.out.println(file.getName());
            }
        }
    }

    public static void main(String[] args) {
       File path = new File("./src/basejava/webapp");
        
       String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        MainFile mainFile = new MainFile();
        mainFile.listFile(path);

    }
}
