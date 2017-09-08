package thread;

import java.util.concurrent.ThreadFactory;

import utils.Log;

public class MyThreadFactory implements ThreadFactory
{

    @Override
    public Thread newThread(Runnable runnable)
    {
        Thread t = new Thread(runnable);
        Log.d("new thread["+t.getName()+"]");
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        return t;
    }

}
