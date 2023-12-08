package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerTasks {

    private ExecutorService threadPool;
    private ServerSocket server;
    // private volatile boolean running;
    private AtomicBoolean running;
    private BlockingQueue<String> commandQueue;

    public ServerTasks() throws IOException {
        System.out.println(" --- Iniciando Servidor --- ");
        this.server = new ServerSocket(12345);
        this.threadPool = Executors.newCachedThreadPool(new CustomThreadFactory());
        // this.threadPool = Executors.newFixedThreadPool(4, new CustomThreadFactory());
        this.running = new AtomicBoolean(true);
        this.commandQueue = new ArrayBlockingQueue<>(2);
        startConsumers();
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

                DistributeTasks distributeTasks = new DistributeTasks(threadPool, commandQueue, socket, this);
                threadPool.execute(distributeTasks);

            } catch (SocketException e) {
                System.out.println("SocketException. Running: " + this.running);
            }

        }

    }

    private void startConsumers() {
        int maxConsumers = 2;
        for(int i = 0; i < maxConsumers; i++) {
            TaskHandler task = new TaskHandler(commandQueue);
            this.threadPool.execute(task);
        }
    }

    public void stopServer() throws IOException {
        // running = false;
        this.running.set(false);
        server.close();
        threadPool.shutdown();

    }
}
