package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import thread.MyThreadFactory;

public class ThreadUtil
{
    private static ExecutorService es = Executors.newCachedThreadPool(new MyThreadFactory());
    
    public static void execTask(Runnable r)
    {
        es.execute(r);
    }
}
