1. HashMap描述

> Hash table based implementation of the Map interface. This implementation provides all of the optional map operations, and permits null values and the null key. (The HashMap class is roughly equivalent to Hashtable, except that it is unsynchronized and permits nulls.) This class makes no guarantees as to the order of the map; in particular, it does not guarantee that the order will remain constant over time.

几个关键点：
- 基于Hash table实现
- 允许null value和null key
- 与Hash Table的不同(允许null, 不同步)
- 不保证有序
    - 与插入顺序会不同)
    - 顺序也会随时间变化

2. 重要参数

> - **Initial capacity** The capacity is the number of buckets in the hash table, The initial capacity is simply the capacity at the time the hash table is created.
> - **Load factor** The load factor is a measure of how full the hash table is allowed to get before its capacity is automatically increased.When the number of entries in the hash table exceeds the product of the load factor and the current capacity, the hash table is rehashed (that is, internal data structures are rebuilt) so that the hash table has approximately twice the number of buckets.

重要参数：
- capability: HashTable中bucket的数目；
- load factor: HashTable最大允许装载的比例。
    - 默认为0.75
        - Higher values decrease the space overhead but increase the lookup cost (reflected in most of the operations of the HashMap class, including get and put). 
    - ReHash/ReBuild:当entry的数目超过load factor * capability时：
        - hash table进行rehash(内部数据结构rebuild)
        - bucket的数量扩充为原来的两倍。

同步性：
非同步，在必要时可以：
```
Map m = Collections.synchronizedMap(new HashMap(...));
```
迭代器是fail-fast的，在使用迭代器期间，若map发生改变会报ConcurrentModificationException错误；

2. put操作

put的流程：
1. 对key的hashCode()做hash，然后再计算index;
1. 如果没碰撞直接放到bucket里；
1. 如果碰撞了，以链表的形式存在buckets后；
1. 如果碰撞导致链表过长(大于等于TREEIFY_THRESHOLD)，就把链表转换成红黑树；
1. 如果节点已经存在就替换old value(保证key的唯一性)
1. 如果bucket满了(超过load factor*current capacity)，就要resize。

方法说明：

```
public V put(K key,
             V value)
Associates the specified value with the specified key in this map. If the map previously contained a mapping for the key, the old value is replaced.
Returns:
the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.)
```
```
    public V put(K key, V value) {
        // 对key进行hash计算
        return putVal(hash(key), key, value, false, true);
    }
    // hash的计算只与key的hashCode值有关
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        // 若table为则创建
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        // 计算index = (n-1) & hash，若目标为空则创建新节点
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {
            // bucket已占用
            Node<K,V> e; K k;
            // 节点p与要插入元素的hash && key相同, e指向p
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)
                //key 不同并且p 为treeNode，则插入tree(实现为红黑树)，返回位置
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                // p为链表节点
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        // 不存在hash&&key相同的元素
                        // p指向新元素
                        p.next = newNode(hash, key, value, null);
                        // 若超过threshold,将链表变为红黑树
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    // 若找到老元素
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }    
```

3. get操作

get流程：
1. 计算hash
2. 通过Key获得bucket的index
3. 在bucket中查找hash&&key值相同的元素
    1. bucket中为链表
    2. bucket中为红黑树
4. 返回元素的值/null
```
    public V get(Object key) {
        Node<K,V> e;
        // 计算hash，通过hash和key取得元素
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }
    
    final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
```
4. resize操作