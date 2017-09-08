package com.iutils.network.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Created by 10110878 on 2017/8/19.
 */
public class SocketUtil {

    private static  Charset charset = Charset.forName("utf-8");
    
    public static String decode(ByteBuffer buffer)
    {
        CharBuffer charBuffer = charset.decode(buffer);
        return charBuffer.toString();
    }

    
    public static ByteBuffer encode(String str)
    {
        return charset.encode(str);
    }

    public static BufferedReader getReader(InputStream is)
    {
        return new BufferedReader(new InputStreamReader(is));
    }

    public static BufferedWriter getWriter(OutputStream os)
    {
        return new BufferedWriter(new OutputStreamWriter(os));
    }
}
