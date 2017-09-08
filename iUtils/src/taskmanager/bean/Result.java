package taskmanager.bean;

public class Result
{
    private String msgId;
    private String resultCode;
    
    public Result(String msgId, String resultCode)
    {
        this.msgId = msgId;
        this.resultCode = resultCode;
    }
    
    public String getMsgId()
    {
        return msgId;
    }
    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }
    public String getResultCode()
    {
        return resultCode;
    }
    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }
    @Override
    public String toString()
    {
        return "Result [msgId=" + msgId + ", resultCode=" + resultCode + "]";
    }
    
}
