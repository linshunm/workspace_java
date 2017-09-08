import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.Log;


public class Test
{
    private static String id = "0";
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Log.d("test"+"999".length());
        
        
//        for(int i=0; i<100; i++)
//        {
//            list.add(i+"");
//        }
//        
//        for(int j=0;j<10;j++)
//        {
//            id = String.valueOf(j);
//            if(j%2 == 0)
//            {
//                new Thread(){
//                    public void run()
//                    {
//                        test.read(list);
//                    }
//                }.start();
//            }
//            else
//            {
//                new Thread(){
//                    public void run()
//                    {
//                        test.remove(list, id);
//                    }
//                }.start();
//            }
//        }

    }
    
    private static int test()
    {
        try
        {
            String kk = null;
            kk.length();
            return 1;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 2;
        }
        finally
        {
            Log.d("finnaly");
        }
    }
    
    private void read(List<String> list)
    {
        synchronized(list)
        {
            for(String s : list)
            {
                System.out.println(s);
            }
        }
        
    }
    
    private void remove(List<String> list, String s)
    {
        synchronized(list)
        {
            Iterator<String> iterator = list.iterator();
            while(iterator.hasNext())
            {
                if(iterator.next().equals(s))
                {
                    iterator.remove();
                }
            }  
        }
        
    }

}
