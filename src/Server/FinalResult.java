package Server;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// public class FinalResult implements Runnable {
public class FinalResult implements Callable<Void> {

    private Future<String> futureWS;
    private Future<String> futureDB;
    private PrintStream clientOutput;

    public FinalResult(Future<String> futureWS, Future<String> futureDB, PrintStream clientOutput) {
        this.futureWS = futureWS;
        this.futureDB = futureDB;
        this.clientOutput = clientOutput;
    }

    @Override
    public Void call() {

        System.out.println("\r\n" + //
                "Waiting for final result.");

        try {
            String futureNumberWS = this.futureWS.get(20, TimeUnit.SECONDS);
            String futureNumberDB = this.futureDB.get(20, TimeUnit.SECONDS);

            this.clientOutput.println("C2 final result: " + futureNumberWS + ", " + futureNumberDB);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Canceling execution");
            this.clientOutput.println("Timeout: C2 execution exceeded the maximum allowed time.");
            this.futureWS.cancel(true);
            this.futureDB.cancel(true);
        }
        


        return null;
    }
    
}
