package taskmanager;

public class RspObj
{
    private String taskId;
    private String rspCode;
    
    public RspObj(String taskId, String rspCode)
    {
        this.taskId = taskId;
        this.rspCode = rspCode;
    }
    
    public String getTaskId()
    {
        return taskId;
    }
    public String getRspCode()
    {
        return rspCode;
    }

    @Override
    public String toString()
    {
        return "RspObj [taskId=" + taskId + ", rspCode=" + rspCode + "]";
    }
    
    
}
