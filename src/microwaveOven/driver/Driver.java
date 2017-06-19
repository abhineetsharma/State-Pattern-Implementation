package microwaveOven.driver;

import microwaveOven.service.MicrowaveContext;
import microwaveOven.util.Results;

public class Driver
{

    public static void main(String[] args)
    {
        MicrowaveContext microWave = new MicrowaveContext();
        microWave.action("1");
        System.out.println("Hello World");
        Results results = new Results(null);
        results.storeNewResult("Hello");
        results.writeToStdout();
        results.writeToFile();
    }

}
