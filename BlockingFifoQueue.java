package edu.utdallas.blockingFIFO;



public class BlockingFifoQueue<T> {

	private T[] queue;				//Queue for threads

	private int nextIn;				// Index of next put method
	private int nextOut;			// Index of next take method
	private int capacity;			// Queue capacity(upper bounded)
	private int numOfTask;			// To count number of task in queue 

	private Object notFull, notEmpty;			// Monitors used for synchronization when queue is not full or not empty

	private Object putLock, takeLock; // Monitors used for synchronization when put and take method are operated.


	@SuppressWarnings("unchecked")
	public BlockingFifoQueue(int capacity) {

		this.queue = ( T[] ) new Object[capacity];
		this.capacity = capacity;
		this.nextIn = 0;
		this.nextOut = 0;
		this.numOfTask = 0;

		this.notFull = new Object();
		this.notEmpty = new Object();
		this.putLock = new Object();
		this.takeLock = new Object();

	}
	public void put(T task) {


		if (numOfTask == capacity)
		{ 
			synchronized (notFull) 
			{

				try 
				{
					notFull.wait();  			//if buffer is full, wait until it take out.
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
				
		}
		
		queue[nextIn] = task;
		nextIn = ++nextIn % capacity;
		++numOfTask;
		
		synchronized (notEmpty) 
		{		
			notEmpty.notify();		// Send signal to take tasks
		}




	}
	public T take() 
	{
		T task = null;


		if(numOfTask == 0) 
		{
			synchronized (notEmpty) 
			{

				try 
				{
					notEmpty.wait();	// Buffer is empty, wait signal for put 
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();

				}
			}
		}
		
				
		task = queue[nextOut];			// keep taking out task until it is empty.
		nextOut = ++nextOut % capacity;
		--numOfTask;				
		
		synchronized (notFull) 
		{
			notFull.notify();		// Send signal to put tasks into queue.
		}
		return task;
	}







}



