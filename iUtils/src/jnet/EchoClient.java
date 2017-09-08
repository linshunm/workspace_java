package jnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import utils.Log;

public class EchoClient {
	
	private Socket socket;
	
	public EchoClient() throws IOException
	{
		socket = new Socket("localhost", 8088);
		socket.setSoTimeout(5000);
	}
	
	public void talk()
	{
		try {
			PrintWriter pw = NetUtil.getWriter(socket);
			BufferedReader br = NetUtil.getReader(socket);
			
			BufferedReader lbr = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			while((msg = lbr.readLine()) != null)
			{
				Log.d(msg);
				pw.println(msg);
				Log.d("send");
				
				Log.d(br.readLine());
				Log.d("readed");
				
				if("bye".equals(msg))
				{
					break;
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EchoClient client = new EchoClient();
			client.talk();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
