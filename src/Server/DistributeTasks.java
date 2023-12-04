package Server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class DistributeTasks implements Runnable {

    private Socket socket;
    private ServerTasks server;

    public DistributeTasks(Socket socket, ServerTasks server) {
        this.socket = socket;
        this.server = server;
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
                        break;
                    }

                    case "c2": {
                        clientOutput.println("Command c2 confirmation");
                        break;
                    }

                    case "close server": {
                        clientOutput.println("Shutting down the server.");
                        server.stopServer();
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
