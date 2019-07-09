//package com.shuai.java.util;
//
//import javax.swing.tree.TreeNode;
//import java.io.Serializable;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.*;
//
///**
// *1.基于哈希表实现的映射接口。此实现提供所有可选映射操作，并允许空值和空键。
// * (HashMap类大致相当于Hashtable，只是它是不同步的，并且允许为空。)该类不保证映射的顺序;特别是，它不能保证顺序会随着时间保持不变。
// * 2.这个实现为基本操作(get和put)提供了稳定的时间性能，假设hash函数将元素恰当地分散到各个桶中。
// * 集合视图的迭代需要与HashMap实例的“容量”(桶的数量)及其大小(键值映射的数量)成比例的时间。因此，如果迭代性能很重要，那么不要将初始容量设置得太高(或负载因子太低)。
// * 3.HashMap实例有两个影响其性能的参数:初始容量和负载因子。容量是哈希表中的桶数，初始容量就是创建哈希表时的容量。
// * 负载因子是衡量在哈希表的容量被自动增加之前，哈希表被允许获得的满值的度量。
// * 当哈希表中的条目数超过负载因子和当前容量的乘积时，哈希表将被重新哈希(即重新构建内部数据结构)，以便哈希表具有大约两倍的桶数。
// * 4.一般来说，默认的负载因子(.75)在时间和空间成本之间提供了很好的权衡。
// * 较高的值减少了空间开销，但增加了查找成本(反映在HashMap类的大多数操作中，包括get和put)。
// * 在设置map的初始容量时，应该考虑map中预期条目的数量及其负载因子，从而最小化rehash操作的数量。
// * 如果初始容量大于最大条目数除以负载因子，则不会发生任何重哈希操作。
// * 5.如果要将许多映射存储在HashMap实例中，那么使用足够大的容量创建映射将比让映射根据需要执行自动哈希来增长表更有效地存储映射。
// * 注意，使用具有相同hashCode()的多个键肯定会降低任何散列表的性能。为了改善影响，当键是可比较的时，该类可以使用键之间的比较顺序来帮助断开连接。
// * 6.注意，这个实现不是同步的。如果多个线程同时访问一个散列映射，并且其中至少有一个线程从结构上修改了映射，那么它必须在外部同步。
// * (结构修改是添加或删除一个或多个映射的任何操作;仅更改与实例已经包含的键关联的值不是结构修改。)这通常是通过在一些自然封装映射的对象上进行同步来实现的。
// * 如果不存在这样的对象，则应该使用集合“包装”映射。synchronizedMap方法。这最好在创建时完成，以防止意外的不同步访问map
// * 7.这个类的所有“集合视图方法”返回的迭代器都是fast-fail的:如果在创建迭代器之后的任何时候对映射进行结构上的修改，除了通过迭代器自己的remove方法之外，迭代器将抛出ConcurrentModificationException。
// * 因此，在面对并发修改时，迭代器会触发fast-fail机制，而不是在将来某个不确定的时间冒着任意的、不确定的行为的风险。
// * 8.注意，不能保证迭代器的快速故障行为，因为通常来说，在存在非同步并发修改的情况下，不可能做出任何严格的保证。
// * 故障快速迭代器以最大的努力抛出ConcurrentModificationException。
// * 因此，编写一个依赖于此异常来判断其正确性的程序是错误的:迭代器的快速故障行为应该只用于检测bug。
// * @param <K>
// * @param <V>
// */
//public class MyHashMap<K, V> extends AbstractMap<K,V> implements Map<K,V>,Cloneable,Serializable {
//
//
//    /**
//     * 默认初始容量—必须是2的幂。
//     */
//    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
//
//    /**
//     * 最大容量，如果较高的值由带参数的任何构造函数隐式指定，则使用该值。必须是2的幂<= 1<<30。
//     */
//    static final int MAXIMUM_CAPACITY = 1 << 30;
//
//    /**
//     * 构造函数中没有指定时使用的负载因子
//     */
//    static final float DEFAULT_LOAD_FACTOR = 0.75f;
//
//
//    /**
//     * 使用树(而不是列表)来设置bin计数阈值。当向至少具有这么多节点的bin添加元素时，bin将转换为树。
//     * 该值必须大于2，并且应该至少为8，以便与树木移除中关于收缩后转换回普通垃圾箱的假设相吻合。
//     */
//    static final int TREEIFY_THRESHOLD = 8;
//
//    /**
//     * 用于在调整大小操作期间反树化(拆分)bin的bin计数阈值。应小于TREEIFY_THRESHOLD，且最多6个孔配合下进行缩孔检测。
//     */
//    static final int UNTREEIFY_THRESHOLD = 6;
//
//
//    /**
//     * 最小的表容量，其中的箱子可以treeified。(否则，如果一个bin中有太多节点，则会调整表的大小。)应至少为4 * TREEIFY_THRESHOLD，以避免调整大小和treeification阈值之间的冲突。
//     */
//    static final int MIN_TREEIFY_CAPACITY = 64;
//
//
//    static class Node<K,V> implements Map.Entry<K,V> {
//        final int hash;
//        final K key;
//        V value;
//        Node<K,V> next;
//
//        Node(int hash, K key, V value, Node<K,V> next) {
//            this.hash = hash;
//            this.key = key;
//            this.value = value;
//            this.next = next;
//        }
//
//        public final K getKey()        { return key; }
//        public final V getValue()      { return value; }
//        public final String toString() { return key + "=" + value; }
//
//        public final int hashCode() {
//            return Objects.hashCode(key) ^ Objects.hashCode(value);
//        }
//
//        public final V setValue(V newValue) {
//            V oldValue = value;
//            value = newValue;
//            return oldValue;
//        }
//
//        public final boolean equals(Object o) {
//            if (o == this)
//                return true;
//            if (o instanceof Map.Entry) {
//                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
//                return (Objects.equals(key, e.getKey()) &&
//                        Objects.equals(value, e.getValue()));
//            }
//            return false;
//        }
//    }
//
//    /*----------------------静态共用方法------------------------*/
//
//    /**
//     * 根据key值获取其hash值
//     * @param key 键
//     * @return 散列值
//     */
//    static final int hash(Object key){
//        int h;
//        //hashCode()返回的是一个32位的二进制数，在计算每个实例在数组中对应的下标时，会用hash值对数组长度取模来确定，如果hash值
//        //只有高位不同低位相同，怎么得到相同的数组地址，就会产生hash碰撞，因此在算hash时，将hashCode的值右移16位，然后用高16位
//        //和低16位异或，算出hash值，这样不管高位低位有哪一位变化，hash值都不同，减小了hash碰撞
//        return (key==null)?0:(h=key.hashCode())^(h>>>16);
//    }
//
//    /**
//     * 如果x的类是“Class C implementation Comparable”的形式，则返回该类，否则为null
//     * @return 类或null
//     */
//    static Class<?> comparableClassFor(Object x) {
//        if (x instanceof Comparable) {
//            Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
//            if ((c = x.getClass()) == String.class) // bypass checks
//                return c;
//            if ((ts = c.getGenericInterfaces()) != null) {
//                for (int i = 0; i < ts.length; ++i) {
//                    if (((t = ts[i]) instanceof ParameterizedType) &&
//                            ((p = (ParameterizedType)t).getRawType() ==
//                                    Comparable.class) &&
//                            (as = p.getActualTypeArguments()) != null &&
//                            as.length == 1 && as[0] == c) // type arg is c
//                        return c;
//                }
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * 如果x匹配kc (k的筛选比较类)，则返回k. compareto (x)，否则0。
//     */
//    @SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
//    static int compareComparables(Class<?> kc, Object k, Object x) {
//        return (x == null || x.getClass() != kc ? 0 :
//                ((Comparable)k).compareTo(x));
//    }
//
//
//    /**
//     * 返回给定目标容量的两倍幂。
//     * @param cap 容量
//     * @return 返回与给定容量离得最近的2倍幂
//     */
//    static final int tableSizeFor(int cap) {
//        int n = cap - 1;
//        n |= n >>> 1;
//        n |= n >>> 2;
//        n |= n >>> 4;
//        n |= n >>> 8;
//        n |= n >>> 16;
//        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
//    }
//
//    @Override
//    public Set<Entry<K, V>> entrySet() {
//        return null;
//    }
//
//
//    /*-----------------成员变量----------------*/
//
//    /**
//     * 第一次使用时初始化，并根据需要调整大小。当分配时，长度总是2的幂。(在某些操作中，我们还允许长度为零，以允许当前不需要的引导机制。)
//     */
//    transient Node<K,V>[] table;
//
//    /**
//     * 保存缓存entrySet ()。注意AbstractMap字段用于keySet()和values()。
//     */
//    transient Set<MyMap.Entry<K,V>> entrySet;
//
//    transient int size;
//
//    transient int modCount;
//
//    /**
//     * 要调整大小的下一个大小值(容量*负载因子)。
//     */
//    int threshold;
//
//    /**
//     * hash表的负载因子
//     */
//    final float loadFactor;
//
//    /*-----------------公共方法-----------------*/
//
//    public MyHashMap(int initialCapacity, float loadFactor){
//        if (initialCapacity < 0)
//            throw new IllegalArgumentException("Illegal initial capacity: " +
//                    initialCapacity);
//        if (initialCapacity > MAXIMUM_CAPACITY)
//            initialCapacity = MAXIMUM_CAPACITY;
//        if (loadFactor <= 0 || Float.isNaN(loadFactor))
//            throw new IllegalArgumentException("Illegal load factor: " +
//                    loadFactor);
//        this.loadFactor = loadFactor;
//        this.threshold = tableSizeFor(initialCapacity);
//    }
//
//    public MyHashMap(int initialCapacity) {
//        this(initialCapacity, DEFAULT_LOAD_FACTOR);
//    }
//
//
//    public MyHashMap() {
//        this.loadFactor = DEFAULT_LOAD_FACTOR;
//    }
//
//
//
//    final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
//        int s = m.size();
//        if (s > 0) {
//            if (table == null) { // pre-size
//                float ft = ((float)s / loadFactor) + 1.0F;
//                int t = ((ft < (float)MAXIMUM_CAPACITY) ?
//                        (int)ft : MAXIMUM_CAPACITY);
//                if (t > threshold)
//                    threshold = tableSizeFor(t);
//            }
//            else if (s > threshold)
//                resize();
//            for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
//                K key = e.getKey();
//                V value = e.getValue();
//                putVal(hash(key), key, value, false, evict);
//            }
//        }
//    }
//
//    public int size(){
//        return size;
//    }
//
//    public boolean isEmpty(){
//        return size == 0;
//    }
//
//    public V get(Object key){
//        Node<K,V> e;
//        return null;
//    }
//
//
//    final Node<K,V> getNode(int hash, Object key){
//        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
//        //如果table不为空，而且table长度大于0，而且table的那个桶不为null
//        if((tab=table)!=null&&(n = tab.length)>0&&(first = tab[(n-1)&hash])!=null){
//            //如果hash表的第一个元素的hash值等于要查询的hash值，而且第一个元素的key等于要查询的key
//            if(first.hash == hash && ((k = first.key) == key || (key != null && key.equals(k))))
//                return first;
//            //如果第一个不是空也不是要找的key,则需要看后面跟的是链表还是红黑树。
//            if((e=first.next)!=null){
//                if(first instanceof TreeNode)
//                    return ((TreeNode<K,V>) first).getTreeNode(hash,key);
//                do{
//                    if(e.hash == hash&&((k = e.key)==key||(key!=null &&key.equals(k))))
//                        return e;
//                }while ((e=e.next)!=null);
//            }
//        }
//        return null;
//    }
//
//    public boolean containsKey(Object key){
//        return getNode(hash(key),key)!=null;
//    }
//
//
//    /**
//     *
//     * @param hash key所对应的hash值
//     * @param key 键
//     * @param value 值
//     * @param onlyIfAbsent onlyIfAbsent为真时，不要更改现有值
//     * @param evict 如果为false，则该表处于创建模式
//     * @return 前一个值，如果没有，则为空
//     */
//    final V putVal(int hash,K key,V value,boolean onlyIfAbsent, boolean evict){
//        Node<K,V>[] tab;
//        Node<K,V> p;
//        int n, i;
//        if((tab = table) == null||(n=tab.length)==0)
//            n = (tab = resize()).length;
//        if ((p = tab[i = (n - 1) & hash]) == null)
//            tab[i] = newNode(hash, key, value, null);
//        else {
//            Node<K,V> e; K k;
//            if (p.hash == hash &&
//                    ((k = p.key) == key || (key != null && key.equals(k))))
//                e = p;
//            else if (p instanceof TreeNode)
//                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
//            else {
//                for (int binCount = 0; ; ++binCount) {
//                    if ((e = p.next) == null) {
//                        p.next = newNode(hash, key, value, null);
//                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
//                            treeifyBin(tab, hash);
//                        break;
//                    }
//                    if (e.hash == hash &&
//                            ((k = e.key) == key || (key != null && key.equals(k))))
//                        break;
//                    p = e;
//                }
//            }
//            if (e != null) { // existing mapping for key
//                V oldValue = e.value;
//                if (!onlyIfAbsent || oldValue == null)
//                    e.value = value;
//                afterNodeAccess(e);
//                return oldValue;
//            }
//        }
//        ++modCount;
//        if (++size > threshold)
//            resize();
//        afterNodeInsertion(evict);
//        return null;
//    }
//
//    /**
//     * 初始化或双精度表大小。如果为空，则按照字段阈值中包含的初始容量目标分配。
//     * 否则，因为我们使用的是2的幂展开，所以每个bin中的元素必须保持相同的索引，或者在新表中以2的幂偏移量移动。
//     * @return 表
//     */
//    final Node<K,V>[] resize() {
//        Node<K,V>[] oldTab = table; //当前所有元素所在的数组，称为老的元素数组
//        int oldCap = (oldTab == null) ? 0 : oldTab.length; //老的元素数组长度
//        int oldThr = threshold;	// 老的扩容阀值设置
//        int newCap, newThr = 0;	// 新数组的容量，新数组的扩容阀值都初始化为0
//        if (oldCap > 0) {	// 如果老数组长度大于0，说明已经存在元素
//            // PS1
//            if (oldCap >= MAXIMUM_CAPACITY) { // 如果数组元素个数大于等于限定的最大容量（2的30次方）
//                //扩容阀值设置为int最大值（2的31次方 -1 ），因为oldCap再乘2就溢出了。
//                threshold = Integer.MAX_VALUE;
//                return oldTab;	// 返回老的元素数组
//            }
//
//           /*
//            * 如果数组元素个数在正常范围内，那么新的数组容量为老的数组容量的2倍（左移1位相当于乘以2）
//            * 如果扩容之后的新容量小于最大容量  并且  老的数组容量大于等于默认初始化容量（16），那么新数组的扩容阀值设置为老阀值的2倍。（老的数组容量大于16意味着：要么构造函数指定了一个大于16的初始化容量值，要么已经经历过了至少一次扩容）
//            */
//            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
//                    oldCap >= DEFAULT_INITIAL_CAPACITY)
//                newThr = oldThr << 1; // double threshold
//        }
//
//        // PS2
//        // 运行到这个else if  说明老数组没有任何元素
//        // 如果老数组的扩容阀值大于0，那么设置新数组的容量为该阀值
//        // 这一步也就意味着构造该map的时候，指定了初始化容量。
//        else if (oldThr > 0) // initial capacity was placed in threshold
//            newCap = oldThr;
//        else {               // zero initial threshold signifies using defaults
//            // 能运行到这里的话，说明是调用无参构造函数创建的该map，并且第一次添加元素
//            newCap = DEFAULT_INITIAL_CAPACITY;	// 设置新数组容量 为 16
//            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY); // 设置新数组扩容阀值为 16*0.75 = 12。0.75为负载因子（当元素个数达到容量了4分之3，那么扩容）
//        }
//
//        // 如果扩容阀值为0 （PS2的情况）
//        if (newThr == 0) {
//            float ft = (float)newCap * loadFactor;
//            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
//                    (int)ft : Integer.MAX_VALUE);  // 参见：PS2
//        }
//        threshold = newThr; // 设置map的扩容阀值为 新的阀值
//        @SuppressWarnings({"rawtypes","unchecked"})
//        // 创建新的数组（对于第一次添加元素，那么这个数组就是第一个数组；对于存在oldTab的时候，那么这个数组就是要需要扩容到的新数组）
//        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
//        table = newTab;	// 将该map的table属性指向到该新数组
//        if (oldTab != null) {	// 如果老数组不为空，说明是扩容操作，那么涉及到元素的转移操作
//            for (int j = 0; j < oldCap; ++j) { // 遍历老数组
//                Node<K,V> e;
//                if ((e = oldTab[j]) != null) { // 如果当前位置元素不为空，那么需要转移该元素到新数组
//                    oldTab[j] = null; // 释放掉老数组对于要转移走的元素的引用（主要为了使得数组可被回收）
//                    // 如果元素没有有下一个节点，说明该元素不存在hash冲突
//                    if (e.next == null)
//                        newTab[e.hash & (newCap - 1)] = e;
////  PS3
////   把元素存储到新的数组中，存储到数组的哪个位置需要根据hash值和数组长度来进行取模
////   【hash值  %   数组长度】   =    【  hash值   & （数组长度-1）】
////   这种与运算求模的方式要求  数组长度必须是2的N次方，但是可以通过构造函数随意指定初始化容量呀，如果指定了17,15这种，岂不是出问题了就？没关系，最终会通过tableSizeFor方法将用户指定的转化为大于其并且最相近的2的N次方。 15 -> 16、17-> 32
////
////
////   如果该元素有下一个节点，那么说明该位置上存在一个链表了（hash相同的多个元素以链表的方式存储到了老数组的这个位置上了）
////   例如：数组长度为16，那么hash值为1（1%16=1）的和hash值为17（17%16=1）的两个元素都是会存储在数组的第2个位置上（对应数组下标为1），当数组扩容为32（1%32=1）时，hash值为1的还应该存储在新数组的第二个位置上，但是hash值为17（17%32=17）的就应该存储在新数组的第18个位置上了。
////   所以，数组扩容后，所有元素都需要重新计算在新数组中的位置。
//
//
//                    else if (e instanceof TreeNode)  // 如果该节点为TreeNode类型
//                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);  // 此处单独展开讨论
//                    else { // preserve order
//                        Node<K,V> loHead = null, loTail = null;  // 按命名来翻译的话，应该叫低位首尾节点
//                        Node<K,V> hiHead = null, hiTail = null;  // 按命名来翻译的话，应该叫高位首尾节点
////                        // 以上的低位指的是新数组的 0  到 oldCap-1 、高位指定的是oldCap 到 newCap - 1
//                        Node<K,V> next;
////                        // 遍历链表
//                        do {
//                            next = e.next;
////                            // 这一步判断好狠，拿元素的hash值  和  老数组的长度  做与运算
////                            // PS3里曾说到，数组的长度一定是2的N次方（例如16），如果hash值和该长度做与运算，结果为0，就说明该hash值小于数组长度（例如hash值为7），
//                            // 那么该hash值再和新数组的长度取摸的话mod值也不会放生变化，所以该元素的在新数组的位置和在老数组的位置是相同的，所以该元素可以放置在低位链表中。
//                            if ((e.hash & oldCap) == 0) {
////                                // PS4
//                                if (loTail == null) // 如果没有尾，说明链表为空
//                                    loHead = e; // 链表为空时，头节点指向该元素
//                                else
//                                    loTail.next = e; // 如果有尾，那么链表不为空，把该元素挂到链表的最后。
//                                loTail = e; // 把尾节点设置为当前元素
//                            }
//
////                            // 如果与运算结果不为0，说明hash值大于老数组长度（例如hash值为17）
////                            // 此时该元素应该放置到新数组的高位位置上
////                            // 例：老数组长度16，那么新数组长度为32，hash为17的应该放置在数组的第17个位置上，也就是下标为16，那么下标为16已经属于高位了，低位是[0-15]，高位是[16-31]
//                            else {  // 以下逻辑同PS4
//                                if (hiTail == null)
//                                    hiHead = e;
//                                else
//                                    hiTail.next = e;
//                                hiTail = e;
//                            }
//                        } while ((e = next) != null);
//                        if (loTail != null) { // 低位的元素组成的链表还是放置在原来的位置
//                            loTail.next = null;
//                            newTab[j] = loHead;
//                        }
//                        if (hiTail != null) {  // 高位的元素组成的链表放置的位置只是在原有位置上偏移了老数组的长度个位置。
//                            hiTail.next = null;
//                            newTab[j + oldCap] = hiHead; // 例：hash为 17 在老数组放置在0下标，在新数组放置在16下标；    hash为 18 在老数组放置在1下标，在新数组放置在17下标；
//                        }
//                    }
//                }
//            }
//        }
//        return newTab; // 返回新数组
//    }
//
//
//    static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
//        TreeNode<K,V> parent;  // red-black tree links
//        TreeNode<K,V> left;
//        TreeNode<K,V> right;
//        TreeNode<K,V> prev;    // needed to unlink next upon deletion
//        boolean red;
//        TreeNode(int hash, K key, V val, Node<K,V> next) {
//            super(hash, key, val, next);
//        }
//
//        /**
//         * Returns root of tree containing this node.
//         */
//        final TreeNode<K,V> root() {
//            for (TreeNode<K,V> r = this, p;;) {
//                if ((p = r.parent) == null)
//                    return r;
//                r = p;
//            }
//        }
//
//        /**
//         * Ensures that the given root is the first node of its bin.
//         */
//        static <K,V> void moveRootToFront(Node<K,V>[] tab, TreeNode<K,V> root) {
//            int n;
//            if (root != null && tab != null && (n = tab.length) > 0) {
//                int index = (n - 1) & root.hash;
//                TreeNode<K,V> first = (TreeNode<K,V>)tab[index];
//                if (root != first) {
//                    Node<K,V> rn;
//                    tab[index] = root;
//                    TreeNode<K,V> rp = root.prev;
//                    if ((rn = root.next) != null)
//                        ((TreeNode<K,V>)rn).prev = rp;
//                    if (rp != null)
//                        rp.next = rn;
//                    if (first != null)
//                        first.prev = root;
//                    root.next = first;
//                    root.prev = null;
//                }
//                assert checkInvariants(root);
//            }
//        }
//
//        /**
//         * Finds the node starting at root p with the given hash and key.
//         * The kc argument caches comparableClassFor(key) upon first use
//         * comparing keys.
//         */
//        final TreeNode<K,V> find(int h, Object k, Class<?> kc) {
//            TreeNode<K,V> p = this;
//            do {
//                int ph, dir; K pk;
//                TreeNode<K,V> pl = p.left, pr = p.right, q;
//                if ((ph = p.hash) > h)
//                    p = pl;
//                else if (ph < h)
//                    p = pr;
//                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
//                    return p;
//                else if (pl == null)
//                    p = pr;
//                else if (pr == null)
//                    p = pl;
//                else if ((kc != null ||
//                        (kc = comparableClassFor(k)) != null) &&
//                        (dir = compareComparables(kc, k, pk)) != 0)
//                    p = (dir < 0) ? pl : pr;
//                else if ((q = pr.find(h, k, kc)) != null)
//                    return q;
//                else
//                    p = pl;
//            } while (p != null);
//            return null;
//        }
//
//        /**
//         * Calls find for root node.
//         */
//        final TreeNode<K,V> getTreeNode(int h, Object k) {
//            return ((parent != null) ? root() : this).find(h, k, null);
//        }
//
//        /**
//         * Tie-breaking utility for ordering insertions when equal
//         * hashCodes and non-comparable. We don't require a total
//         * order, just a consistent insertion rule to maintain
//         * equivalence across rebalancings. Tie-breaking further than
//         * necessary simplifies testing a bit.
//         */
//        static int tieBreakOrder(Object a, Object b) {
//            int d;
//            if (a == null || b == null ||
//                    (d = a.getClass().getName().
//                            compareTo(b.getClass().getName())) == 0)
//                d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
//                        -1 : 1);
//            return d;
//        }
//
//        /**
//         * Forms tree of the nodes linked from this node.
//         * @return root of tree
//         */
//        final void treeify(Node<K,V>[] tab) {
//            TreeNode<K,V> root = null;
//            for (TreeNode<K,V> x = this, next; x != null; x = next) {
//                next = (TreeNode<K,V>)x.next;
//                x.left = x.right = null;
//                if (root == null) {
//                    x.parent = null;
//                    x.red = false;
//                    root = x;
//                }
//                else {
//                    K k = x.key;
//                    int h = x.hash;
//                    Class<?> kc = null;
//                    for (TreeNode<K,V> p = root;;) {
//                        int dir, ph;
//                        K pk = p.key;
//                        if ((ph = p.hash) > h)
//                            dir = -1;
//                        else if (ph < h)
//                            dir = 1;
//                        else if ((kc == null &&
//                                (kc = comparableClassFor(k)) == null) ||
//                                (dir = compareComparables(kc, k, pk)) == 0)
//                            dir = tieBreakOrder(k, pk);
//
//                        TreeNode<K,V> xp = p;
//                        if ((p = (dir <= 0) ? p.left : p.right) == null) {
//                            x.parent = xp;
//                            if (dir <= 0)
//                                xp.left = x;
//                            else
//                                xp.right = x;
//                            root = balanceInsertion(root, x);
//                            break;
//                        }
//                    }
//                }
//            }
//            moveRootToFront(tab, root);
//        }
//
//        /**
//         * Returns a list of non-TreeNodes replacing those linked from
//         * this node.
//         */
//        final Node<K,V> untreeify(MyHashMap<K,V> map) {
//            Node<K,V> hd = null, tl = null;
//            for (Node<K,V> q = this; q != null; q = q.next) {
//                Node<K,V> p = map.replacementNode(q, null);
//                if (tl == null)
//                    hd = p;
//                else
//                    tl.next = p;
//                tl = p;
//            }
//            return hd;
//        }
//
//        /**
//         * Tree version of putVal.
//         */
//        final TreeNode<K,V> putTreeVal(MyHashMap<K,V> map, Node<K,V>[] tab,
//                                       int h, K k, V v) {
//            Class<?> kc = null;
//            boolean searched = false;
//            TreeNode<K,V> root = (parent != null) ? root() : this;
//            for (TreeNode<K,V> p = root;;) {
//                int dir, ph; K pk;
//                if ((ph = p.hash) > h)
//                    dir = -1;
//                else if (ph < h)
//                    dir = 1;
//                else if ((pk = p.key) == k || (k != null && k.equals(pk)))
//                    return p;
//                else if ((kc == null &&
//                        (kc = comparableClassFor(k)) == null) ||
//                        (dir = compareComparables(kc, k, pk)) == 0) {
//                    if (!searched) {
//                        TreeNode<K,V> q, ch;
//                        searched = true;
//                        if (((ch = p.left) != null &&
//                                (q = ch.find(h, k, kc)) != null) ||
//                                ((ch = p.right) != null &&
//                                        (q = ch.find(h, k, kc)) != null))
//                            return q;
//                    }
//                    dir = tieBreakOrder(k, pk);
//                }
//
//                TreeNode<K,V> xp = p;
//                if ((p = (dir <= 0) ? p.left : p.right) == null) {
//                    Node<K,V> xpn = xp.next;
//                    TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn);
//                    if (dir <= 0)
//                        xp.left = x;
//                    else
//                        xp.right = x;
//                    xp.next = x;
//                    x.parent = x.prev = xp;
//                    if (xpn != null)
//                        ((TreeNode<K,V>)xpn).prev = x;
//                    moveRootToFront(tab, balanceInsertion(root, x));
//                    return null;
//                }
//            }
//        }
//
//
//        /**
//         * Removes the given node, that must be present before this call.
//         * This is messier than typical red-black deletion code because we
//         * cannot swap the contents of an interior node with a leaf
//         * successor that is pinned by "next" pointers that are accessible
//         * independently during traversal. So instead we swap the tree
//         * linkages. If the current tree appears to have too few nodes,
//         * the bin is converted back to a plain bin. (The test triggers
//         * somewhere between 2 and 6 nodes, depending on tree structure).
//         */
//        final void removeTreeNode(MyHashMap<K,V> map, Node<K,V>[] tab,
//                                  boolean movable) {
//            int n;
//            if (tab == null || (n = tab.length) == 0)
//                return;
//            int index = (n - 1) & hash;
//            TreeNode<K,V> first = (TreeNode<K,V>)tab[index], root = first, rl;
//            TreeNode<K,V> succ = (TreeNode<K,V>)next, pred = prev;
//            if (pred == null)
//                tab[index] = first = succ;
//            else
//                pred.next = succ;
//            if (succ != null)
//                succ.prev = pred;
//            if (first == null)
//                return;
//            if (root.parent != null)
//                root = root.root();
//            if (root == null || root.right == null ||
//                    (rl = root.left) == null || rl.left == null) {
//                tab[index] = first.untreeify(map);  // too small
//                return;
//            }
//            TreeNode<K,V> p = this, pl = left, pr = right, replacement;
//            if (pl != null && pr != null) {
//                TreeNode<K,V> s = pr, sl;
//                while ((sl = s.left) != null) // find successor
//                    s = sl;
//                boolean c = s.red; s.red = p.red; p.red = c; // swap colors
//                TreeNode<K,V> sr = s.right;
//                TreeNode<K,V> pp = p.parent;
//                if (s == pr) { // p was s's direct parent
//                    p.parent = s;
//                    s.right = p;
//                }
//                else {
//                    TreeNode<K,V> sp = s.parent;
//                    if ((p.parent = sp) != null) {
//                        if (s == sp.left)
//                            sp.left = p;
//                        else
//                            sp.right = p;
//                    }
//                    if ((s.right = pr) != null)
//                        pr.parent = s;
//                }
//                p.left = null;
//                if ((p.right = sr) != null)
//                    sr.parent = p;
//                if ((s.left = pl) != null)
//                    pl.parent = s;
//                if ((s.parent = pp) == null)
//                    root = s;
//                else if (p == pp.left)
//                    pp.left = s;
//                else
//                    pp.right = s;
//                if (sr != null)
//                    replacement = sr;
//                else
//                    replacement = p;
//            }
//            else if (pl != null)
//                replacement = pl;
//            else if (pr != null)
//                replacement = pr;
//            else
//                replacement = p;
//            if (replacement != p) {
//                TreeNode<K,V> pp = replacement.parent = p.parent;
//                if (pp == null)
//                    root = replacement;
//                else if (p == pp.left)
//                    pp.left = replacement;
//                else
//                    pp.right = replacement;
//                p.left = p.right = p.parent = null;
//            }
//
//            TreeNode<K,V> r = p.red ? root : balanceDeletion(root, replacement);
//
//            if (replacement == p) {  // detach
//                TreeNode<K,V> pp = p.parent;
//                p.parent = null;
//                if (pp != null) {
//                    if (p == pp.left)
//                        pp.left = null;
//                    else if (p == pp.right)
//                        pp.right = null;
//                }
//            }
//            if (movable)
//                moveRootToFront(tab, r);
//        }
//
//        /**
//         * Splits nodes in a tree bin into lower and upper tree bins,
//         * or untreeifies if now too small. Called only from resize;
//         * see above discussion about split bits and indices.
//         *
//         * @param map the map
//         * @param tab the table for recording bin heads
//         * @param index the index of the table being split
//         * @param bit the bit of hash to split on
//         */
//        final void split(MyHashMap<K,V> map, Node<K,V>[] tab, int index, int bit) {
//            TreeNode<K,V> b = this;
//            // Relink into lo and hi lists, preserving order
//            TreeNode<K,V> loHead = null, loTail = null;
//            TreeNode<K,V> hiHead = null, hiTail = null;
//            int lc = 0, hc = 0;
//            for (TreeNode<K,V> e = b, next; e != null; e = next) {
//                next = (TreeNode<K,V>)e.next;
//                e.next = null;
//                if ((e.hash & bit) == 0) {
//                    if ((e.prev = loTail) == null)
//                        loHead = e;
//                    else
//                        loTail.next = e;
//                    loTail = e;
//                    ++lc;
//                }
//                else {
//                    if ((e.prev = hiTail) == null)
//                        hiHead = e;
//                    else
//                        hiTail.next = e;
//                    hiTail = e;
//                    ++hc;
//                }
//            }
//
//            if (loHead != null) {
//                if (lc <= UNTREEIFY_THRESHOLD)
//                    tab[index] = loHead.untreeify(map);
//                else {
//                    tab[index] = loHead;
//                    if (hiHead != null) // (else is already treeified)
//                        loHead.treeify(tab);
//                }
//            }
//            if (hiHead != null) {
//                if (hc <= UNTREEIFY_THRESHOLD)
//                    tab[index + bit] = hiHead.untreeify(map);
//                else {
//                    tab[index + bit] = hiHead;
//                    if (loHead != null)
//                        hiHead.treeify(tab);
//                }
//            }
//        }
//
//        /* ------------------------------------------------------------ */
//        // Red-black tree methods, all adapted from CLR
//
//        static <K,V> TreeNode<K,V> rotateLeft(TreeNode<K,V> root,
//                                              TreeNode<K,V> p) {
//            TreeNode<K,V> r, pp, rl;
//            if (p != null && (r = p.right) != null) {
//                if ((rl = p.right = r.left) != null)
//                    rl.parent = p;
//                if ((pp = r.parent = p.parent) == null)
//                    (root = r).red = false;
//                else if (pp.left == p)
//                    pp.left = r;
//                else
//                    pp.right = r;
//                r.left = p;
//                p.parent = r;
//            }
//            return root;
//        }
//
//        static <K,V> TreeNode<K,V> rotateRight(TreeNode<K,V> root,
//                                               TreeNode<K,V> p) {
//            TreeNode<K,V> l, pp, lr;
//            if (p != null && (l = p.left) != null) {
//                if ((lr = p.left = l.right) != null)
//                    lr.parent = p;
//                if ((pp = l.parent = p.parent) == null)
//                    (root = l).red = false;
//                else if (pp.right == p)
//                    pp.right = l;
//                else
//                    pp.left = l;
//                l.right = p;
//                p.parent = l;
//            }
//            return root;
//        }
//
//        static <K,V> TreeNode<K,V> balanceInsertion(TreeNode<K,V> root,
//                                                    TreeNode<K,V> x) {
//            x.red = true;
//            for (TreeNode<K,V> xp, xpp, xppl, xppr;;) {
//                if ((xp = x.parent) == null) {
//                    x.red = false;
//                    return x;
//                }
//                else if (!xp.red || (xpp = xp.parent) == null)
//                    return root;
//                if (xp == (xppl = xpp.left)) {
//                    if ((xppr = xpp.right) != null && xppr.red) {
//                        xppr.red = false;
//                        xp.red = false;
//                        xpp.red = true;
//                        x = xpp;
//                    }
//                    else {
//                        if (x == xp.right) {
//                            root = rotateLeft(root, x = xp);
//                            xpp = (xp = x.parent) == null ? null : xp.parent;
//                        }
//                        if (xp != null) {
//                            xp.red = false;
//                            if (xpp != null) {
//                                xpp.red = true;
//                                root = rotateRight(root, xpp);
//                            }
//                        }
//                    }
//                }
//                else {
//                    if (xppl != null && xppl.red) {
//                        xppl.red = false;
//                        xp.red = false;
//                        xpp.red = true;
//                        x = xpp;
//                    }
//                    else {
//                        if (x == xp.left) {
//                            root = rotateRight(root, x = xp);
//                            xpp = (xp = x.parent) == null ? null : xp.parent;
//                        }
//                        if (xp != null) {
//                            xp.red = false;
//                            if (xpp != null) {
//                                xpp.red = true;
//                                root = rotateLeft(root, xpp);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        static <K,V> TreeNode<K,V> balanceDeletion(TreeNode<K,V> root,
//                                                   TreeNode<K,V> x) {
//            for (TreeNode<K,V> xp, xpl, xpr;;)  {
//                if (x == null || x == root)
//                    return root;
//                else if ((xp = x.parent) == null) {
//                    x.red = false;
//                    return x;
//                }
//                else if (x.red) {
//                    x.red = false;
//                    return root;
//                }
//                else if ((xpl = xp.left) == x) {
//                    if ((xpr = xp.right) != null && xpr.red) {
//                        xpr.red = false;
//                        xp.red = true;
//                        root = rotateLeft(root, xp);
//                        xpr = (xp = x.parent) == null ? null : xp.right;
//                    }
//                    if (xpr == null)
//                        x = xp;
//                    else {
//                        TreeNode<K,V> sl = xpr.left, sr = xpr.right;
//                        if ((sr == null || !sr.red) &&
//                                (sl == null || !sl.red)) {
//                            xpr.red = true;
//                            x = xp;
//                        }
//                        else {
//                            if (sr == null || !sr.red) {
//                                if (sl != null)
//                                    sl.red = false;
//                                xpr.red = true;
//                                root = rotateRight(root, xpr);
//                                xpr = (xp = x.parent) == null ?
//                                        null : xp.right;
//                            }
//                            if (xpr != null) {
//                                xpr.red = (xp == null) ? false : xp.red;
//                                if ((sr = xpr.right) != null)
//                                    sr.red = false;
//                            }
//                            if (xp != null) {
//                                xp.red = false;
//                                root = rotateLeft(root, xp);
//                            }
//                            x = root;
//                        }
//                    }
//                }
//                else { // symmetric
//                    if (xpl != null && xpl.red) {
//                        xpl.red = false;
//                        xp.red = true;
//                        root = rotateRight(root, xp);
//                        xpl = (xp = x.parent) == null ? null : xp.left;
//                    }
//                    if (xpl == null)
//                        x = xp;
//                    else {
//                        TreeNode<K,V> sl = xpl.left, sr = xpl.right;
//                        if ((sl == null || !sl.red) &&
//                                (sr == null || !sr.red)) {
//                            xpl.red = true;
//                            x = xp;
//                        }
//                        else {
//                            if (sl == null || !sl.red) {
//                                if (sr != null)
//                                    sr.red = false;
//                                xpl.red = true;
//                                root = rotateLeft(root, xpl);
//                                xpl = (xp = x.parent) == null ?
//                                        null : xp.left;
//                            }
//                            if (xpl != null) {
//                                xpl.red = (xp == null) ? false : xp.red;
//                                if ((sl = xpl.left) != null)
//                                    sl.red = false;
//                            }
//                            if (xp != null) {
//                                xp.red = false;
//                                root = rotateRight(root, xp);
//                            }
//                            x = root;
//                        }
//                    }
//                }
//            }
//        }
//
//        /**
//         * Recursive invariant check
//         */
//        static <K,V> boolean checkInvariants(TreeNode<K,V> t) {
//            TreeNode<K,V> tp = t.parent, tl = t.left, tr = t.right,
//                    tb = t.prev, tn = (TreeNode<K,V>)t.next;
//            if (tb != null && tb.next != t)
//                return false;
//            if (tn != null && tn.prev != t)
//                return false;
//            if (tp != null && t != tp.left && t != tp.right)
//                return false;
//            if (tl != null && (tl.parent != t || tl.hash > t.hash))
//                return false;
//            if (tr != null && (tr.parent != t || tr.hash < t.hash))
//                return false;
//            if (t.red && tl != null && tl.red && tr != null && tr.red)
//                return false;
//            if (tl != null && !checkInvariants(tl))
//                return false;
//            if (tr != null && !checkInvariants(tr))
//                return false;
//            return true;
//        }
//
//    }
//    Node<K,V> replacementNode(Node<K,V> p, Node<K,V> next) {
//        return new Node<>(p.hash, p.key, p.value, next);
//    }
//    Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
//        return new Node<>(hash, key, value, next);
//    }
//    // Create a tree bin node
//    TreeNode<K,V> newTreeNode(int hash, K key, V value, Node<K,V> next) {
//        return new TreeNode<>(hash, key, value, next);
//    }
//    void afterNodeAccess(Node<K,V> p) { }
//    void afterNodeInsertion(boolean evict) { }
//    void afterNodeRemoval(Node<K,V> p) { }
//    final void treeifyBin(Node<K,V>[] tab, int hash) {
//        int n, index; Node<K,V> e;
//        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
//            resize();
//        else if ((e = tab[index = (n - 1) & hash]) != null) {
//            TreeNode<K,V> hd = null, tl = null;
//            do {
//                TreeNode<K,V> p = replacementTreeNode(e, null);
//                if (tl == null)
//                    hd = p;
//                else {
//                    p.prev = tl;
//                    tl.next = p;
//                }
//                tl = p;
//            } while ((e = e.next) != null);
//            if ((tab[index] = hd) != null)
//                hd.treeify(tab);
//        }
//    }
//    TreeNode<K,V> replacementTreeNode(Node<K,V> p, Node<K,V> next) {
//        return new TreeNode<>(p.hash, p.key, p.value, next);
//    }
//
//    public static void main(String[] args){
//        System.out.println(tableSizeFor(7));
//    }
//
//}
