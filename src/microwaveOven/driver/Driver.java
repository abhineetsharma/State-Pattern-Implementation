package microwaveOven.driver;
import microwaveOven.service.MicrowaveContext;
import microwaveOven.util.FileProcessor;
import microwaveOven.util.Logger;
import microwaveOven.util.Results;

public class Driver {
    public static void main(String[] args) {
        try {
            if (null != args && args.length > 0) {
                String inputFile = args[0];
                Logger.log(String.format("Input File: %s", inputFile));
                FileProcessor fileProcessor = new FileProcessor(inputFile);

                String outputFile = null;
                if (args.length > 1) {
                    outputFile = args[1];
                    Logger.log(String.format("Output File: %s", outputFile));
                }
                Results results = new Results(outputFile);
                MicrowaveContext microWave = new MicrowaveContext(results);
                String str;
                while ((str = fileProcessor.readLine()) != null) {
                    microWave.action(str);
                }
                results.writeToStdout();
                if (null != outputFile)
                    results.writeToFile();

            } else {
                System.out.println("No arguments pass, Input file needed for execution");
                System.exit(1);
            }
        } finally {
            Logger.stopLogging();
        }
    }
}
