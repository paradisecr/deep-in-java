# Thread
## 1. CountDownLatch
- Latch.java
- public CountDownLatch(int count)：构造函数。
- public void countDown()：Decrements the count of the latch.  
- public void await() throws InterruptedException:等待。 
- public boolean await(long timeout, TimeUnit unit)：  


CountDownLatch是一种灵活的闭锁实现。它可以使一个或一组线程等待一组时间发生。
