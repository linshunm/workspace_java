package utils;

public class Log
{
    public static void d(String msg)
    {
        System.out.println(DateFormatUtil.getRecordDateStr()+" "+msg);
    }
}
