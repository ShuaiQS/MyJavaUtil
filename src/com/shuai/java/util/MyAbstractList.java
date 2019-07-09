package com.shuai.java.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;




/**
 * AbstractList继承了AbstractCollection实现了List接口，此类提供 List 接口的骨干实现，以最大限度地减少实现“随机访问”数据存储（如数组）支持的该接口所需的工作。
 * 对于连续的访问数据（如链表），应优先使用 AbstractSequentialList，而不是此类.  要实现一个不可修改的列表，程序员只需扩展这个类，并实现其中是get()，和size()方法。
 *  要实现可修改的列表，编程人员必须另外重写 set(int, E) 方法（否则将抛出UnsupportedOperationException）。
 * 如果列表为可变大小，则编程人员必须另外重写 add(int, E) 和 remove(int) 方法。
 * @author YUETAO
 *
 * @param <E>
 */
public abstract class MyAbstractList<E> extends AbstractCollection<E> implements List<E> {

    /**
     * 默认构造方法，使用protected除了继承，其他类无法调用
     */
    protected MyAbstractList(){

    }

    /**
     * 向集合末尾里面添加元素
     */
    public boolean add(E e) {
        add(size(),e);
        return true;

    }

    abstract public E get(int index);

    /**
     * 子类要重写这个方法，不然会抛出异常
     */
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * 子类要重写此方法，不然会抛出异常
     */
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    /**
     * 子类要重写此方法，不然会抛出异常
     */
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * 在集合中找到第一个o,返回索引，若没有返回-1
     * 迭代器是List接口中的迭代器
     */
    public int indexOf(Object o) {
        ListIterator<E> iterator = listIterator();
        if(o==null) {
            while(iterator.hasNext()) {
                if(iterator.next()==null)
                    return iterator.previousIndex();
            }
        }else {
            while(iterator.hasNext()) {
                if(o.equals(iterator.next()))
                    return iterator.previousIndex();
            }
        }
        return -1;
    }

    /**
     * 从后向前遍历集合，找到第一个o返回下标
     * @param o
     * @return
     */
    public int lastIndexOf(Object o) {
        ListIterator<E> iterator = listIterator(size());
        if(o==null) {
            while (iterator.hasPrevious()) {
                if(iterator.previous()==null)
                    return iterator.nextIndex();
            }
        }else {
            while(iterator.hasPrevious()) {
                if(o.equals(iterator.hasPrevious()))
                    return iterator.previousIndex();
            }
        }
        return -1;
    }


    /**
     * 删除集合中从fromIndex到toIndex中的元素
     * @param fromIndex
     * @param toIndex
     */
    protected void removeRange(int fromIndex, int toIndex) {
        ListIterator<E> iterator = listIterator(fromIndex);
        for(int i=0, n=toIndex-fromIndex;i<n;i++) {
            iterator.next();
            iterator.remove();
        }
    }


    /**
     * 此列表在结构上被修改的次数。结构修改是指改变列表的大小，或者以一种正在进行的迭代可能产生不正确结果的方式扰乱列表。
     * 该字段由迭代器和列表迭代器实现使用，列表迭代器实现由迭代器和listIterator方法返回。
     * 如果该字段的值发生意外更改，迭代器(或列表迭代器)将抛出ConcurrentModificationException来响应下一个、删除、上一个、设置或添加操作。
     * 这提供了快速故障行为，而不是在迭代过程中面对并发修改时的非确定性行为。
     * 子类使用此字段是可选的。如果子类希望提供快速故障迭代器(和列表迭代器)，那么它只需在add(int, E)和remove(int)方法中增加这个字段(以及它覆盖的导致列表结构修改的任何其他方法)。
     * 添加(int, E)或删除(int)的单个调用必须只向该字段添加一个，否则迭代器(和列表迭代器)将抛出伪concurrentmodificationexception。如果实现不希望提供故障快速迭代器，则可以忽略此字段。
     */
    protected transient int modCount = 0;
    /**
     * 清空集合
     */
    public void clear() {
        removeRange(0, size());
    }


