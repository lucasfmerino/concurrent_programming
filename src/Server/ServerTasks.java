package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerTasks {

    private ExecutorService threadPool;
    private ServerSocket server;
    // private volatile boolean running;
    private AtomicBoolean running;

    public ServerTasks() throws IOException {
        System.out.println(" --- Iniciando Servidor --- ");
        this.server = new ServerSocket(12345);
        this.threadPool = Executors.newFixedThreadPool(4, new CustomThreadFactory());
        this.running = new AtomicBoolean(true);
    }

    public static void main(String[] args) throws Exception {

        ServerTasks server = new ServerTasks();
        server.startServer();
        server.stopServer();

    }

    public void startServer() throws IOException {
        while (this.running.get()) {
            try {
                Socket socket = server.accept();
                System.out.println("Aceitando novo cliente na porta " + socket.getPort());

                DistributeTasks distributeTasks = new DistributeTasks(threadPool, socket, this);
                threadPool.execute(distributeTasks);

            } catch (SocketException e) {
                System.out.println("SocketException. Running: " + this.running);
            }

        }

    }

    public void stopServer() throws IOException {
        // running = false;
        this.running.set(false);
        server.close();
        threadPool.shutdown();

    }
}
