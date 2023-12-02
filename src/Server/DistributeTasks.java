package Server;

import java.net.Socket;

public class DistributeTasks implements Runnable{

    private Socket socket;

    public DistributeTasks(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {

        System.out.println("Distribuindo tarefas");

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
