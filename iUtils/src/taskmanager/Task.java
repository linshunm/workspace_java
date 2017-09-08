package taskmanager;

import utils.Log;

abstract public class Task
{
    protected String rqId;//请求队列Id
    protected String taskId;//任务Id
    private long timeout = 2 * 60 * 1000;//默认两分钟
    
    public Task(String rqId, String taskId)
    {
        this.rqId = rqId;
        this.taskId = taskId;
    }
    
    private long startTaskTime = 0L;
    private boolean isDoing = false;
    
    public String getRqId()
    {
        return rqId;
    }
    public String getTaskId()
    {
        return taskId;
    }
    public long getTimeout()
    {
        return timeout;
    }
    public void setTimeout(long timeout)
    {
        this.timeout = timeout;
    }
    
    
    public boolean isDoing()
    {
        return isDoing;
    }
    public void setDoing(boolean isDoing)
    {
        this.isDoing = isDoing;
    }
    protected void doTask()
    {
        isDoing = true;
        startTaskTime = System.currentTimeMillis();
    }
    
    protected void callback(RspObj data)
    {        
        isDoing = false;
        RqManager.getIns().removeTask(rqId, taskId);
        Log.d("callback data["+data+"]");
    }
    
    public boolean isTimeOut()
    {
        long currentTime = System.currentTimeMillis();
        //Log.d("isDoing["+isDoing+"] currentTime["+currentTime+"] startTaskTime["+startTaskTime+"] timeout["+timeout+"]");
        return isDoing && (currentTime-startTaskTime > timeout);
    }
    
    
    
}
