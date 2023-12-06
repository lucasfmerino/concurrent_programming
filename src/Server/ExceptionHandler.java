package Server;

import java.lang.Thread.UncaughtExceptionHandler;

public class ExceptionHandler implements UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("An exception occurred in the thread" + t.getName() + ", " + e.getMessage());
    }


}
