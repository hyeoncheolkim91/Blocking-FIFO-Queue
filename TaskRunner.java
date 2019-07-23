package taskExecutorImpl;

import blockingFIFO;
import Task;

public class TaskRunner implements Runnable {


	private BlockingFifoQueue<Task> TQueue;
	
	
	public TaskRunner(BlockingFifoQueue<Task> Queue)
	{
		this.TQueue = Queue;
	}
	
	
	
	@Override
	public void run() 
	{
		Task newtask;
		while (true) 
		{
			newtask = (Task) TQueue.take();
			try 
			{ 
				
				newtask.execute();
			} catch(Throwable t) 
			{
				/*
					System.out.println(newtask.getName());
					System.out.println(t.getMessage());
					System.out.println(t.getStackTrace()+"\n");
				*/
				
			}
			
						
			
		}



	}
}
