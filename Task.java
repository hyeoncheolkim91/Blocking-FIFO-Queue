package edu.utdallas.taskExecutor;

public interface Task
{
	/** 
	 * Implement with the Task's specific behavior. 
	 */
	void execute();

	/**
	 * Returns a unique name for this task.
	 * Typically the name is assigned in the task's constructor.
	 */
	String getName();
}
