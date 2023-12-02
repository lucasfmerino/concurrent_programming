package Client;

import java.net.Socket;
import java.util.Scanner;

public class ClientTasks {
    public static void main(String[] args) throws Exception {
        
        Socket socket = new Socket("localhost", 12345);
        
        System.out.println("Conexão estabelecida");

        Scanner keyboard = new Scanner(System.in);
        keyboard.nextLine();

        socket.close();
    }
}
