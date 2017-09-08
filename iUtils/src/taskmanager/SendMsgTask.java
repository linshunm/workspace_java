package taskmanager;

import java.util.Random;

import taskmanager.bean.Msg;
import taskmanager.bean.MsgRspObj;
import utils.Log;
import utils.ThreadUtil;

public class SendMsgTask extends Task
{
    private Msg msg;
    private long doTime = 0L;
    
    public SendMsgTask(String rqId, String taskId)
    {
        super(rqId, taskId);
        long timeout = new Random().nextInt(1000);
        doTime = new Random().nextInt(10000);
        msg = new Msg();
        msg.setMsgId(taskId);
        msg.setContent("msg content-"+msg.getMsgId()+" timeout["+timeout+"] doTime["+doTime+"]");
        //super.setTimeout(timeout);
    }
    
    @Override
    protected void doTask()
    {
        super.doTask();
        Log.d("send msg["+msg+"]");
        
        ThreadUtil.execTask(new Runnable(){
            public void run()
            {
                try
                {
                    Thread.sleep(doTime);
                    RqManager.getIns().callback(RqManager.MQ_MESSAGE, new MsgRspObj(taskId, "202"));
                }
                catch(Exception e)
                {
                    Log.d("send msg["+msg+"] exception");
                }
            }
        });
        
    }

    public void callback(MsgRspObj msgRspObj)
    {
        super.callback(msgRspObj);
        Log.d("callback msgRspObj["+msgRspObj +"]");
    }

    @Override
    public String toString()
    {
        return "SendMsgTask [msg=" + msg + "]";
    }


}
