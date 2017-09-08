package reflection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import utils.Log;

public class Client {
	
	public void invoke() throws UnknownHostException, IOException, ClassNotFoundException
	{
		Log.d("client create");

		Call call = Call.getInstance().getBuilder()
				.setClassName("reflection.RemoteService")
				.setMethodName("echo")
				.setClassParms(new Class[]{String.class})
				.setObjParams(new Object[]{"hello"})
				.build();
		Log.d("client call["+call+"]");
		
		Socket socket = new Socket("localhost", 8081);
		Log.d("client 1");
		
		OutputStream os = socket.getOutputStream();
		Log.d("client 2");
		InputStream is = socket.getInputStream();
		Log.d("client 3");
		ObjectOutputStream oos = new ObjectOutputStream(os);
		Log.d("client 4");
		
		oos.writeObject(call);
		Log.d("client 6");

		ObjectInputStream ois = new ObjectInputStream(is);
		Log.d("client 5");
		Object ret = ois.readObject();
		Log.d("client 7");
		
		Log.d((String)ret);
		
		
		ois.close();
		oos.close();
		is.close();
		os.close();
		socket.close();
		
		
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args){
		Client client = new Client();
		try {
			client.invoke();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
