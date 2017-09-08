package thread;

import utils.Log;

public class ActionRunnable implements Runnable
{
    private final int id;
    private IAction action;
    public ActionRunnable(int id, IAction action)
    {
        this.id = id;
        this.action = action;
    }
    @Override
    public void run()
    {
        while(action.next()%2 == 0)
        {
            action.get("ActionRunnable"+id);
        }
        Log.d("ActionRunnable["+id+"] next["+action.next()+"]");
    }

}
