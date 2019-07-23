package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.*;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;

public class TaskExecutorImpl implements TaskExecutor
{
	
	private int numThreads;
	private TaskRunner TPool[];
	private BlockingFifoQueue<Task> Queue;

	
	public TaskExecutorImpl(int numThreads) 
	{
		this.numThreads = numThreads;
		createTPool(); //create thread pool
	}
	
	private void createTPool() //Creates the Thread pool that would hold the tasks
	{
		this.Queue = new BlockingFifoQueue<Task>(100);
		this.TPool = new TaskRunner[numThreads];

		for(int i = 0; i < numThreads; i++) 
		{

			TaskRunner executingTask = new TaskRunner(this.Queue);
			
			Thread executingThread = new Thread(executingTask);
			executingThread.start();

			TPool[i] = executingTask;

		}
	}

	
	@Override
	public void addTask(Task task)
	{
		this.Queue.put(task);
	}

}