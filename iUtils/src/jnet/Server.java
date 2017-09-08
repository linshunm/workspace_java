package jnet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import utils.Log;
import utils.ThreadUtil;

public class Server {
	
	private Selector selector = null;
	private ServerSocketChannel  serverSocketChannel = null;
	private int port =8088;
	private Charset charset = Charset.forName("utf8");
	private Object lock = new Object();
	
	public Server() throws IOException
	{
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		Log.d("server start...");
	}
	
	public void accept()
	{
		while(true)
		{
			try
			{
				SocketChannel socketChannel = serverSocketChannel.accept();
				Log.d("receive connection from:"
				+socketChannel.socket().getInetAddress()
				+":"+socketChannel.socket().getPort());
				socketChannel.configureBlocking(false);
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				synchronized(lock)
				{
					Log.d("accept lock");
					selector.wakeup();
					socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE, buffer);
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void service() throws IOException
	{
		Log.d("service ");
		while(true)
		{
			synchronized(lock)
			{
				//Log.d("service lock");
				
			}
			
			int n = selector.select();
			if(n == 0)
			{
				continue;
			}
			
			//Log.d("service select="+n);
			
			Set<SelectionKey> readKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = readKeys.iterator();
			while(it.hasNext())
			{
				SelectionKey key = null;
				try
				{
					key = it.next();
					//it.remove();
					
					if(key.isReadable())
					{
						receive(key);
					}


					if(key.isWritable())
					{
						send(key);
					}
					readKeys.remove(key);
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
					Log.d("err"+e.getMessage());
					try
					{
						if(key != null)
						{
							key.cancel();
							key.channel().close();
						}
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
				}
			}
		}
	}
	
	private void send(SelectionKey key) throws IOException
	{
		//Log.d("send key["+key+"]");
		ByteBuffer buffer = (ByteBuffer)key.attachment();
		SocketChannel socketChannel = (SocketChannel)key.channel();
		buffer.flip();
		String data = decode(buffer);
		//Log.d("data["+data+"]");
		if(data.indexOf("\r\n")==-1) return;
		
		String outputData = data.substring(0, data.indexOf("\n")+1);
		Log.d("outputData["+outputData+"]");
		
		ByteBuffer outputBuffer = encode("echo:"+outputData);
		while(outputBuffer.hasRemaining())
		{
			socketChannel.write(outputBuffer);
		}
		
		ByteBuffer temp = encode(outputData);
		buffer.position(temp.limit());
		buffer.compact();
		
		if(outputData.equals("bye\r\n"))
		{
			Log.d("close connect["+socketChannel.socket()+"]");
			key.cancel();
			socketChannel.close();
		}
		 
	}
	
	private void receive(SelectionKey key) throws IOException
	{
		Log.d("receive key["+key+"]");
		ByteBuffer buffer = (ByteBuffer) key.attachment();
		SocketChannel socketChannel = (SocketChannel)key.channel();
		ByteBuffer readBuffer = ByteBuffer.allocate(32);
		socketChannel.read(readBuffer);
		readBuffer.flip();
		buffer.limit(buffer.capacity());
		buffer.put(readBuffer);
		
	}
	
	private String decode(ByteBuffer buffer)
	{
		CharBuffer cBuffer = charset.decode(buffer);
		return cBuffer.toString();
	}
	
	private ByteBuffer encode(String msg)
	{
		return charset.encode(msg);
	}
	
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final Server server = new Server();
		ThreadUtil.execTask(new Runnable(){

			@Override
			public void run() {
				server.accept();
			}});
		
		server.service();

	}

}
