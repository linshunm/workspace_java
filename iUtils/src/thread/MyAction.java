package thread;

import utils.Log;

public class MyAction implements IAction
{
    private int count;
    @Override
    public synchronized String get(String key)
    {
        Log.d("get-"+key);
        return "get-"+key;
    }

    @Override
    public synchronized void doo(String key)
    {
        Log.d("doo-"+key);
    }
    
    public synchronized int next()
    {
        count++;
        Thread.yield();
        count++;
        return count;
    }

}
