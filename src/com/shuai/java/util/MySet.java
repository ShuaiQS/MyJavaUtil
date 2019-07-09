package com.shuai.java.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

/**
 * 不包含重复元素的集合。更正式地说，集合不包含e1和e2元素对，因此e1. = (e2)，并且最多只包含一个空元素。顾名思义，这个接口对数学集抽象进行建模。
 * Set接口对所有构造函数的契约以及add、equals和hashCode方法的契约附加了一些规定，这些规定超出了从Collection接口继承的规定。为了方便起见，这里还包括了其他继承方法的声明
 * 关于构造函数的附加规定是，所有构造函数都必须创建一个不包含重复元素的集合
 * @param <E>
 */
public interface MySet<E> extends Collection<E> {

    int size();
    boolean isEmpty();
    boolean contains(Object o);
    Iterator<E> iterator();
    Object[] toArray();
    <T> T[] toArray(T[] a);
    boolean add(E e);
    boolean remove(Object o);
    boolean containsAll(Collection<?> c);
    boolean addAll(Collection<? extends E> c);
    boolean retainAll(Collection<?> c);
    boolean removeAll(Collection<?> c);
    void clear();
    boolean equals(Object o);
    int hashCode();
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this,Spliterator.DISTINCT);
    }
}
