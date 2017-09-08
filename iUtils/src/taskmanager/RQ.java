package taskmanager;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import utils.Log;
import utils.ThreadUtil;

public class RQ
{
    private ScheduledExecutorService scheduExec = null;
    private boolean isRunning = false;
    private Lock lock;
    private Condition doTask;
    private final int MAX_REQ;
    private final String rqId;
    private int count = 0;
    public RQ(String rqId, int maxReq)
    {
        Log.d("new TaskManager rqId["+rqId+"] maxReq["+maxReq+"]");
        
        this.rqId = rqId;
        if(maxReq>0)
        {
            MAX_REQ = maxReq;
        }
        else
        {
            MAX_REQ = 1;
        }
        lock = new ReentrantLock();
        doTask = lock.newCondition();
        
        isRunning = false;
    }
       
    private LinkedList<Task> mq = new LinkedList<Task>();
    
    private void loop()
    {
        Log.d("start loop...");
        while(isRunning)
        {
            lock.lock();
            try
            {
                while(count==MAX_REQ)
                {
                    try
                    {
                        Log.d("loop await count["+count+"] mq size["+mq.size()+"]");
                        doTask.await();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                if(count < mq.size() && !mq.isEmpty())
                {
                    final Task task =  mq.get(count);
                    ThreadUtil.execTask(new Runnable(){
                        public void run()
                        {
                            task.doTask();
                        }
                    });
                    count++;
                    
                    Log.d("rqId["+rqId+"] loop do task["+ task +"] count["+ count +"]");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                lock.unlock();
            }            
        }
        Log.d("loop finished");
    }
    
    public void finishTask(final String taskId)
    {
        //Log.d("rqId["+rqId+"] finish task["+taskId+"]");
        ThreadUtil.execTask(new Runnable(){
            public void run()
            {
                removeTask(taskId);
            }
        });
    }
    
    
    public void newTask(final Task task)
    {
        //Log.d("rqId["+rqId+"] new task["+task+"]");
        ThreadUtil.execTask(new Runnable(){
            public void run()
            {
                addTask(task);
                startTimer();
            }
        });
    }
    
    public void callback(final RspObj rspObj)
    {
        for(int i = 0; i<mq.size(); i++)
        {
            final Task task = mq.get(i);
            String taskId = rspObj.getTaskId();
            if(taskId.equals(task.getTaskId()))
            {
                ThreadUtil.execTask(new Runnable(){
                    public void run()
                    {
                        task.callback(rspObj);
                    }
                });
                break;
            }
        }
    }
    
    public void reTry()
    {
        lock.lock();
        try
        {
            Log.d("rqId["+rqId+"] reTry");
            count = 0;
            doTask.signalAll();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }
    
    
    private void addTask(Task task)
    {
        lock.lock();
        try
        {
            Log.d("rqId["+rqId+"] add task["+task+"]");
            mq.addLast(task);
            doTask.signalAll();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }
    
    private void removeTask(String taskId)
    {
        lock.lock();
        try
        {
            int index = -1;
            for(int i = 0; i<mq.size(); i++)
            {
                if(taskId.equals(mq.get(i).getTaskId()))
                {
                    index = i;
                    break;
                }
            }
            if(index != -1)
            {
                Task task = mq.remove(index);
                count--;
                Log.d("rqId["+rqId+"] remove task["+task+"]");
                doTask.signalAll();
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }
    
    private Runnable timerRunnable = new Runnable()
    {
        public void run()
        {
            boolean isHashTask = false;
            try
            {
                //Log.d("task timeout check ...");
                isRunning = true;
                
                for(int i = 0; i<mq.size(); i++)
                {
                    isHashTask = true;
                    final Task task = mq.get(i);
                    if(task.isTimeOut())
                    {
                        ThreadUtil.execTask(new Runnable(){
                            public void run()
                            {
                                task.callback(new RspObj(task.getTaskId(), "408"));
                            }
                        });
                    }
                }
            }
            catch (Exception e)
            {
                isRunning = false;
                e.printStackTrace();
            }

            
            if(!isHashTask)
            {
                Log.d("all tasks finished");
                isRunning = false;
                rqDeleted();
                doTask.signalAll();
            }
        }
    };

    /** 启动定时器任务 */
    private synchronized void startTimer()
    {
        if(isRunning)
        {
            Log.d("timer has started");
            return;
        }
        Log.d("timer start ...");
        
        isRunning = true;
        if (scheduExec == null)
        {
            scheduExec = Executors.newSingleThreadScheduledExecutor();
        }

        if (!scheduExec.isShutdown())
        {
            //延迟0秒钟执行，每间隔1秒执行一次
            scheduExec.scheduleAtFixedRate(timerRunnable, 0, 1, TimeUnit.SECONDS);
            Log.d("timer running ...");
        }
        else
        {
            Log.d("scheduExec.isShutdown true，so start timer failed.");
        }
        

        
        ThreadUtil.execTask(new Runnable(){
            public void run()
            {
                loop();
            }
        });

    }

    /**
     * 停止定时器
     */
    private void rqDeleted()
    {
        Log.d("rq stop.");
        if (scheduExec != null)
        {
            if (!scheduExec.isShutdown())
            {
                scheduExec.shutdown();
                scheduExec = null;
                Log.d("timer stop.");
            }
        }
    }
}
