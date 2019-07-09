package com.shuai.java.util;

import java.util.Iterator;

/**
 * 支持在两端插入和删除元素的线性集合。deque是“双端队列”的缩写，通常读作“deck”。大多数deque实现对它们可能包含的元素数量没有固定的限制，但是这个接口支持容量限制deques以及那些没有固定大小限制的deques。
 * 这个接口定义了访问deque两端元素的方法。方法用于插入、删除和检查元素。这些方法都以两种形式存在:一种方法在操作失败时抛出异常，另一种方法返回特殊值(根据操作的不同，可以是null，也可以是false)。
 * 后一种插入操作形式是专门为容量受限的Deque实现而设计的;在大多数实现中，insertoperations不能失败。
 * @param <E>
 */
public interface MyDeque<E> extends MyQueue<E> {

    /**
     * 如果能够在不违反容量限制的情况将在deque前面插入指定的元素，则在当前没有可用空间的情况下抛出IllegalStateException。
     * 当使用容量受限的deque时，通常优先使用offerFirst方法。
     * @param e 要添加的元素
     */
    void addFirst(E e);

    /**
     * 如果可以在不违反容量限制的情况下将在deque后面插入指定的元素，并在当前没有可用空间的情况下抛出IllegalStateException。
     * 在使用容量受限的deque时，通常最好使用offerLast方法。
     * @param e 要添加的元素
     */
    void addLast(E e);


    /**
     * 除非违反容量限制，否则将在deque前面插入指定的元素。
     * 当使用容量受限的deque时，此方法通常优于addFirst方法，后者仅通过抛出异常就不能插入元素。
     * @param e 要添加的元素
     */
    void offerFirst(E e);

    /**
     * 除非违反容量限制，否则在deque的末尾插入指定的元素。
     * 当使用容量受限的deque时，此方法通常优于addLast方法，后者仅通过抛出异常就不能插入元素。
     * @param e 要添加的元素
     */
    void offerLast(E e);

    /**
     * 检索并删除此deque的第一个元素。
     * 此方法与pollFirst的唯一不同之处在于，当deque为空时，它会抛出异常。
     * @return 被删除的元素
     */
    E removeFirst();

    /**
     * 检索并删除此deque的最后一个元素。
     * 此方法与pollLast唯一的不同之处在于，当deque为空时，它会抛出异常。
     * @return 被删除的元素
     */
    E removeLast();

    /**
     * 检索并删除此deque的第一个元素，如果该deque为空，则返回null。
     * @return 这个deque的头部，如果deque是空的，则为空
     */
    E pollFirst();


    /**
     * 检索并删除此deque的最后一个元素，如果该deque为空，则返回null。
     * @return 这个deque的尾部，如果deque是空的，则为空
     */
    E pollLast();

    /**
     * 检索但不删除此deque的第一个元素。
     * 此方法与peekFirst的不同之处在于，它只在deque为空时抛出异常。
     * @return 此deque的第一个元素
     */
    E getFirst();


    /**
     * 检索但不删除此deque的最后一个元素。
     * 此方法与peekLast的不同之处在于，它只在deque为空时抛出异常。
     * @return 此deque的最后一个元素
     */
    E getLast();


    /**
     * 检索但不删除此deque的第一个元素，如果该deque为空，则返回null。
     * @return 此deque的第一个元素
     */
    E peekFirst();


    /**
     * 检索但不删除此deque的最后一个元素，如果该deque为空，则返回null。
     * @return 此deque的最后一个元素
     */
    E peekLast();


    /**
     * 从该deque中移除指定元素的第一个匹配项。如果deque不包含该元素，它将保持不变。
     * 更正式地说，删除第一个元素e 。如果这个deque包含指定的元素，则返回true。
     * @param o 如果存在，则从该deque中删除元素
     * @return 如果由于这个调用而删除了一个元素，则为true
     */
    boolean removeFirstOccurence(Object o);


