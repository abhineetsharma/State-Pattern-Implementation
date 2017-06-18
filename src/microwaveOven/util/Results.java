package microwaveOven.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;


public class Results implements StdoutDisplayInterface, FileDisplayInterface {

    private StringBuilder stringBuilderStorage = new StringBuilder();
    private String outputPath;

    public Results(String path) {

        this.outputPath = path;
    }

    public void storeNewResult(Object obj) {
        String str = obj.toString();
        str = String.format("%s%s", str, "\n");

        stringBuilderStorage.append(str);
    }

    private String getStoredString() {
        return stringBuilderStorage.toString().toString();
    }

    @Override
    public void writeToStdout() {
        System.out.println(getStoredString());
    }

    @Override
    public void writeToFile() {

        File file;
        try {
            if (null != outputPath) {
                file = new File(outputPath);

                if (file.exists() && !file.isDirectory()) file.delete();

                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(outputPath), "utf-8"))) {
                    String str = getStoredString();
                    writer.write(str);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in printing stored string into the output file");
            System.exit(0);
        }


    }

    @Override
    public String toString() {
        String className = this.getClass().getName();
        String description = "This class has a data structure as private data member that store Strings and it implements FileDisplayInterface and StdoutDisplayInterface";
        String str = String.format("\nClass : %s\nMethod toString()\nDescription : %s\nPrivate variable :\noutputPath value is : %s\nstringBuilderStorage value is: %s\n", className, description, outputPath, getStoredString());
        System.out.println(str);
        return str;
    }
}