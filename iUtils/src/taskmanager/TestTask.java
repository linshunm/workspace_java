package taskmanager;

import utils.RandomCharUtil;

public class TestTask
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {

        
        RqManager rqMgr;
        try
        {
            rqMgr = RqManager.getIns();
            
            for(int i =0 ;i<5; i++)
                rqMgr.req(RqManager.MQ_MESSAGE, new SendMsgTask(RqManager.MQ_MESSAGE, RandomCharUtil.getNewMsgId()));
            
            
            Thread.sleep(10000);
            rqMgr.reTry(RqManager.MQ_MESSAGE);
            
            /*
            for(int i =0 ;i<2; i++)
                rqMgr1.req(RqManager.MQ_FILE, new SendMsgTask(RqManager.MQ_FILE, RandomCharUtil.getNewMsgId()));
                */
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
     
        /*
        TaskManager tMgr2 = TaskManager.getIns("2",2);
        for(int i =0 ;i<10; i++)
            tMgr2.newTask(new SendMsgTask("2"));
        
        TaskManager tMgr = TaskManager.getIns();
        for(int i =0 ;i<10; i++)
            tMgr.newTask(new SendMsgTask("0"));
        */


    }

}
