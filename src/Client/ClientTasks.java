package Client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTasks {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 12345);

        System.out.println("Conexão estabelecida");

        Thread threadCommands = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    PrintStream output = new PrintStream(socket.getOutputStream());
                    Scanner keyboard = new Scanner(System.in);
                    while (keyboard.hasNextLine()) {
                        String line = keyboard.nextLine();

                        if (line.trim().equals("")) {
                            break;
                        }

                        output.println(line);
                    }

                    output.close();
                    keyboard.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        Thread threadServerResponse = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    System.out.println("Recebendo dados do Servidor:");

                    Scanner serverResponse = new Scanner(socket.getInputStream());
                    while (serverResponse.hasNextLine()) {
                        String line = serverResponse.nextLine();
                        System.out.println(line);
                    }

                    serverResponse.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        threadServerResponse.start();
        threadCommands.start();

        threadServerResponse.join();
        System.out.println("Fechando o socket do cliente");
        socket.close();

    }
}
