package Server;

import java.io.PrintStream;

public class CommandC2 implements Runnable {
    private PrintStream output;

    public CommandC2(PrintStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        System.out.println("C2 command - Running");

        try{
            Thread.sleep(20000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }

        output.println("C2 command - Success!");
    }
    
}
