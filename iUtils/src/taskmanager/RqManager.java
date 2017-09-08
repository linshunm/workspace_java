package taskmanager;

import java.util.HashMap;

import utils.Log;

public class RqManager
{
    public final static String MQ_MESSAGE = "mq_message";
    public final static String MQ_FILE = "mq_file";

    static private HashMap<String, RQ> rqMap = new HashMap<String, RQ>();
    
    private static RqManager rqMgr;
    
    
    private RqManager()
    {
        
    }
    
    public synchronized static RqManager getIns() 
    {
        if(rqMgr == null)
        {
            rqMgr = new RqManager();
        }
        return rqMgr;
    }
    
    

    public void req(String rqId, Task task)
    {
        synchronized(rqMap)
        {
            RQ rq = rqMap.get(rqId);
            if(rq == null)
            {
                int maxReq = 1;
                if(MQ_MESSAGE.equals(rqId))
                {
                    maxReq = 1;
                }
                rq = new RQ(rqId, maxReq);
                rqMap.put(rqId, rq);
            }
            rq.newTask(task);
        }
    }
    
    public void callback(String rqId, RspObj rspObj)
    {
        synchronized(rqMap)
        {
            RQ rq = rqMap.get(rqId);
            if(rq == null)
            {
                Log.d("rq["+rqId+"] has been remove");
            }
            else
            {
                rq.callback(rspObj);
            }
        }
    }
        
    
    public void removeTask(String rqId, String taskId)
    {
        synchronized(rqMap)
        {
            RQ rq = rqMap.get(rqId);
            if(rq == null)
            {
                Log.d("removeTask rq["+rqId+"] has been remove");
            }
            else
            {
                rq.finishTask(taskId);
            }
        }
    }
    
    
    public void reTry(String rqId)
    {
        synchronized(rqMap)
        {
            RQ rq = rqMap.get(rqId);
            if(rq == null)
            {
                Log.d("reTry rq["+rqId+"] has been remove");
            }
            else
            {
                rq.reTry();
            }
        }
    }
    
 
}
