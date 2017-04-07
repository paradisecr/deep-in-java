package me.rui.thread;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by cr on 2017/3/25.
 */
public class SemaphoreApp {

    public static void main(String[] args) throws InterruptedException{
        BoundedHashSet<String> boundedHashSet = new BoundedHashSet(1);
        boundedHashSet.add("a");
        boundedHashSet.add("a");
    }

    /**
     * 通过Semaphore实现的有界阻塞容器
     * @param <T>
     */
    static class BoundedHashSet<T> {
        private final Set<T> set;
        private final Semaphore sem;

        public BoundedHashSet(int bound) {
            this.set = Collections.synchronizedSet(new HashSet<T>());
            this.sem = new Semaphore(bound);
        }

        public boolean add(T o) throws InterruptedException{
            sem.acquire();
            boolean wasAdded = false;
            try {
                wasAdded = set.add(o);
                return wasAdded;
            }
            finally {
                if (!wasAdded)
                    sem.release();
            }
        }

        public boolean remove(T o) {
            boolean wasRemoved = set.remove(o);
            if (wasRemoved) sem.release();
            return wasRemoved;
        }
    }
}
