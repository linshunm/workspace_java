package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utils.Log;

class Meal
{
	private final int orderNum;
	public Meal(int orderNum)
	{
		this.orderNum = orderNum;
	}
	
	@Override
	public String toString() {
		return "Meal [orderNum=" + orderNum + ", toString()="
				+ super.toString() + "]";
	}
	
	
}

class Waiter implements Runnable 
{
	private int count;
	private Restaurant r;
	public Waiter(Restaurant r)
	{
		this.r = r;
	}

	@Override
	public void run() {
		try
		{
			while(!Thread.interrupted())
			{
				synchronized(this)
				{
					while(r.meal == null)
					{
						Log.d("waiter wait for count["+count+"]");
						wait();
						Log.d("waiter wait after count["+count++ +"]");
					}
					
					synchronized(r.chef)
					{
						Log.d("waiter eat meal["+r.meal+"] and notify chef to cook");
						r.meal = null;
						r.chef.notifyAll();
					}
				}
			}	
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
			Log.d("waiter exception["+e.getMessage()+"]");
		}

	}

}

class Chef implements Runnable
{
	private int num;
	private Restaurant r;
	public Chef(Restaurant r)
	{
		this.r = r;
	}
	
	public void run()
	{
		try
		{
			while(!Thread.interrupted())
			{
				Log.d("chef this{"+this+"}");
				synchronized(this)
				{
					while(r.meal != null)
					{
						Log.d("chef wait for num["+num+"]");
						wait();
						Log.d("chef wait after num["+num+"]");
					}
					
					synchronized(r.waiter)
					{
						if(++num <10)
						{
							r.meal = new Meal(num);
							r.waiter.notifyAll();
							Log.d("chef make meal["+r.meal+"] and notify waiter to eat");
						}
						else
						{
							Log.d("chef go home");
							r.es.shutdownNow();
						}
					}
				}
			}
			
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
			Log.d("chef exception["+e.getMessage()+"]");
		}
	}
}

public class Restaurant {
	
	Meal meal;
	Waiter waiter = new Waiter(this);
	Chef chef = new Chef(this);
	ExecutorService es = Executors.newCachedThreadPool();
	
	
	
	private Restaurant()
	{
		es.execute(chef);
		es.execute(waiter);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Restaurant r = new Restaurant();
	}

}
