package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import utils.Log;

public class MessageQueue
{
    private Object[] mQueue;
    private int head = 0;
    private int tail = 0;
    private Lock lock;
    private Condition notFull;
    private Condition notEmpty;
    private long k = 0;
    
    public MessageQueue(int maxsize)
    {
        if(maxsize<0)
        {
            maxsize = 0;
        }
        mQueue = new Object[maxsize];
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }
    
    private int capatity()
    {
        if(mQueue == null)
        {
            return 0;
        }
        else
        {
            return mQueue.length;
        }
    }
    
    public void put(Object obj)
    {

        lock.lock();
        obj = String.valueOf(k++);
        if(obj == null)
        {
            return;
        }
        
        
        try
        {
            while((tail +1)%capatity() == head)
            {
                notFull.await();
            }
            tail = ++tail % capatity();
            mQueue[tail] = obj;

            Log.d("put obj["+(String)obj+"]");
            notEmpty.signalAll();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }
    
    public Object take()
    {
        Object obj = null;
        lock.lock();
        try
        {
            while(head == tail)
            {
                notEmpty.await();
            }
            
            obj = mQueue[head];
            head = ++head % capatity();

            //Log.d("take obj["+(String)obj+"]");
            
            notFull.signalAll();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
        
        return obj;
    }
    
    

    /**
     * @param args
     */
    public static void main(String[] args)
    { 
        final int THREAD_POOL_SIZE = 100;
        ExecutorService es = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        MessageQueue mQueue = new MessageQueue(10);

        for(int i = 1; i <= 10; ++i) {
            es.execute(new Consumer(mQueue));
        }
        for(int i = 1; i <= 10; ++i) {
            es.execute(new Producer(mQueue));
        }
        
//
//        for(int i=0; i<10; i++)
//        {
//            es.execute(new Consumer(mQueue));
//            es.execute(new Producer(mQueue));
//        }
        
       

    }
    
    

}


class Consumer implements Runnable
{
    private MessageQueue mQueue;
    private boolean isStop = false;
    public Consumer(MessageQueue mQueue)
    {
        this.mQueue = mQueue;
    }
    
    

    public void setStop(boolean isStop)
    {
        this.isStop = isStop;
    }



    @Override
    public void run()
    {
        while(!isStop)
        {
            if(mQueue != null)
            {
                mQueue.take();
            }
        }
    }
    
}

class Producer implements Runnable
{
    private MessageQueue mQueue;
    private boolean isStop = false;
    public Producer(MessageQueue mQueue)
    {
        this.mQueue = mQueue;
    }
    
    

    public void setStop(boolean isStop)
    {
        this.isStop = isStop;
    }



    @Override
    public void run()
    {
        while(!isStop)
        {
            if(mQueue != null)
            {
                mQueue.put(null);
            }
        }
    }
    
}
