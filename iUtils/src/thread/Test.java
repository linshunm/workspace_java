package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test
{

    /**
     * @param args
     */
    /**
     * @param args
     */
    /**
     * @param args
     */
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        //ExecutorService es = Executors.newCachedThreadPool();
        ExecutorService es = Executors.newCachedThreadPool(new MyThreadFactory());
        IAction action = new MyAction();
        for(int i=0; i<10; i++)
        {
            es.execute(new ActionRunnable(i,action));
        }        
        es.shutdown();
    }

}
