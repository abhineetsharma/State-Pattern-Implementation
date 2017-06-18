package microwaveOven.util;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;


public class FileProcessor {

    private String inputPath;
    private File file;
    private BufferedReader br;

    //constructor
    public FileProcessor(String path) {
        this.inputPath = path;
        getInitializedFileObject();
    }



    private BufferedReader getInitializedBufferedReaderObject(FileInputStream fStream){
        return new BufferedReader(new InputStreamReader(fStream));
    }

    private void getInitializedFileObject(){
        br = null;
        String path = inputPath;
        file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            try {
                br = getInitializedBufferedReaderObject(new FileInputStream(file));
            } catch (IOException ex) {
                if (br != null) try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        } else {
            System.out.println("input File not found");
            System.exit(0);
        }
    }



    //get the one at a time from the input file
    public String readLine() {
        if (file.exists() && !file.isDirectory()) {
            String strLine;
            try {
                if ((strLine = br.readLine()) != null)
                    return strLine.trim();
                else {
                    br.close();
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String toString(){
        String className = this.getClass().getName();
        String description = "This class has a method String readLine(...), which returns one line at a time from a file.";
        String str = String.format("Class : %s\nMethod toString()\nDescription : %s\nPrivate variable inputPath value is : %s\n",className,description,inputPath);
        System.out.println(str);
        return str;
    }


}