    /**
     * 从该deque中移除指定元素的最后一个匹配项。如果deque不包含该元素，它将保持不变。
     * 更正式地说，删除最后一个元素e 。如果这个deque包含指定的元素，则返回true。
     * @param o 如果存在，则从该deque中删除元素
     * @return 如果由于这个调用而删除了一个元素，则为true
     */
    boolean removeLastOccurrence(Object o);


    /**
     * 如果能够在不违反容量限制的情况下立即将指定的元素插入到deque所表示的队列中(换句话说，在deque的末尾)，成功后返回true，
     * 如果当前没有空间可用，则抛出IllegalStateException。当使用容量受限的deque时，通常最好使用offer。
     * @param e 需要添加的元素
     * @return 如果由于这个调用而添加了一个元素，则为true
     */
    boolean add(E e);


    /**
     * 如果可以在不违反容量限制的情况下立即将指定的元素插入deque表示的队列中(换句话说，在deque的末尾)，成功时返回true，
     * 如果当前没有可用空间则返回false。当使用容量受限的deque时，此方法通常比add方法更可取，add方法只能通过抛出异常才能插入元素。
     * @param e 需要插入的元素
     * @return 如果由于这个调用而添加了一个元素，则为true
     */
    boolean offer(E e);

    /**
     * 检索并删除由这个deque表示的队列的头部(换句话说，这个deque的第一个元素)。
     * 此方法与poll的不同之处在于，它只在deque为空时抛出异常。
     * @return 此deque表示的队列的头部
     */
    E remove();


    /**
     * 检索并删除由这个deque表示的队列的头部(换句话说，就是这个deque的第一个元素)，如果这个deque为空，则返回null。
     * @return deque的第一个元素，如果deque为空，则为null
     */
    E poll();


    /**
     * 检索但不删除由这个deque表示的队列的头部(换句话说，这个deque的第一个元素)。
     * 这个方法与peek的不同之处在于，它只在deque为空时抛出异常。
     * @return 这个deque表示的队列的头
     */
    E element();


    /**
     * 检索但不删除由这个deque表示的队列的头部(换句话说，这个deque的第一个元素)，如果这个deque为空，则返回null。
     * @return 此deque表示的队列的头部，如果该deque为空，则为null
     */
    E peek();

    //*** Stack methods ***

    /**
     * 如果可以在不违反容量限制的情况下立即将元素推入由deque表示的堆栈(换句话说，位于deque的顶部)，
     * 如果当前没有空间可用，则抛出IllegalStateException。
     * 这个方法等价于addFirst。
     * @param e 要推入的元素
     */
    void push(E e);


    /**
     * 从这个deque表示的堆栈中弹出一个元素。换句话说，删除并返回deque的第一个元素。
     * @return deque的第一个元素
     */
    E pop();

    //*** Collection methods ***

    /**
     * 从该deque中移除指定元素的第一个匹配项。如果deque不包含该元素，它将保持不变。
     * 如果这个deque包含指定的元素，则返回true.这个方法等价于removeFirstOccurrence(Object)。
     * @param o 如果存在，则从该deque中删除o元素
     * @return 如果由于这个调用而删除了一个元素返回true
     */
    boolean remove(Object o);

    /**
     * 如果此deque包含指定的元素，则返回true。更正式地说，当且仅当deque包含至少一个元素e
     * @param o o元素，其存在于此deque中有待测试
     * @return 如果这个deque包含指定的元素，返回true
     */
    boolean contains(Object o);

    /**
     * 返回此deque队列中的元素个数
     * @return 此deque队列中的元素个数
     */
    public int size();

    /**
     * 按正确的顺序返回deque中元素的迭代器。
     * 元素将按从第一个(head)到最后一个(tail)的顺序返回。
     * @return 迭代器
     */
    Iterator<E> iterator();

    /**
     * 以相反的顺序返回deque中元素的迭代器。
     * 元素将按从最后(tail)到第一个(head)的顺序返回。
     * @return 反向迭代器
     */
    Iterator<E> descendingIterator();
}
