package Server;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;


public class CommandC2Database implements Callable<String> {
    private PrintStream output;

    public CommandC2Database(PrintStream output) {
        this.output = output;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Server received C2 command DB - Running");

        output.println("Running C2 command DB");
        Thread.sleep(15000);

        int number = new Random().nextInt(100) + 1;

        System.out.println("Server finished C2 command DB.");

        return Integer.toString(number);
    }

    // output.println("C2 command - Success!");
}