    /**
     * 将指定集合中的所有元素插入到此列表的指定位置(可选操作)。
     * 将当前位于该位置的元素(如果有的话)和随后的任何元素移动到右边(增加它们的索引)。
     * 新元素将按指定集合的迭代器返回它们的顺序出现在这个列表中。
     * 如果在操作进行期间修改了指定的集合，则此操作的行为未定义。
     * (注意，如果指定的集合是这个列表，并且它不是空的，则会发生这种情况。)
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        boolean modified = false;
        for(E e:c) {
            add(index++,e);
            modified = true;
        }

        return modified;
    }

    /**
     * 向集合添加元素时，检查index是否超出数组下标界限
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * 超出下界提示
     * @param index
     * @return
     */
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size();
    }


    /**
     * 集合上的迭代器。迭代器代替了Java集合框架中的枚举。迭代器与枚举有两方面的不同:
     * 迭代器允许调用者在迭代期间使用定义良好的语义从基础集合中删除元素。
     * 方法名得到了改进
     */
    public Iterator<E> iterator(){
        return new Itr();
    }


    /**
     * 用于列表的迭代器，它允许程序员以任意方向遍历列表，在迭代期间修改列表，并获取迭代器在列表中的当前位置。
     * ListIterator没有当前元素;它的光标位置总是位于调用previous()返回的元素和调用next()返回的元素之间。
     * 长度为n的列表的迭代器有n+1个可能的光标位置.
     * 注意，remove和set(对象)方法不是根据游标位置定义的;它们的定义是对调用next或previous()返回的最后一个元素进行操作。
     */
    public ListIterator<E> listIterator(){
        return listIterator(0);
    }


    /**
     * 返回列表中元素的列表迭代器(按适当的顺序)，从列表中的指定位置开始。指定的索引指示将由对next的初始调用返回的第一个元素。对previous的初始调用将返回具有指定索引- 1的元素。
     * 这个实现返回ListIterator接口的一个简单实现，扩展了Iterator()方法返回的Iterator接口的实现。ListIterator实现依赖于支持列表的get(int)、set(int, E)、add(int, E)和remove(int)方法。
     */

    public ListIterator<E> listIterator(final int index){
        rangeCheckForAdd(index);
        return new ListItr(index);
    }

    /**
     * 这里this是多态的，指的是那个外部调用subList方法的那个List对象，先判断this是否实现了RandomAccess接口，实现返回RandomAccessSublist否则返回SubList
     */
    public List<E> subList(int fromIndex, int toIndex) {
        return (this instanceof RandomAccess ?
                new RandomAccessSubList<>(this, fromIndex, toIndex) :
                new SubList<>(this, fromIndex, toIndex));
    }

    /**
     * 将指定的对象与此列表进行相等性比较。
     * 当且仅当指定的对象也是一个列表时，返回true，两个列表的大小相同，且两个列表中所有对应的元素对都相等。
     * 如果(e1==null ?e2 = = null: . equals (e2)))。
     * 换句话说，如果两个列表以相同的顺序包含相同的元素，那么它们就被定义为相等的。
     */
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(!(o instanceof List))
            return false;
        ListIterator<E> e1 = listIterator();
        ListIterator<?> e2 = ((List<?>)o).listIterator();
        while(e1.hasNext()&&e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }

        return !(e1.hasNext() || e2.hasNext());
    }


    public int hashCode() {
        int hashCode = 1;
        for(E e:this)
            hashCode = 31*hashCode+(e==null? 0 : e.hashCode());
        return hashCode;
    }


    private class Itr implements Iterator<E> {
        /**
         * 下一个元素的索引
         */
        int cursor = 0;

        /**
         * 最近一次调用next或previous返回的元素的索引。如果此元素被要删除的调用删除，则重置为-1。
         */
        int lastRet = -1;

        /**
         * 记录容器修改次数
         */
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size();
        }

        public E next() {
            checkForComodification();  //遍历时检查有没有被修改过
            try {
                int i = cursor;
                E next = get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            //这行代码解释了,不能未调用next()就remove() 也不能连续调用两次remove()
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyAbstractList.this.remove(lastRet);
                if (lastRet < cursor)
                    cursor--;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }


    /**
     * ListIterator的实现,和Iterator实现基本相同,它继承了Itr类,区别在于它支持在调用next或者privious后,添加元素.这里根据源代码分析.
     * 在调用next的时候,调用add,add方法会在cursor指针(下一个要next的元素)的位置添加元素.并将cursor+1,使得next的调用无法返回添加的元素.
     * 在调用privious的时候,调用add,add方法会在已经返回的元素的位置处,添加元素,并将cursor+1,这时候,下次返回的将是cursor-1的元素,即新添加的元素.
     * @author YUETAO
     *
     */
    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            //将元素指针设为指定值
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public E previous() {
            //检查元素是否被修改
            checkForComodification();
            try {
                int i = cursor - 1;
                E previous = get(i);
                //结束方法调用的时候，cursor位置停留在返回的元素的位置上，这点与next不同
                lastRet = cursor = i;
                return previous;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor-1;
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyAbstractList.this.set(lastRet, e);
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();
            /*
             * add方法在next之后调用下一个next无法返回新添加的元素
             * 在privious后调用，下一个privious可以返回
             * 实际上无论是next还是privious方法后调用，
             * add方法都使指针向前移动了1位
             */
            try {
                int i = cursor;
                MyAbstractList.this.add(i, e);
                lastRet = -1;
                cursor = i + 1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * SubList继承AbstractList抽象类，AbstractList实现了List接口，所以SubList说到底就是一个List的实现类，
     * 主要用于返回List的视图，这个视图是原List对象中的一部分，确实是一部分，直接将原List对象引用到新的子视图的List，对子视图进行改变，原List对象也会随之改变
     * @author YUETAO
     *
     * @param <E>
     */
    class SubList<E> extends MyAbstractList<E> {
        //l 就是存放母List的那个引用，当List对象调用subList方法时，会调用abstractList中的subList的实现
        private final MyAbstractList<E> l;
        //开始子List在母List中的开始位置
        private final int offset;

        //记录大小
        private int size;

        /**
         * 构造方法，做3个对fromIndex和toIndex的检查后，将list 付给l，fromIndex付给offset，原list对象的modCount付给expectedModCount
         * 其实就是将原来的list对象放到SubList中，同时将子列表的开始下标和结束下标付给SubList
         * @param list
         * @param fromIndex
         * @param toIndex
         */
        SubList(MyAbstractList<E> list, int fromIndex, int toIndex) {
            if (fromIndex < 0)
                throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
            if (toIndex > list.size())
                throw new IndexOutOfBoundsException("toIndex = " + toIndex);
            if (fromIndex > toIndex)
                throw new IllegalArgumentException("fromIndex(" + fromIndex +
                        ") > toIndex(" + toIndex + ")");
            l = list;
            offset = fromIndex;
            size = toIndex - fromIndex;
            this.modCount = l.modCount;
        }

        public E set(int index, E element) {
            //检查越界
            rangeCheck(index);
            //检查同步
            checkForComodification();
            return l.set(index+offset, element);
        }

        public E get(int index) {
            rangeCheck(index);
            checkForComodification();
            return l.get(index+offset);
        }

        public int size() {
            checkForComodification();
            return size;
        }

        /**
         * 将元素添加到子列表的index位置，首先检查是否同步，调用母列表的add(index,e)方法，
         */
        public void add(int index, E element) {
            rangeCheckForAdd(index);
            checkForComodification();
            l.add(index+offset, element);
            this.modCount = l.modCount;
            size++;
        }

        public E remove(int index) {
            rangeCheck(index);
            checkForComodification();
            E result = l.remove(index+offset);
            this.modCount = l.modCount;
            size--;
            return result;
        }

        protected void removeRange(int fromIndex, int toIndex) {
            checkForComodification();
            l.removeRange(fromIndex+offset, toIndex+offset);
            this.modCount = l.modCount;
            size -= (toIndex-fromIndex);
        }

        public boolean addAll(Collection<? extends E> c) {
            return addAll(size, c);
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            rangeCheckForAdd(index);
            int cSize = c.size();
            if (cSize==0)
                return false;

            checkForComodification();
            l.addAll(offset+index, c);
            this.modCount = l.modCount;
            size += cSize;
            return true;
        }

        public Iterator<E> iterator() {
            return listIterator();
        }

        /**
         * 这个ListIterator匿名类本身操作的也是母列表中的listIterator，用i来存储这个参数
         * 其他方法基本都是通过i这个ListIterator来对迭代进行操作，方法类似AbstractList中ListIterator
         */
        public ListIterator<E> listIterator(final int index) {
            checkForComodification();
            rangeCheckForAdd(index);

            return new ListIterator<E>() {
                private final ListIterator<E> i = l.listIterator(index+offset);

                public boolean hasNext() {
                    return nextIndex() < size;
                }

                public E next() {
                    if (hasNext())
                        return i.next();
                    else
                        throw new NoSuchElementException();
                }

                public boolean hasPrevious() {
                    return previousIndex() >= 0;
                }

                public E previous() {
                    if (hasPrevious())
                        return i.previous();
                    else
                        throw new NoSuchElementException();
                }

                public int nextIndex() {
                    return i.nextIndex() - offset;
                }

                public int previousIndex() {
                    return i.previousIndex() - offset;
                }

                public void remove() {
                    i.remove();
                    SubList.this.modCount = l.modCount;
                    size--;
                }

                public void set(E e) {
                    i.set(e);
                }

                public void add(E e) {
                    i.add(e);
                    SubList.this.modCount = l.modCount;
                    size++;
                }
            };
        }
        public List<E> subList(int fromIndex, int toIndex) {
            return new SubList<>(this, fromIndex, toIndex);
        }

        private void rangeCheck(int index) {
            if (index < 0 || index >= size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private void rangeCheckForAdd(int index) {
            if (index < 0 || index > size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private String outOfBoundsMsg(int index) {
            return "Index: "+index+", Size: "+size;
        }

        private void checkForComodification() {
            if (this.modCount != l.modCount)
                throw new ConcurrentModificationException();
        }

    }

    @SuppressWarnings("hiding")
    class RandomAccessSubList<E> extends SubList<E> implements RandomAccess {
        RandomAccessSubList(MyAbstractList<E> list, int fromIndex, int toIndex) {
            super(list, fromIndex, toIndex);
        }

        public List<E> subList(int fromIndex, int toIndex) {
            return new RandomAccessSubList<>(this, fromIndex, toIndex);
        }
    }

}
