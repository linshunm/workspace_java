package reflection;

import java.io.Serializable;
import java.util.Arrays;

public class Call implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String className;
	private String methodName;
	private Class[] classParms;
	private Object[] objPrams;
	
	private static Call instance;
	
	public static Call getInstance()
	{
		synchronized(Call.class)
		{
			if(instance != null)
			{
				return instance;
			}
			else
			{
				instance = new Call();
				return instance;
			}
		}
	}
	
	public Builder getBuilder()
	{
		return new Builder();
	}
	
	
	
	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public Class[] getClassParms() {
		return classParms;
	}

	public Object[] getObjPrams() {
		return objPrams;
	}



	class Builder{
		
		private String className;
		private String methodName;
		private Class[] classParms;
		private Object[] objPrams;
		
		public Call build()
		{
			Call call = new Call();
			call.className = this.className;
			call.methodName = this.methodName;
			call.classParms = this.classParms;
			call.objPrams = this.objPrams;
			return call;
		}
		
		public Builder setClassName(String className)
		{
			this.className = className;
			return this;
		}
		
		public Builder setMethodName(String methodName)
		{
			this.methodName = methodName;
			return this;
		}
		
		public Builder setClassParms(Class[] classParams)
		{
			this.classParms = classParams;
			return this;
		}
		
		public Builder setObjParams(Object[] objParams)
		{
			this.objPrams = objParams;
			return this;
		}
		
	}



	@Override
	public String toString() {
		return "Call [className=" + className + ", methodName=" + methodName
				+ ", classParms=" + Arrays.toString(classParms) + ", objPrams="
				+ Arrays.toString(objPrams) + "]";
	}
	
	

}
