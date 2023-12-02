package Server;

import java.net.Socket;
import java.util.Scanner;

public class DistributeTasks implements Runnable {

    private Socket socket;

    public DistributeTasks(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            System.out.println("Distribuindo tarefas para" + socket);

            Scanner inputClient = new Scanner(socket.getInputStream());

            while(inputClient.hasNextLine()) {
                String command = inputClient.nextLine();
                System.out.println(command);
            }

            inputClient.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
