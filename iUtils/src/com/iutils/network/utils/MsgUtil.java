package com.iutils.network.utils;



import com.iutils.network.bean.Msg;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import utils.Log;

/**
 * Created by 10110878 on 2017/8/20.
 */
public class MsgUtil {

private static final String TAG = "MsgUtil";

  
    public static String head(Msg msg)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("IUP 1.0\r\n");
        if(msg.msgType == Msg.IM_MSG)
        {
            sb.append("Msg-Type:IM\r\n");
        }
        sb.append("Content-Length:"+msg.contentLength+"\r\n");
        sb.append("\r\n");

        return  sb.toString();
    }
    public static Msg decode(ByteBuffer buffer)
    {
        buffer.flip();
        String tempStr = SocketUtil.decode(buffer);
        Log.d("tempStr["+tempStr+"] length["+tempStr.length()+"]");

        int endIndex= -1;
        int contentLength = 0 ;
        if((endIndex=tempStr.indexOf("\r\n\r\n")) == -1) {
            return null;
        }

        Msg msg = new Msg();
        String headStr = tempStr.substring(0, endIndex + 4);
        int headLength = headStr.getBytes().length;
        Log.d("headStr[" + headStr + "] length[" + headStr.getBytes().length + "]");
        String heads[] = headStr.split("\r\n");
        for (String s : heads) {

            if (s.startsWith("Content-Length")) {
                contentLength = Integer.valueOf(s.substring(s.indexOf(":") + 1));
                msg.contentLength = contentLength;
                continue;
            }

            if(s.startsWith("Msg-Type"))
            {
                String strMsgType = s.substring(s.indexOf(":") + 1);
                if("IM".equals(strMsgType))
                {
                    msg.msgType = Msg.IM_MSG;
                }
                continue;
            }
        }

        buffer.position(headLength);
        buffer.compact();

        buffer.flip();

        byte[] content = new byte[contentLength];
        buffer.get(content);

        String contentStr = new String(content);

        buffer.compact();
        Log.d( "msg[" + msg + "]");
        return msg;
    }

    public static void encode(ByteBuffer buffer, Msg msg)
    {
        buffer.put(SocketUtil.encode(head(msg)));
        buffer.put(msg.bytesContent);
    }
}
