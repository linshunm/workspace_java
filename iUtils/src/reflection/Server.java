package reflection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.util.HashMap;

import utils.Log;

public class Server {
	
	private HashMap<Class, Object> objs = new HashMap<Class, Object>();
	
	public void service() throws IOException, ClassNotFoundException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		ServerSocket server = new ServerSocket(8081);
		Log.d("servr start...");
		while(true)
		{
			Log.d("new socket");
			Socket socket = server.accept();
			Log.d("get socket");
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			ObjectInputStream ois = new ObjectInputStream(is);
			Object obj = ois.readObject();
			Call call = (Call) obj;
			Log.d("read call["+call+"]");
			Object retObj = invoke(call);
			
			ObjectOutputStream oos = new ObjectOutputStream(os);
			
			oos.writeObject(retObj);
			
			ois.close();
			oos.close();
			is.close();
			os.close();
			socket.close();
		}
	}
	
	private void register()
	{
		objs.put(RemoteService.class, new RemoteService());
	}
	
	private Object invoke(Call call) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		Object obj = null;
		
		Class<?> classType = Class.forName(call.getClassName());
		
		Method method = classType.getMethod(call.getMethodName(), call.getClassParms());
		
		obj = method.invoke(objs.get(classType), call.getObjPrams());
		
		return obj;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.register();
		try {
			server.service();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
