package thread;

import utils.Log;

public class R1 implements Runnable
{
    private final int id;
    public R1(int id)
    {
        this.id = id;
        Log.d("R1["+id+"]");
    }
    @Override
    public void run()
    {
        int i=3;
        while(i>0)
        {
            Log.d("R1["+id+"]:"+i--);
            Thread.yield();
        }
    }

}
