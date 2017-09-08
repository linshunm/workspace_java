package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import utils.Log;

public class CountDownLatchTest
{
    
    

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        ExecutorService es = Executors.newFixedThreadPool(2);
        final CountDownLatch latch = new CountDownLatch(1);
        
        for(int i=0; i<1; i++)
        {
            es.execute(new Runnable(){

                @Override
                public void run()
                {
                    try
                    {
                        Log.d("do job");
                        TimeUnit.SECONDS.sleep(5);
                        latch.countDown();//¼ÆÊý¼õÒ»
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    Log.d("do job finish");
                }});
        }
        
        es.execute(new Runnable(){

            @Override
            public void run()
            {
                try
                {
                    Log.d("do last");
                    latch.await();
                    Log.d("do last finish");
                    
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }});

    }

}
