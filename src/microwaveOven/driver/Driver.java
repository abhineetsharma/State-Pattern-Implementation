package microwaveOven.driver;

import microwaveOven.util.Results;

public class Driver
{

    public static void main(String[] args)
    {
        System.out.println("Hello World");
        Results results = new Results(null);
        results.storeNewResult("Hello");
        results.writeToStdout();
        results.writeToFile();
    }

}
