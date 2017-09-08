package com.iutils.network.bean;


import java.util.Arrays;

/**
 * Created by 10110878 on 2017/8/20.
 */
public class Msg {
    public static final int IM_MSG = 10001;


    public int msgType;
    public int contentLength;
    public String content;
    public byte[] bytesContent;

    public Msg()
    {}

    public Msg(String content) {
        this.content = content;
        this.msgType = IM_MSG;
        this.contentLength = content.getBytes().length;
        this.bytesContent = content.getBytes();
    }

    @Override
    public String toString() {
        return "Msg{" +
                "bytesContent=" + Arrays.toString(bytesContent) +
                ", content='" + content + '\'' +
                ", contentLength=" + contentLength +
                ", msgType=" + msgType +
                '}';
    }
}
