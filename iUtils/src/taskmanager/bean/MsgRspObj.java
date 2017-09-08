package taskmanager.bean;

import taskmanager.RspObj;

public class MsgRspObj extends RspObj
{
    public MsgRspObj(String taskId, String rspCode)
    {
        super(taskId, rspCode);
        
        msgRspData= "taskId["+taskId+"] rspCode["+rspCode+"]";
    }

    private String msgRspData;

    public String getMsgRspData()
    {
        return msgRspData;
    }

    public void setMsgRspData(String msgRspData)
    {
        this.msgRspData = msgRspData;
    }

    @Override
    public String toString()
    {
        return "MsgRspObj [msgRspData=" + msgRspData + "]";
    }
    
}
