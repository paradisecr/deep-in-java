## Java IO
一.Linux 网络IO模型

### 一. Linux 网络IO模型

Unix网络编程对IO模型的分类：
1. 阻塞IO模型；
2. 非阻塞IO模型；
3. ***IO复用模型：*** Linux提供select/poll，顺序扫描fd是否就绪。epoll使用基于事件驱动方式代替顺序扫描，因此性能更高。
Java NIO的核心类库多路复用器Selector就是基于epoll的多路复用技术实现的。
4. 信号驱动IO模型；
5. 异步IO。其与信号驱动模型的主要区别是：信号驱动IO由内核通知我们何时可以***开始一个IO操作***；异步IO模型由内核
通知我们***IO操作何时已经完成***。


epoll相比select的改进：
1. 支持一个进程打开的socket描述符(fd)不受限制(只受限于操作系统最大文件句柄数，与内存大小有关)；
2. IO效率不会随着fd数目的增加而线性下降；
3. 使用mmap加速内核与用户空间的消息传递；
4. epoll的API更加简单。

### 二. Java IO演进

演进：
- 1.4之前：同步阻塞式IO;
- 1.5引入支持非阻塞的、面向块的NIO,
    - 缓冲区：面向块；
    - 通道：全双工。
- 1.7引入NIO 2.0：异步IO
    - 异步通道：异步文件通道和异步套接字通道。
    
ByteBuffer：
![http://dl2.iteye.com/upload/attachment/0096/4782/b8a7bad8-ec65-36dc-bb11-4f352e00cd67.png](ByteBuffer结构)
缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。 
为了理解Buffer的工作原理，需要熟悉它的三个属性： 
- capacity：buffer块的大小
- position：下一个可读/可写的位置。
- limit：读取/写入的指针限制(写时为capacity,读时为上一次写入的position)

flip方法：将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，并将limit设置成之前position的值。 
换句话说，position现在用于标记读的位置，limit表示之前写进了多少个byte、char等 —— 现在能读取多少个byte、char等。 

### 三. Netty IO

使用Netty框架搭建网络应用。

1. TCP粘包/拆包问题：在应用层进行解决。
2. 主流编解码框架：
    - Google Protobuf; 灵活、跨平台、代码自动生成；
    - FaceBook Thrift;适用于静态数据交换。
    - JBoss Marshalling: 兼容Java 序列化。
3. 自定义了缓冲区——ByteBuf,因为ByteBuffer的局限性：
- 长度固定，不能动态伸缩；
- 只有一个标志位置的指针position，读写的时候需要手动调整指针，容易出错；
- API功能有限，一些高级特性不支持；