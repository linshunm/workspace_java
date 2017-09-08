package io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import utils.Log;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ByteBuffer bBuf = ByteBuffer.allocate(1024);
		Charset charset = Charset.forName("UTF8");
		bBuf.put(charset.encode("iup 1.0\r\nMsg-Type:IM\r\nContent-Length:4\r\n\r\ntest"));
		Log.d("bBuf["+bBuf+"]");
		bBuf.flip();
		Log.d("bBuf["+bBuf+"]");
		charset.decode(bBuf);
		Log.d("bBuf["+bBuf+"]");
		
		
		
		CharBuffer cBuf = charset.decode(bBuf);
		String tempStr = cBuf.toString();
		Log.d("tempStr["+tempStr+"] length["+tempStr.length()+"]");
		
		int endIndex= -1;
		int contentLength = 0 ;
		if((endIndex=tempStr.indexOf("\r\n\r\n")) != -1)
		{
			String headStr = tempStr.substring(0, endIndex+4);
			int headLength = headStr.getBytes().length;
			Log.d("headStr["+headStr+"] length["+headStr.getBytes().length+"]");
			String heads[] = headStr.split("\r\n");
			for(String s : heads)
			{
				Log.d("head item["+s+"]");
				
				if(s.startsWith("Content-Length"))
				{
					contentLength = Integer.valueOf(s.substring(s.indexOf(":")+1));
					Log.d("contentLength["+contentLength+"]");
				}
			}
			
			bBuf.position(headLength);
			bBuf.compact();
			
			bBuf.flip();
			Log.d("bBuf["+bBuf+"]");
			
			byte[] content = new byte[contentLength];
			bBuf.get(content);
			
			String contentStr = new String(content);
			Log.d("bBuf["+bBuf+"] contentStr["+contentStr+"]");
			
			bBuf.compact();
			Log.d("bBuf["+bBuf+"]");
			
			
			
		}
		
		/*
		System.out.println("----------Test allocate--------");
		System.out.println("before alocate:"
				+ Runtime.getRuntime().freeMemory());
		
		// 如果分配的内存过小，调用Runtime.getRuntime().freeMemory()大小不会变化？
		// 要超过多少内存大小JVM才能感觉到？
		ByteBuffer buffer = ByteBuffer.allocate(102400);
		System.out.println("buffer = " + buffer);
		
		System.out.println("after alocate:"
				+ Runtime.getRuntime().freeMemory());
		
		// 这部分直接用的系统内存，所以对JVM的内存没有影响
		ByteBuffer directBuffer = ByteBuffer.allocateDirect(102400);
		System.out.println("directBuffer = " + directBuffer);
		System.out.println("after direct alocate:"
				+ Runtime.getRuntime().freeMemory());
		
		System.out.println("----------Test wrap--------");
		byte[] bytes = new byte[32];
		buffer = ByteBuffer.wrap(bytes);
		System.out.println(buffer);
		
		buffer = ByteBuffer.wrap(bytes, 10, 10);
		System.out.println(buffer);	
		*/
	}

}
