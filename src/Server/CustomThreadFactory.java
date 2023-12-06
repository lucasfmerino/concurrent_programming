package Server;

import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {
    private static int threadNumber = 1;

    @Override
    public Thread newThread(Runnable r) {

        Thread thread = new Thread(r, "Thread Tasks Server " +  threadNumber);

        threadNumber ++;

        thread.setUncaughtExceptionHandler(new ExceptionHandler());

        return thread;
    }
    
}
