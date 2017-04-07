# Thread
## 1. CountDownLatch
文件：  
- [Latch.java](Latch.java)  

用法：  
- public CountDownLatch(int count)：构造函数。
- public void countDown()：Decrements the count of the latch.  
- public void await() throws InterruptedException:等待。 
- public boolean await(long timeout, TimeUnit unit)：  


CountDownLatch是一种灵活的闭锁实现。它可以使一个或一组线程等待一组事件发生。


## 2. FutureTask
文件：  
- [FutureLoader.java](FutureLoader.java)  

用法：  
- public FutureTask(Callable<V> callable)：构造函数。  
- public V get() throws InterruptedException, ExecutionException

FutureTask可用于闭锁，实现了Future语义，表示一种抽象的可生成结果的计算。FutureTask表示的计算是通过Callable来实现的，相当于一种可生成结果的Runnable，并且可以处于一下3种状态：等待运行，正在运行和运行完成。  
运行完成表示计算的所有可能结束方式，包括正常结束，由于取消而结束和由于异常而结束。当FutureTask进入完成状态后，它会永远停止在这个状态。  
Future.get的行为取决于任务的状态，会阻塞知道任务进入完成状态。FutureTask将计算结果从执行计算的线程传递到获取这个结果的线程。  

