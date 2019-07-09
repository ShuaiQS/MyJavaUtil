package com.shuai.java.util;

import java.util.Collection;

/**
 * 用于在处理之前保存元素的集合。除了基本的收集操作之外，队列还提供额外的插入、提取和检查操作。
 * 这些方法都以两种形式存在:一个throwsan异常，如果操作失败，另一个返回一个特殊值(null或false，取决于操作)。
 * 后一种插入操作的形式是专门为容量受限的队列实现而设计的;在大多数实现中，插入操作不会失败。
 * 队列通常(但不一定)以FIFO(先进先出)方式对元素排序。例外情况包括优先级队列，它根据suppliedcomparator或元素的自然顺序对元素排序，
 * 以及后进先出(LIFO)队列(或堆栈)，后进先出(last-in-first-out)对元素排序。
 * 无论使用什么顺序，队列的头部都是将通过调用remove()或poll()删除的元素。在FIFO队列中，所有新元素都插入到队列的尾部。
 * 其他类型的队列可能使用不同的位置规则。每个队列实现都必须指定其有序属性。
 * 如果可能，offer方法插入一个元素，否则返回false。这与集合不同。该方法只能通过抛出未检查异常来添加元素。
 * offer方法是为在正常情况下而不是异常情况下(例如，在固定容量(或“有界”)队列中)使用而设计的。
 * remove()和poll()方法删除并返回队列的头部。确切地说，从队列中删除哪个元素是队列的排序策略的功能，该功能因实现而异。
 * 只有当队列为空时，remove()和poll()方法的行为不同:remove()方法抛出异常，而poll()方法返回null。
 * 元素()和peek()方法返回队列的头部，但不删除。
 * 队列接口不定义阻塞队列方法，这在并发编程中很常见。这些方法在java.util.concurrent中定义，它们等待元素出现或空间可用。
 * BlockingQueue接口，它扩展了这个接口。
 * 队列实现通常不允许插入null元素，尽管LinkedList等一些实现不禁止插入null元素。
 * 即使在允许null的实现中，也不应该将null插入队列，因为poll方法也将null作为一个特殊的返回值来表示队列不包含任何元素。
 * 队列实现通常不定义基于元素的方法equals和hashCode版本，而是从类对象继承基于标识的版本，因为基于元素的相等性并不总是为具有相同元素但排序属性不同的队列定义。
 */
public interface MyQueue<E> extends Collection<E> {
    /**
     * 如果可以在不违反容量限制的情况下立即将指定的元素插入此队列，成功时返回true，
     * 如果当前没有空间可用，则抛出IllegalStateException。
     * add方法相对与offer方法而言，有一个专门抛出的异常IllegalStateException，代表由于容量限制，
     * 导致不能添加元素的异常。
     * @param e 需要添加的元素
     * @return 返回成功与否
     */
    boolean add(E e);

    /**
     * 如果可以在不违反容量限制的情况下立即将指定的元素插入到此队列中。
     * 当使用受容量限制的队列时，此方法通常比add更可取，因为add只能通过抛出异常才能插入元素。
     * 在容量限制的队列中， add方法通过抛异常的方式表示容量已满，offer方法通过返回
     *  false的方式表示容量已满，抛异常处理更加耗时，offer直接返回false的方式更好
     * @param e 需要插入的元素
     * @return 返回成功与否
     */
    boolean offer(E e);

    /**
     * 检索并删除此队列的头。此方法与poll的不同之处在于，它只在队列为空时抛出异常。
     * @return 队列的头
     * @throws java.util.NoSuchElementException 如果队列为空抛出该异常
     */
    E remove();

    /**
     * 检索并删除此队列的头部，如果该队列为空，则返回null。
     * @return 队列的头
     */
    E poll();

    /**
     * 检索但不删除此队列的头。此方法与peek的不同之处在于，它只在队列为空时抛出异常。
     * @return 队列的头
     * @throws java.util.NoSuchElementException 如果队列为空抛出该异常
     */
    E element();

    /**
     * 检索但不删除此队列的头部，如果该队列为空，则返回null。
     * @return 此队列的头，如果该队列为空，则为空
     */
    E peek();

}
