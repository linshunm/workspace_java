package thread;

import utils.Log;
import utils.ThreadUtil;

public class ThreadLocalTest implements Runnable
{
    private static final ThreadLocal<String> threadSession = new ThreadLocal<String>();
    private final int id;
    public ThreadLocalTest(int i)
    {
        id = i;
        threadSession.set(String.valueOf(i));
    }
    
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(10);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Object o = threadSession.get();
        if(o != null)
        {
            Log.d(id + ":" + (String)o);
        }
        else
        {
            Log.d(id + ": get null");
        }
        
    }
    
    
    public static void main(String[] args) 
    {
        for(int i=0; i<10; i++)
        {
            ThreadUtil.execTask(new ThreadLocalTest(i));
        }
    }

}
