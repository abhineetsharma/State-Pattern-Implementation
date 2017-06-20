package microwaveOven.driver;

import microwaveOven.service.MicrowaveContext;
import microwaveOven.util.FileProcessor;
import microwaveOven.util.Results;

public class Driver
{
    public static void main(String[] args)
    {
        Results results = new Results(null);
        MicrowaveContext microWave = new MicrowaveContext(results);

        FileProcessor fileProcessor = new FileProcessor("input.txt");
        String str;
        while((str = fileProcessor.readLine()) != null){
            microWave.action(str);
        }


        results.writeToStdout();

    }

}
