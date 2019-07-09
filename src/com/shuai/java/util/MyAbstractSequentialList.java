package com.shuai.java.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 这个类提供了Listinterface的一个框架实现，以最小化实现这个由“顺序访问”数据存储(如链表)支持的接口所需的工作。
 * 对于随机访问数据(例如数组)，应该优先使用AbstractList。
 * 这个类与AbstractList类相反，它实现了列表迭代器顶部的“随机访问”方法(get(int index)、set(int index、E元素)、
 * add(int index、E元素)和remove(int index)，而不是其他方法。
 * 要实现列表，程序员只需要扩展这个类并为listIterator和sizemethod提供实现。
 * 对于不可修改的列表，程序员只需要实现最后一个迭代器的hasNext、next、hasPrevious、previous和index方法。
 * 对于可修改列表，程序员还应该实现listiterator的set方法。对于可变大小的列表，程序员还应该实现列表迭代器的remove和add方法。
 * @param <E> 元素类型
 */
public abstract class MyAbstractSequentialList<E> extends MyAbstractList<E> {

    protected MyAbstractSequentialList(){

    }


    /**
     *这个实现首先获得一个指向索引元素的列表迭代器(使用listIterator(index))。然后，它使用ListIterator获取元素。然后返回它。
     * @param index 索引
     * @return 返回列表中指定位置的元素。
     */
    public E get(int index) {
        try{
            return listIterator(index).next();
        }catch (NoSuchElementException e){
            throw new IndexOutOfBoundsException("Index"+index);
        }
    }


    /**
     * 这个实现首先获得一个指向索引元素的列表迭代器(使用listIterator(index))。
     * 然后，它使用ListIterator获取当前元素。然后用ListIterator.set替换它。
     * 如果列表迭代器不执行set操作，这个实现将抛出一个UnsupportedOperationException。
     * @param index 要更改元素的索引
     * @param element 更改后的元素值
     * @return 更改前的元素值
     */
    public E set(int index, E element){
        try {
            ListIterator<E> e = listIterator(index);
            E oldVal = e.next();
            e.set(element);
            return oldVal;
        }catch (NoSuchElementException e){
            throw new IndexOutOfBoundsException("Index"+index);
        }
    }


    /**
     * 在列表中的指定位置插入指定元素(可选操作)。将当前位于该位置的元素(如果有的话)和随后的任何元素向右移动(将一个元素添加到它们的索引中)。
     * 这个实现首先获得一个指向索引元素的列表迭代器(使用listIterator(index))。然后，用ListIterator.add插入指定的元素。
     * 如果列表迭代器没有执行add操作，这个实现将抛出一个UnsupportedOperationException。
     * @param index 索引
     * @param element 元素
     */
    public void add(int index, E element) {
        try {
            listIterator(index).add(element);
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }


    /**
     *这个实现首先获得一个指向索引元素的列表迭代器(使用listIterator(index))。然后，它使用ListIterator.remove删除元素。
     * 如果列表迭代器没有执行删除操作，这个实现将抛出一个UnsupportedOperationException。
     * @param index 删除元素的索引值
     * @return 删除列表中指定位置的元素(optionaloperation)。将任何后续元素向左移动(从它们的索引中减去一个)。返回从列表中删除的元素。
     */
    public E remove(int index){
        try {
            ListIterator<E> e = listIterator(index);
            E outCast = e.next();
            e.remove();
            return outCast;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }


    /**
     * 将指定集合中的所有元素插入到此列表的指定位置(可选操作)。
     * 将当前位于该位置的元素(如果有的话)和任何随后的元素向右移动(增加它们的索引)。
     * 新元素将按特定集合的迭代器返回它们的顺序出现在这个列表中。
     * 如果在操作进行期间修改了指定的集合，则此操作的行为未定义。
     * (注意，如果specifiedcollection是这个列表，并且它不是空的，则会发生这种情况。)
     * 此实现获取指定集合上的迭代器和指向索引元素(使用listIterator(index))的列表上的列表迭代器。
     * 然后，它遍历specifiedcollection，使用ListIterator一次将从迭代器获得的元素插入到这个列表中。
     * 添加后面跟着ListIterator。接下来(跳过添加的元素)。
     * @param index 要插入集合的下标
     * @param c 集合
     * @return 插入成功与否
     */
    public boolean addAll(int index, Collection<? extends E> c){
        try {
            boolean modified = false;
            ListIterator<E> e1 = listIterator(index);
            Iterator<? extends E> e2 = c.iterator();
            while (e2.hasNext()) {
                e1.add(e2.next());
                modified = true;
            }
            return modified;
        } catch (NoSuchElementException exc) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }
    }

    /**
     * 返回此列表中元素的迭代器(按适当的顺序)。
     * @return 迭代器
     */
    public Iterator<E> iterator() {
        return listIterator();
    }


    public abstract ListIterator<E> listIterator(int index);
}
