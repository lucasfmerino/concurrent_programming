package Server;

import java.io.PrintStream;

public class CommandC1 implements Runnable {
    private PrintStream output;

    public CommandC1(PrintStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        System.out.println("C1 command - Running");

        try{
            Thread.sleep(20000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }

        output.println("C1 command - Success!");
    }
    
}
