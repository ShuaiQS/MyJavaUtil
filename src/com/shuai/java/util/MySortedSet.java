package com.shuai.java.util;



/**
 * 进一步对其元素提供总体排序的集合。元素使用它们的自然顺序进行排序，或者由比较器(通常在排序集创建时提供)进行排序。集合的迭代器将按升序遍历集合元素。还提供了几个额外的操作来利用排序。(此接口是SortedMap的集合模拟。)
 * 插入排序集的所有元素必须实现可比接口(或被指定的比较器接受)。此外,所有这些元素都必须相互可比:e1.compareTo (e2)(或comparator.compare (e1, e2))不能抛出一个ClassCastException任何元素e1和e2在排序集。试图违反这一限制将导致出错的方法或构造函数调用抛出一个ClassCastException。
 *
 */
public interface MySortedSet<E> extends MySet<E> {

    /**
     * 返回用于对该集合中的元素排序的比较器，如果该集合使用其元素的自然顺序，则返回null。
     * @return 比较器
     */
    Comparable<? super E> comparator();

    /**
     * 返回该集合中元素范围从fromElement(包含)到toElement(排除)的部分的视图。(如果fromElement和toElement相等，则返回的集合为空。)
     * 返回的集合由这个集合支持，因此返回集合中的更改将反映在这个集合中，反之亦然。返回的集合支持此集合支持的所有可选集合操作。
     * 返回的集合将在试图插入超出其范围的元素时抛出IllegalArgumentException。
     * @param fromElement 返回集的低端端点(包括)
     * @param toElement 返回集的高端点(不包括)
     * @return 集合中元素范围从fromElement(包含)到toElement(排除)的部分的视图
     */
    MySortedSet<E> subSet(E fromElement, E toElement);


    /**
     * 返回该集合中元素大于或等于fromElement的部分的视图。返回的集合由这个集合支持，因此返回集合中的更改将反映在这个集合中，反之亦然。
     * 返回的集合支持此集合支持的所有可选集合操作。返回的集合将在试图插入超出其范围的元素时抛出IllegalArgumentException。
     * @param fromElement 返回集的低端端点(包括)
     * @return 集合中元素大于或等于fromElement的部分的视图
     */
    MySortedSet<E> tailSet(E fromElement);

    /**
     * 返回此集合中当前的第一个(最低的)元素。
     * @return 返回此集合中当前的第一个(最低的)元素。
     */
    E first();

    /**
     * 返回此集合中当前的最后(最高)元素。
     * @return 返回此集合中当前的最后(最高)元素。
     */
    E last();

//    default Spliterator<E> spliterator() {
//        return new Spliterators.IteratorSpliterator<E>(
//                this, Spliterator.DISTINCT | Spliterator.SORTED | Spliterator.ORDERED) {
//            @Override
//            public Comparator<? super E> getComparator() {
//                return MySortedSet.this.comparator();
//            }
//        };
//    }

}
