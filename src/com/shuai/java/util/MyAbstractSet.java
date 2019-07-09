package com.shuai.java.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * 该类提供Set接口的基本实现，以最小化实现该接口所需的工作。
 * 通过扩展这个类实现一组的过程是相同的,实现通过扩展AbstractCollection集合,除了学所有子类的方法和构造函数必须遵守附加设置界面所施加的约束(例如,添加方法必须不允许添加一组对象)的多个实例。
 * 注意，这个类不会覆盖AbstractCollection类中的任何实现。它只添加了equals和hashCode的实现。
 * @param <E>
 */
public abstract class MyAbstractSet<E> extends AbstractCollection<E> implements MySet<E> {
    protected MyAbstractSet(){

    }

    public boolean equals(Object o){
        if( o==this)
            return true;
        if(!(o instanceof MySet))
            return false;
        Collection<?> c = (Collection<?>) o;
        if(c.size()!=size())
            return false;
        try{
            return containsAll(c);
        }catch (ClassCastException unused){
            return false;
        }catch (NullPointerException unused){
            return false;
        }
    }

    public int hashCode(){
        int h = 0;
        Iterator<E> iterator = iterator();
        while(iterator.hasNext()){
            E obj = iterator.next();
            if(obj!=null)
                h+=obj.hashCode();
        }
        return h;
    }

    /**
     * 从该集合中移除指定集合中包含的所有元素(可选操作)。
     * 如果指定的集合也是一个集合，则此操作将有效地修改该集合，使其值为两个集合的非对称集差
     * 这个实现通过调用size方法来确定这个集合和指定的集合中哪个更小。如果这个集合的元素更少，那么实现将遍历这个集合，依次检查迭代器返回的每个元素，看看它是否包含在指定的集合中。如果它是这样包含的，则使用迭代器的remove方法从这个集合中删除它。
     * 如果指定集合的元素更少，则实现将遍历指定集合，使用这个集合从这个集合中删除迭代器返回的每个元素
     * @param c 集合
     * @return 是否删除成功
     */
    public boolean removeAll(Collection<?> c){
        Objects.requireNonNull(c);
        boolean modified = false;
        if(size()>c.size()){
            for(Iterator<?> iterator = c.iterator();iterator.hasNext();)
                modified |= remove(iterator.next());
        }else{
            for(Iterator<?> iterator = iterator();iterator.hasNext();)
                if(c.contains(iterator.next())){
                    iterator.remove();
                    modified = true;
                }
        }
        return modified;
    }



}
