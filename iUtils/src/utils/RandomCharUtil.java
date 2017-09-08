package utils;

import java.util.Random;

/**
 * 取得一个随机数 工具类
 */
public class RandomCharUtil
{
    public static int CHATROOM_INDEX = 0;

    private static Integer idSeq = 0;

    /**
     * 获取17位MsgId
     */
    public static String getNewMsgId()
    {
        // 设置消息随机数
        String szMsgId = null;

        String timeStamp = DateFormatUtil.getCompleteTimeStr1();

        synchronized (idSeq)
        {
            szMsgId = String.format("%s%03x", timeStamp, idSeq++ % 4096);
        }

        return szMsgId;
    }

    /**
     * 取得一个随机数
     * 
     * @return
     */
    public static String getRandomChar()
    {
        String seed = "23456789abcdefghjkmnpqstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
        int start = new Random().nextInt(seed.length());
        return seed.substring(start, start + 1);
    }

    /**
     * 取得一个随机数
     * 
     * @return
     */
    public static String getRandomIndex()
    {
        String seed = "012345678";
        int start = new Random().nextInt(seed.length());

        return seed.substring(start, start + 1);
    }

    public static synchronized int getChatRoomIndex()
    {
        if (CHATROOM_INDEX < 10000)
            return ++CHATROOM_INDEX;
        else
            return 1;
    }
}
