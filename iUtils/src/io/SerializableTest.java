package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import utils.Log;

public class SerializableTest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	class Person implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String id;
		private String name;
		private int age;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		@Override
		public String toString() {
			return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
		}
		
		

	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SerializableTest test = new SerializableTest();
		
		File file = new File("object.txt");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			Person p = test.new Person();
			p.setAge(11);
			p.setName("kevin аж");
			p.setId("20304294032-49-");
			
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(p);
			oos.flush();
			oos.close();
			
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Person p1 = (Person)ois.readObject();
			
			Log.d(p1.toString());
			
		} catch (FileNotFoundException e) {
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
