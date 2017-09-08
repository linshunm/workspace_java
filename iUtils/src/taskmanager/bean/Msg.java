package taskmanager.bean;

public class Msg
{
    private String msgId;
    private String content;
    public String getMsgId()
    {
        return msgId;
    }
    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }
    public String getContent()
    {
        return content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    @Override
    public String toString()
    {
        return "Msg [msgId=" + msgId + ", content=" + content + "]";
    }
    

}
