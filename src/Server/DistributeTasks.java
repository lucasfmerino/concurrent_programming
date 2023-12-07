package Server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistributeTasks implements Runnable {

    private Socket socket;
    private ServerTasks server;
    private ExecutorService threadPool;

    public DistributeTasks(ExecutorService threadPool, Socket socket, ServerTasks server) {
        this.socket = socket;
        this.server = server;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        try {

            System.out.println("Distribuindo tarefas para" + socket);

            Scanner clientInput = new Scanner(socket.getInputStream());

            PrintStream clientOutput = new PrintStream(socket.getOutputStream());

            while (clientInput.hasNextLine()) {
                String command = clientInput.nextLine();
                System.out.println("[" + socket.getPort() + "]: " + command);

                switch (command) {
                    case "c1": {
                        clientOutput.println("Command c1 confirmation");
                        CommandC1 c1 = new CommandC1(clientOutput);
                        this.threadPool.execute(c1);
                        break;
                    }

                    case "c2": {
                        clientOutput.println("Command c2 confirmation");
                        CommandC2CallWS c2WS = new CommandC2CallWS(clientOutput);
                        CommandC2Database c2DB = new CommandC2Database(clientOutput);
                        Future<String> futureWS = this.threadPool.submit(c2WS);
                        Future<String> futureDB = this.threadPool.submit(c2DB);

                        this.threadPool.submit(new FinalResult(futureWS, futureDB, clientOutput));

                        break;
                    }

                    case "close server": {
                        clientOutput.println("Shutting down the server.");
                        server.stopServer();
                        // System.exit(0);
                        break;
                    }

                    default:
                        clientOutput.println("Command: " + command + " not found.");
                        break;
                }

            }

            clientOutput.close();
            clientInput.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
