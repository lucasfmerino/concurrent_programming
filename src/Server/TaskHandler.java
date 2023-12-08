package Server;

import java.util.concurrent.BlockingQueue;

public class TaskHandler implements Runnable {

    private BlockingQueue<String> commandQueue;

    public TaskHandler(BlockingQueue<String> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {

        try {

                String command = null;
                
                while((command = commandQueue.take()) != null) {

                    System.out.println("Consuming command: " + command + ", " + Thread.currentThread().getName());
                    Thread.sleep(8000);
                }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
