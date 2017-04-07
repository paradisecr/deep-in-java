package me.rui.thread.ThreadObjects;

/**
 * Created by cr on 2017/4/7.
 *  简单的线程类，练习使用常用的线程操作：创建线程、执行线程、线程睡眠、中断线程、线程间等待。
 */
public class SimpleThreads {
    // 某线程说话啦
    private static void threadMsg(String msg) {
        String threadName = Thread.currentThread().getName();
        System.out.printf("%s:%s%n", threadName, msg);
    }

    // 一个循环吃东西的线程，只知道吃吃吃
    private static class MessageLoop implements Runnable {
        int minTime = 10;
        String[] foods = {"Banana", "大盘鸡", "酱鸡腊肉", "卤煮火炒", "烧花鸭", "烧子鹅"};
        @Override
        public void run() {
            // 尽可能吃啊吃，到一定限度下才停下来(超过底线 && 被其他线程叫停)，吃完
            int count = 0;
            while (count < minTime) {
                threadMsg("我在吃——" + foods[++count % foods.length]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    if (count < minTime) {
                        threadMsg("我还没吃够呢~~~再吃会儿~~~~");
                    } else {
                        return;
                    }
                }
            }
        }
    }

    // 主函数，创建吃东西线程，然后叫它停下来不要再吃了，并且等它停下来以后再结束
    public static void main(String[] args) throws InterruptedException{
        threadMsg("开饭啦.....");
        // 创建吃货线程
        Thread t = new Thread(new MessageLoop(), "吃货");
        long patience = 1000 * 3;
        long startTime = System.currentTimeMillis();
        t.start();
        // 让吃的家伙停下来
        while (t.isAlive()) {
            threadMsg("还在等吃货.....");
            t.join(1000);
            if (((System.currentTimeMillis() - startTime) > patience) && t.isAlive()) {
                threadMsg("吃货你该停下来啦！！！");
                t.interrupt();
                t.join();
            }
        }
    }
}
