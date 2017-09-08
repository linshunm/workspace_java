package thread;

import java.lang.Thread.UncaughtExceptionHandler;

import utils.Log;

public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler
{

    @Override
    public void uncaughtException(Thread thread, Throwable throwable)
    {
        Log.d("thread["+thread.getName()+"] throwable["+throwable+"]");
    }

}
