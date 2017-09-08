package jnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import utils.Log;

public class EchoServer {
	
	//D:\workspace_java\iUtils>javac -encoding utf8 -d classes -sourcepath src src/jnet/*.java
	
	private ServerSocket serverSocket;
	
	public EchoServer() throws IOException
	{
		Log.d("new serversocket");
		serverSocket = new ServerSocket(NetConst.SERVER_PORT);
	}
	
	private String echo(String msg)
	{
		return "echo:"+msg;
	}
	
	public void service()
	{
		while(true)
		{
			Socket socket = null;
			try
			{
				socket = serverSocket.accept();
				//socket.setSoTimeout(5000);
				Log.d("accept socket["+socket+"]");
				
				PrintWriter writer = NetUtil.getWriter(socket);
				BufferedReader reader = NetUtil.getReader(socket);
				
				String msg = null;
				while((msg = reader.readLine()) != null)
				{
					Log.d(msg);
					writer.println(echo(msg));
//					writer.write(echo(msg));
//					writer.flush();
					Log.d("writed");
					
					if("bye".equals(msg))
					{
						Log.d("bye!");
						break;
					}
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(socket != null)
				{
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EchoServer server = new EchoServer();
			server.service();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
