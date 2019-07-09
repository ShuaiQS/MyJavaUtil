package com.shuai.java.util;

import sun.misc.SharedSecrets;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class MyArrayList<E> extends MyAbstractList<E>
    implements List<E>,RandomAccess,Cloneable,java.io.Serializable {

    private static final long serialVersionUID = 8683452581122892189L;
    /**
     * 默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 用于空实例的共享空数组实例。
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 共享空数组实例，用于默认大小的空实例。
     * 我们将其与EMPTY_ELEMENTDATA区分开来，
     * 以了解添加第一个元素时应该膨胀多少。
     * 用于空实例的空数组实例。如 ArrayList list = new ArrayList<>() list就等于{}.
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 存储ArrayList元素的数组缓冲区。
     * ArrayList的容量是这个数组缓冲区的长度。
     * 当添加第一个元素时，任何带有elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * 的空ArrayList都将扩展为DEFAULT_CAPACITY。
     */
    transient Object[] elementData;//非私有以简化嵌套类访问

    /**
     * ArrayList的大小
     */
    private int size;

    /**
     * 构造初始容量为initialCapacity的空列表
     * @param initialCapacity 初始容量
     */
    public MyArrayList(int initialCapacity){
        if(initialCapacity>0)
            this.elementData = new Object[initialCapacity];
        else if(initialCapacity == 0)
            this.elementData = EMPTY_ELEMENTDATA;
        else
            throw new IllegalArgumentException("Illegal Capacity: "+initialCapacity);

    }

    /**
     * 构造初始容量为10的空列表
     */
    public MyArrayList(){
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 构造包含指定集合的元素的列表，按集合的迭代器返回元素的顺序排列。
     * @param c 集合
     */
//    public MyArrayList(Collection<? extends E> c){
//        elementData = c.toArray();
//        if((size=elementData.length)!=0){
//            //c.toArray可能不会返回Object[]
//            if(elementData.getClass()!=Object.class)
//                elementData = Arrays.copyOf(elementData,size,Object[].class);
//        }
//        else
//            this.elementData = EMPTY_ELEMENTDATA;
//    }

    /**
     * 将ArrayList实例的容量调整为列表的当前大小。
     * 应用程序可以使用此操作最小化ArrayList实例的存储。
     */
    public void trimToSize(){
        modCount++;
        if(size<elementData.length)
            elementData = (size==0)?EMPTY_ELEMENTDATA
                    :Arrays.copyOf(elementData,size);
    }

    /**
     * 如果需要，增加这个ArrayList实例的容量，以确保它至少可以容纳由最小容量参数指定的元素数量。
     * 在ArryList数据长度和数据量过大的情况下初始化理想的ArryList容积的效率明显高于自动扩容
     * @param minCapacity 最小容量
     */
    public void ensureCapacity(int minCapacity){
        //判断需要扩容的数组是否为空实例（空数组）如果不为空，则变量等于0，为空则变量等于数组默认容量10
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)? 0: DEFAULT_CAPACITY;
        //如果需要扩容的量大于定义的变量，则进一步调用以下方法
        if(minCapacity > minExpand)
            ensureCapacityInternal(minCapacity);

    }

    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        //如果ArrayList是通过无参数构造器new出来的，则返回10和minCapacity中最大的
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        //否则直接返回所传的minCapacity.
        return minCapacity;
    }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private void ensureExplicitCapacity(int minCapacity) {
        //用来标记当前ArrayList集合操作变化的次数
        modCount++;

        // 如果大于0，说明elementData数组大小不够用，需要扩容
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    /**
     * 要分配的数组的最大大小。一些vm在数组中保留一些头信息。
     * 试图分配更大的数组可能会导致OutOfMemoryError:请求的数组大小超过VM限制
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        //新容量扩展为old+old/2
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        //如果扩展的新容量小于需要的最小容量，则直接将要扩的容量置为需要的最小容量
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        //如果扩展的容量大于MAX_ARRAY_SIZE（int的最大值-8.8是为对象头预留的位置），如果超出int最大值，
        // 则抛出异常，否则如果扩展的值大于int最大值-8，则返回int最大值，否则返回int的最大值-8
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        //最后把数据拷贝到新扩展数组中
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }


    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 如果此列表包含指定的元素，则返回true。
     * @param o 指定元素
     * @return 包含返回true，不包含返回false
     */
    public boolean contains(Object o){
        return indexOf(o) >= 0;
    }

    public int indexOf(Object o){
        if(o==null){
            for(int i=0;i<size;i++){
                if(elementData[i]==null)
                    return i;
            }
        }else{
            for(int i=0;i<size;i++){
                if(o.equals(elementData[i]))
                    return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o){
        if(o==null){
            for(int i=size-1; i>=0;i--)
                if(elementData[i]==null)
                    return i;
        }else{
            for(int i=size-1;i>=0;i--)
                if(elementData[i]==null)
                    return i;
        }
        return -1;
    }

    /**
     * 返回此ArrayList实例的浅副本。(元素本身不会被复制。)
     * @return ArrayList浅副本
     */
    public Object clone(){
        try {
            MyArrayList<?> v = (MyArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData,size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     *返回的数组将是“安全的”，因为这个列表不维护对它的引用。(换句话说，这个方法必须分配一个新的数组)。
     * 因此，调用者可以自由地修改返回的数组。
     * @return 返回一个数组，该数组按适当的顺序(从第一个元素到最后一个元素)包含列表中的所有元素。
     */
    public Object[] toArray(){
        return Arrays.copyOf(elementData,size);
    }


    /**
     * 返回一个数组，该数组包含列表中按正确顺序排列的所有元素(从第一个元素到最后一个元素);
     * 返回数组的运行时类型是指定数组的运行时类型。
     * 如果列表符合指定的数组，则返回其中的列表。
     * 否则，将使用指定数组的运行时类型和该列表的大小分配一个新数组。
     * @param a 数组
     * @param <T> 类型
     * @return 将list中的元素按照a数组形式返回
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a){
        if(a.length<size)
            return (T[]) Arrays.copyOf(elementData,size,a.getClass());
        System.arraycopy(elementData,0,a,0,size);
        if(a.length>size)
            a[size] = null;
        return a;
    }

    @SuppressWarnings("unchecked")
    E elementData(int index){
        return (E) elementData[index];
    }

    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    /**
     *
     * @param index 需要修改的索引
     * @param element 需要修改的值
     * @return 被修改前的值
     */
    public E set(int index, E element){
        rangeCheck(index);
        E oldValue = get(index);
        elementData[index] = element;
        return oldValue;
    }

    /**
     * 向列表中添加元素，添加元素前先扩容
     * @param e 要添加的值
     * @return 成功与否
     */
    public boolean add(E e){
        ensureCapacityInternal(size+1);
        elementData[size++] = e;
        return true;
    }

    /**
     * 向列表指定位置添加值
     * @param index 指定位置下标
     * @param element 指定值
     */
    public void add(int index, E element){
        rangeCheckForAdd(index);
        ensureCapacityInternal(size+1);
        //将原来的列表index之后的数据全部向后移动一位
        System.arraycopy(elementData, index, elementData, index+1, size-index);
        elementData[index] = element;
        size++;
    }


    /**
     * 删除列表中指定下标的值，如果指定的下标不是最后一位，则要把指定下标后面的值全部向前移动一位，然后size减一
     * @param index 指定下标
     * @return 被删除的值
     */
    public E remove(int index){
        rangeCheck(index);
        modCount++;
        E oldValue = elementData(index);
        int numMoved = size - index - 1;
        if(numMoved>0)
            System.arraycopy(elementData,index+1,elementData,index,numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    /**
     * 快速删除，没有返回值，不用下标检查
     * @param index 指定删除的索引位置
     */
    private void fastRemove(int index){
        modCount++;
        int numMoved = size - index - 1;
        if(numMoved>0)
            System.arraycopy(elementData,index+1,elementData,index,numMoved);
        elementData[--size] = null;
    }

    /**
     * 删除指定元素，只删除列表中第一个
     * @param o 指定的元素值
     * @return 删除成功与否
     */
    public boolean remove(Object o){
        if(o==null){
            for(int i=0;i<size;i++){
                if(elementData[i] == null) {
                    fastRemove(i);
                    return true;
                }
            }

        }else{
            for(int i = 0;i<size;i++){
                if(o.equals(elementData[i])){
                    fastRemove(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 清空列表中的所有元素
     */
    public void clear(){
        modCount++;
        for(int i=0;i<size;i++)
            elementData[i] = null;
        size = 0;
    }

    /**
     * 向列表中添加一个集合中的所有值
     * @param c 所给集合
     * @return 添加成功与否
     */
    public boolean addAll(Collection<? extends  E> c){
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size+numNew);
        System.arraycopy(a,0,elementData,size,numNew);
        size+=numNew;
        return numNew!=0;
    }

    /**
     * 向指定元素索引处添加一个集合中的所有值
     * @param index 指定元素下标
     * @param c 所给集合
     * @return 添加成功与否
     */
    public boolean addAll(int index, Collection<? extends  E> c){
        rangeCheckForAdd(index);
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size+numNew);
        int numMove = size - index;
        if(numMove>0)
            System.arraycopy(elementData,index,elementData,index+numNew,numMove);
        System.arraycopy(a,0,elementData,index,numNew);
        size+=numNew;
        return numNew !=0;
    }

    /**
     * 删除列表中指定范围内的所有元素
     * @param fromIndex 起始下标
     * @param toIndex 结束下标
     */
    protected void removeRange(int fromIndex, int toIndex){
        modCount++;
        int numMoved = size-toIndex;
        System.arraycopy(elementData,toIndex,elementData,fromIndex,numMoved);
        int newSize = size-(toIndex-fromIndex);
        for(int i=newSize;i<size;i++)
            elementData[i] = null;
        size =newSize;
    }

    /**
     * 批量删除列表中包含集合或不包含集合的元素
     * @param c 给定集合
     * @param complement 表示是包含该集合元素还是不包含该集合元素
     * @return 批量删除是否成功
     */
    private boolean batchRemove(Collection<?> c, boolean complement){
        final Object[] elementData = this.elementData;
        int r=0,w=0;
        boolean modified = false;
        try{
            for(;r<size;r++)
                if(c.contains(elementData[r]) == complement)
                    elementData[w++] = elementData[r];
        }finally {
            //如果c.contains()抛出异常r就会小于size，然后需要复制剩下的
            if(r!=size){
                System.arraycopy(elementData,r,elementData,w,size-r);
                w +=size - r;
            }
            if(w!=size){
                for(int i=w;i<size;i++)
                    elementData[i] = null;
                modCount+=size-w;
                size = w;
                modified = true;
            }
        }
        return modified;
    }

    /**
     * 只保留此列表中包含在指定集合中的元素。换句话说，从这个列表中删除指定集合中不包含的所有元素。
     * @param c 给定集合
     * @return 删除成功与否
     */
    public boolean retainAll(Collection<?> c){
        //检查指定的对象引用是否为空。该方法主要用于方法和构造函数中的参数验证
        Objects.requireNonNull(c);
        return batchRemove(c,true);
    }

    /**
     * 从该列表中删除指定集合中包含的所有元素。
     * @param c 指定集合
     * @return 删除成功与否
     */
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, false);
    }

    /**
     * 将ArrayList实例的状态保存到流中(即序列化它)。
     * 在new一个ArrayList对象时，默认是开辟一个长度为10的对象数组，如果只存入几个对象（不到默认的10个），如果采用默认序列化，则会将其余为null也序列化到文件中。
     * 数组内存储的的元素其实只是一个引用，单单序列化一个引用没有任何意义，反序列化后这些引用都无法在指向原来的对象。
     * 如果声明为transient类型，就能让虚拟机不会自行处理我们的这个数组，这才有了ArrayList的write/readObject方法。通过这种方式避免了浪费资源去存储没有的数据。
     * @param s 流
     * @throws java.io.IOException 流异常
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException{

        int expectedModCount = modCount;
        //将ArrayList中除了transient的其他数据序列化
        s.defaultWriteObject();
        //先把数组大小序列化
        s.writeInt(size);

        // 再按照顺序将数组中有值的元素一一序列化。
        for (int i=0; i<size; i++) {
            s.writeObject(elementData[i]);
        }
        //如果在序列化期间修改列表长度，则抛出异常
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }


    /**
     * 从流中重新构造ArrayList实例(即反序列化它)。
     */
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;

        // 反序列化除了transient的其他数据
        s.defaultReadObject();

        // 反序列化数组中的元素
        s.readInt(); // ignored

        if (size > 0) {
            // 类似clone()，根据大小而不是容量分配数组
            int capacity = calculateCapacity(elementData, size);
            SharedSecrets.getJavaOISAccess().checkArray(s, Object[].class, capacity);
            ensureCapacityInternal(size);

            Object[] a = elementData;
            // 按适当的顺序读取所有元素。
            for (int i=0; i<size; i++) {
                a[i] = s.readObject();
            }
        }
    }


    private class Itr implements Iterator<E> {
        int cursor;       // 要返回的下一个索引
        int lastRet = -1; // 返回的最后一个元素的索引;如果没有表示-1
        int expectedModCount = modCount;

        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            //检查有没有增删过列表
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = MyArrayList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((E) elementData[i++]);
            }

            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        /**
         * 获取迭代器前一个元素，获取完后cursor=cursor-1,lastRet = cursor-1
         * @return
         */
        @SuppressWarnings("unchecked")
        public E previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        /**
         * 将最近遍历过的数值设置为e
         * @param e 新的值
         */
        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        /**
         * 将e通过迭代器添加在列表中，添加过后cursor+1，lastRet=-1
         * @param e 添加的元素
         */
        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public List<E> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, size);
        return new SubList(this, 0, fromIndex, toIndex);
    }

    /**
     * 下标检查
     * @param fromIndex 开始下标
     * @param toIndex 结束下标
     * @param size 大小
     */
    static void subListRangeCheck(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > size)
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                    ") > toIndex(" + toIndex + ")");
    }

    private class SubList extends MyArrayList<E> implements RandomAccess {
        private final MyAbstractList<E> parent;
        private final int parentOffset;
        private final int offset;
        int size;

        SubList(MyAbstractList<E> parent,
                int offset, int fromIndex, int toIndex) {
            this.parent = parent;
            this.parentOffset = fromIndex;
            this.offset = offset + fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = MyArrayList.this.modCount;
        }

        public E set(int index, E e) {
            rangeCheck(index);
            checkForComodification();
            E oldValue = MyArrayList.this.elementData(offset + index);
            MyArrayList.this.elementData[offset + index] = e;
            return oldValue;
        }

        public E get(int index) {
            rangeCheck(index);
            checkForComodification();
            return MyArrayList.this.elementData(offset + index);
        }

        public int size() {
            checkForComodification();
            return this.size;
        }

        public void add(int index, E e) {
            rangeCheckForAdd(index);
            checkForComodification();
            parent.add(parentOffset + index, e);
            this.modCount = parent.modCount;
            this.size++;
        }

        public E remove(int index) {
            rangeCheck(index);
            checkForComodification();
            E result = parent.remove(parentOffset + index);
            this.modCount = parent.modCount;
            this.size--;
            return result;
        }

        protected void removeRange(int fromIndex, int toIndex) {
            checkForComodification();
            parent.removeRange(parentOffset + fromIndex,
                    parentOffset + toIndex);
            this.modCount = parent.modCount;
            this.size -= toIndex - fromIndex;
        }

        public boolean addAll(Collection<? extends E> c) {
            return addAll(this.size, c);
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            rangeCheckForAdd(index);
            int cSize = c.size();
            if (cSize==0)
                return false;

            checkForComodification();
            parent.addAll(parentOffset + index, c);
            this.modCount = parent.modCount;
            this.size += cSize;
            return true;
        }

        public Iterator<E> iterator() {
            return listIterator();
        }

        public ListIterator<E> listIterator(final int index) {
            checkForComodification();
            rangeCheckForAdd(index);
            final int offset = this.offset;

            return new ListIterator<E>() {
                int cursor = index;
                int lastRet = -1;
                int expectedModCount = MyArrayList.this.modCount;

                public boolean hasNext() {
                    return cursor != SubList.this.size;
                }

                @SuppressWarnings("unchecked")
                public E next() {
                    checkForComodification();
                    int i = cursor;
                    if (i >= SubList.this.size)
                        throw new NoSuchElementException();
                    Object[] elementData = MyArrayList.this.elementData;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i + 1;
                    return (E) elementData[offset + (lastRet = i)];
                }

                public boolean hasPrevious() {
                    return cursor != 0;
                }

                @SuppressWarnings("unchecked")
                public E previous() {
                    checkForComodification();
                    int i = cursor - 1;
                    if (i < 0)
                        throw new NoSuchElementException();
                    Object[] elementData = MyArrayList.this.elementData;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i;
                    return (E) elementData[offset + (lastRet = i)];
                }

                @SuppressWarnings("unchecked")
                public void forEachRemaining(Consumer<? super E> consumer) {
                    Objects.requireNonNull(consumer);
                    final int size = SubList.this.size;
                    int i = cursor;
                    if (i >= size) {
                        return;
                    }
                    final Object[] elementData = MyArrayList.this.elementData;
                    if (offset + i >= elementData.length) {
                        throw new ConcurrentModificationException();
                    }
                    while (i != size && modCount == expectedModCount) {
                        consumer.accept((E) elementData[offset + (i++)]);
                    }
                    // update once at end of iteration to reduce heap write traffic
                    lastRet = cursor = i;
                    checkForComodification();
                }

                public int nextIndex() {
                    return cursor;
                }

                public int previousIndex() {
                    return cursor - 1;
                }

                public void remove() {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();

                    try {
                        SubList.this.remove(lastRet);
                        cursor = lastRet;
                        lastRet = -1;
                        expectedModCount = MyArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                public void set(E e) {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();

                    try {
                        MyArrayList.this.set(offset + lastRet, e);
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                public void add(E e) {
                    checkForComodification();

                    try {
                        int i = cursor;
                        SubList.this.add(i, e);
                        cursor = i + 1;
                        lastRet = -1;
                        expectedModCount = MyArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                final void checkForComodification() {
                    if (expectedModCount != MyArrayList.this.modCount)
                        throw new ConcurrentModificationException();
                }
            };
        }

        public List<E> subList(int fromIndex, int toIndex) {
            subListRangeCheck(fromIndex, toIndex, size);
            return new MyArrayList.SubList(this, offset, fromIndex, toIndex);
        }

        private void rangeCheck(int index) {
            if (index < 0 || index >= this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private void rangeCheckForAdd(int index) {
            if (index < 0 || index > this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private String outOfBoundsMsg(int index) {
            return "Index: "+index+", Size: "+this.size;
        }

        private void checkForComodification() {
            if (MyArrayList.this.modCount != this.modCount)
                throw new ConcurrentModificationException();
        }

        public Spliterator<E> spliterator() {
            checkForComodification();
            return new ArrayListSpliterator<E>(MyArrayList.this, offset,
                    offset + this.size, this.modCount);
        }
    }
    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modCount;
        @SuppressWarnings("unchecked")
        final E[] elementData = (E[]) this.elementData;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            action.accept(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }


    @Override
    public Spliterator<E> spliterator() {
        return new ArrayListSpliterator<>(this, 0, -1, 0);
    }

    /** Index-based split-by-two, lazily initialized Spliterator */
    static final class ArrayListSpliterator<E> implements Spliterator<E> {

        /*
         * If ArrayLists were immutable, or structurally immutable (no
         * adds, removes, etc), we could implement their spliterators
         * with Arrays.spliterator. Instead we detect as much
         * interference during traversal as practical without
         * sacrificing much performance. We rely primarily on
         * modCounts. These are not guaranteed to detect concurrency
         * violations, and are sometimes overly conservative about
         * within-thread interference, but detect enough problems to
         * be worthwhile in practice. To carry this out, we (1) lazily
         * initialize fence and expectedModCount until the latest
         * point that we need to commit to the state we are checking
         * against; thus improving precision.  (This doesn't apply to
         * SubLists, that create spliterators with current non-lazy
         * values).  (2) We perform only a single
         * ConcurrentModificationException check at the end of forEach
         * (the most performance-sensitive method). When using forEach
         * (as opposed to iterators), we can normally only detect
         * interference after actions, not before. Further
         * CME-triggering checks apply to all other possible
         * violations of assumptions for example null or too-small
         * elementData array given its size(), that could only have
         * occurred due to interference.  This allows the inner loop
         * of forEach to run without any further checks, and
         * simplifies lambda-resolution. While this does entail a
         * number of checks, note that in the common case of
         * list.stream().forEach(a), no checks or other computation
         * occur anywhere other than inside forEach itself.  The other
         * less-often-used methods cannot take advantage of most of
         * these streamlinings.
         */

        private final MyArrayList<E> list;
        private int index; // current index, modified on advance/split
        private int fence; // -1 until used; then one past last index
        private int expectedModCount; // initialized when fence set

        /** Create new spliterator covering the given  range */
        ArrayListSpliterator(MyArrayList<E> list, int origin, int fence,
                             int expectedModCount) {
            this.list = list; // OK if null unless traversed
            this.index = origin;
            this.fence = fence;
            this.expectedModCount = expectedModCount;
        }

        private int getFence() { // initialize fence to size on first use
            int hi; // (a specialized variant appears in method forEach)
            MyArrayList<E> lst;
            if ((hi = fence) < 0) {
                if ((lst = list) == null)
                    hi = fence = 0;
                else {
                    expectedModCount = lst.modCount;
                    hi = fence = lst.size;
                }
            }
            return hi;
        }

        public ArrayListSpliterator<E> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid) ? null : // divide range in half unless too small
                    new ArrayListSpliterator<E>(list, lo, index = mid,
                            expectedModCount);
        }

        public boolean tryAdvance(Consumer<? super E> action) {
            if (action == null)
                throw new NullPointerException();
            int hi = getFence(), i = index;
            if (i < hi) {
                index = i + 1;
                @SuppressWarnings("unchecked") E e = (E)list.elementData[i];
                action.accept(e);
                if (list.modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                return true;
            }
            return false;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            int i, hi, mc; // hoist accesses and checks from loop
            MyArrayList<E> lst; Object[] a;
            if (action == null)
                throw new NullPointerException();
            if ((lst = list) != null && (a = lst.elementData) != null) {
                if ((hi = fence) < 0) {
                    mc = lst.modCount;
                    hi = lst.size;
                }
                else
                    mc = expectedModCount;
                if ((i = index) >= 0 && (index = hi) <= a.length) {
                    for (; i < hi; ++i) {
                        @SuppressWarnings("unchecked") E e = (E) a[i];
                        action.accept(e);
                    }
                    if (lst.modCount == mc)
                        return;
                }
            }
            throw new ConcurrentModificationException();
        }

        public long estimateSize() {
            return (long) (getFence() - index);
        }

        public int characteristics() {
            return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
        }
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        // figure out which elements are to be removed
        // any exception thrown from the filter predicate at this stage
        // will leave the collection unmodified
        int removeCount = 0;
        final BitSet removeSet = new BitSet(size);
        final int expectedModCount = modCount;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            @SuppressWarnings("unchecked")
            final E element = (E) elementData[i];
            if (filter.test(element)) {
                removeSet.set(i);
                removeCount++;
            }
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }

        // shift surviving elements left over the spaces left by removed elements
        final boolean anyToRemove = removeCount > 0;
        if (anyToRemove) {
            final int newSize = size - removeCount;
            for (int i=0, j=0; (i < size) && (j < newSize); i++, j++) {
                i = removeSet.nextClearBit(i);
                elementData[j] = elementData[i];
            }
            for (int k=newSize; k < size; k++) {
                elementData[k] = null;  // Let gc do its work
            }
            this.size = newSize;
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            modCount++;
        }

        return anyToRemove;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final int expectedModCount = modCount;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            elementData[i] = operator.apply((E) elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        final int expectedModCount = modCount;
        Arrays.sort((E[]) elementData, 0, size, c);
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }


}
