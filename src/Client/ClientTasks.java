package Client;

import java.net.Socket;

public class ClientTasks {
    public static void main(String[] args) throws Exception {
        
        Socket socket = new Socket("localhost", 12345);
        
        System.out.println("Conexão estabelecida");

        socket.close();
    }
}
