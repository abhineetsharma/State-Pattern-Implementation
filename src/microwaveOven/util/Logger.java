package microwaveOven.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;

public class Logger {
    private static StringBuilder log = new StringBuilder();

    public static boolean errors = false;

    public static void storeNewResult(Object obj) {
        errors = true;
        String str = obj.toString();
        str = String.format("%s%s", str, "\n");
        log.append(str);
        writeToStdout();
    }

    private static String getStoredLog() {
        return log.toString().trim();
    }



    public static void writeToStdout() {
        System.out.println(getStoredLog());
    }


    public static void writeToFile() {
        String outputPath = "";
        File file;
        try {
            if (null != outputPath) {
                file = new File(outputPath);

                if (file.exists() && !file.isDirectory()) file.delete();

                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(outputPath), "utf-8"))) {
                    String str = getStoredLog();
                    writer.write(str);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error in printing of stored string into the output file");
            System.exit(0);
        }


    }
}