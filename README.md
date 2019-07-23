# TaskExecutor-2019
# Project Goals
The primary goal of this project is to have teams implement the multithreaded synchronization needed to implement the TaskExecutor service. 
The secondary goal of the project (but the most difficult) is to implement a Finite Bounded FIFO Buffer described in the textbook, and in class, using only Java’s Object as monitors and without the use of synchronized methods. Note Synchronized blocks are acceptable, even needed to implement the bounded buffer.
# Goal1: TaskExecutor Service
The TaskExecutor is a service that accepts instances of Tasks (classes implementing the Task interface) and executes each task in one of the multiple threads maintained by the thread pool. That is, the service maintains a pool of pre-spawned threads that are used to execute Tasks. 
Figure 1 provides an overview of the structure and design of the TaskExecutor service. Clients provide implementations of the Task interface which performs some application-specific operation. Clients utilize the TaskExecutor.addTask() method to add these tasks to the Blocking FIFO queue. Pooled threads remove the tasks from the queue and call the Task’s execute() method. This Task executes for some amount of time before completing by returning from the execute() method. At this point the task-running threads attempts to obtain a new Task from the queue. If the blocking queue is empty (no tasks to execute) the task running thread’s execution must be blocked until a new task is added to the task queue. If the blocking queue is full (no space to add a new task) the client’s thread’s execution must be blocked until a task is been removed from the queue.
 
Figure 1: Task Executor Overview
# Goal 2: Implementing the Blocking Queue
Teams are to provide an implementation of a Blocking Queue. This is a FIFO queue that is both thread-safe and blocking. Because the queue is internal to the TaskExecutor, the project does not specify the queue’s interface. However, for the sake of discussion let us use the following interface as an example:
public interface BlockingFIFO
{
    void put(Task item) throws Exception;
    Task take() throws Exception;
}

# By ‘Blocking’ we mean that…
1.	The put(task) method places the given task into the queue. If the queue is full, the put() method must blocking the client’s thread until space is made available i.e. when a Task is removed from the queue through the take() method.
2.	The take() method removes and returns a task from the queue. If the queue is empty, the pooled thread calling take() will block until a Task has been placed in the queue though the put() method. 
As described in the Grading Section, teams have two options when implementing the Blocking Queue.
1.	Teams can initially use the class java.util.concurrent.ArrayBlockingQueue which is provided by the Java Development Kit (JDK) runtime library. ArrayBlockingQueue implements the needed blocking behavior as described above. But the use of ArrayBlockingQueue provides less than full credit for the project. 
2.	Teams implement their own BlockingFIFO queue. The three restrictions are 1) teams must implement their BlockingFIFO using the boundedbuffer design as provided in the book (and later in this document) 2) the BlockingFIFO must implement using Java Objects as monitors (Not Semaphores) as described in this document and, 3) implementations must be based on using an array of Task as its container. That is, the size of the queue must be fixed when the container is created. The FIFO implementation size (array length) must be no more than 100 elements. Implementations cannot use any of Java’s built-in container classes (e.g. ArrayList) which has its built-in synchronization.
# Project Requirements
Be sure to read and understand these project requirements. Your submissions will be held accountable for them. 
1.	The BlockingFIFO must implement the design given for boundedbuffer as described in the text book, and later in this document (See the section BlockingFIFO Implementation Notes). Any other FIFO design will not receive credit for the implementation. 
2.	The BlockingFIFO must implement its synchronization using Java Objects as monitors. You cannot use Semaphores, Locks, or other synchronization mechanisms offered by Java.
3.	The classes implementing the TaskExecutor, BlockingFIFO, Thread Pool cannot contain synchronized methods. All of the synchronization must be implemented using primitive Object monitors. Also synchronized block will be needed. 
4.	The project will provide an implementation of the provided TaskExecutor interface. 
5.	The TaskExecutor will accept and execute implementations of the provided Task interface.
6.	The TaskExecutor and Task interface must implement the interface definitions given in the student development project source files including package and exact method signatures i.e. no changes to the interfaces are allowed. 
7.	You have been provided with the source for the test program TaskExecutorTest and the task SimpleTestTask both of which you can use to exercise and verify your TaskExecutor implementations. However, you must not modify either of these test classes. Grading will be evaluated using unmodified versions of these test classes.
8.	Each thread will be assigned a unique name when created. See Thread.setName(String). 
9.	Threads will be maintained in a pool and reused to execute multiple Tasks. Threads are not to be created and destroyed for each task’s execution. 
10.	Exceptions thrown during Task execution cannot cause the failure of the executing thread. It is suggested that Task execution be wrapped in a try / catch block that logs and ignores the caught exceptions.
11.	Tasks will execute concurrently on N threads where N is the thread pool size and is provided as a service initialization parameter. 
12.	The BlockingFIFO implementation must use an Array to store tasks. You cannot use synchronized data structures such as ArrayList to implement your blocking queue.
13.	The BlockingFIFO implementation must use an array of length no more than 100 elements. The reason for this requirement is that an array larger that the number of tasks injected during testing will not exercise the blocking nature of FIFO put() operations.
14.	When the number of inserted tasks exceeds the number of threads, unexecuted tasks will remain on the BlockingFIFO until removed by a task running thread. 
15.	Every pooled thread’s execution must block when the BlockingFIFO is empty i.e. Pool threads should not spin or busy-wait when attempting to obtain a task from the service’s empty FIFO. 
16.	When the BlockingFIFO is full, client threads attempting to add a new task to the queue must block until a Task is removed. Again, no polling, or busy waiting is allowed. 
17.	The project will be delivered as a library jar file which will be linked with the test applications used to initialize and test the TaskExecutor’s correct implementation. 
18.	The TaskExecutor should catch, report (log), and eat any exceptions thrown during an application-specific Task’s execution i.e. an Exception thrown by a Task should not cause a pool’ed thread to exit. 
19.	Your implementation cannot print any messages to stdout. The number of lines printed to the console will be used to determine the correctness of your implementation and any additional lines of text will throw off the count and will cause your project to fail. 
20.	It is entirely correct for the test application to not exit once the N tasks have been processed. 
