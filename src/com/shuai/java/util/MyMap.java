package com.shuai.java.util;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 将键映射到值的对象。地图不能包含重复的键;每个键最多可以映射到一个值。
 * 这个接口取代了Dictionary类，Dictionary类是一个完全抽象的类，而不是接口。
 * Map接口提供了三个集合视图，这些视图允许将映射的内容看作键集、值集或键值映射集。
 * 映射的顺序定义为映射集合视图上的迭代器返回其元素的顺序。一些映射实现，比如TreeMap类，对它们的顺序做出了特定的保证;而其他类，如HashMap类，则没有。
 * @param <K>
 * @param <V>
 */
public interface MyMap<K,V> {

    int size();

    boolean isEmpty();

    boolean containsKey(Object key);

    boolean containsValue(Object value);

    V get(Object key);

    V put(K key, V value);

    V remove(Object key);

    void putAll(Map<? extends K, ? extends V> m);

    void clear();

    MySet<K> keySet();

    Collection<V> value();

    MySet<MyMap.Entry<K, V>> entrySet();

    interface Entry<K, V>{

        K getKey();
        V getValue();
        V setValue(V value);
        boolean equals(Object o);
        int hashCode();


        /**
         * 返回一个按照Key值比较的比较器
         * @param <K> key
         * @param <V> Value
         * @return 比较器
         */
        public static <K extends Comparable<? super K>,V>Comparator<MyMap.Entry<K,V>>comparingByKey(){
            return (Comparator<MyMap.Entry<K,V>>&Serializable)
                    (c1,c2)->c1.getKey().compareTo(c2.getKey());
        }

        /**
         * 返回一个按照Value比较的比较器
         * @param <K> key
         * @param <V> Value
         * @return 比较器
         */
        public static <K,V extends Comparable<? super V>>Comparator<MyMap.Entry<K,V>>comparingByValue(){
            return (Comparator<MyMap.Entry<K,V>>&Serializable)
                    (c1,c2)->c1.getValue().compareTo(c2.getValue());
        }

        public static <K, V> Comparator<MyMap.Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<MyMap.Entry<K, V>> & Serializable)
                    (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
        }

        public static <K, V> Comparator<MyMap.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<MyMap.Entry<K, V>> & Serializable)
                    (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
        }
    }

    boolean equals(Object o);

    int hashCode();

    /**
     * 返回指定键映射到的值，如果此映射不包含键的映射，则返回defaultValue。
     * @param key 键
     * @param defaultValue 默认值
     * @return 指定键映射到的值，如果此映射不包含键的映射，则返回defaultValue。
     */
    default V getOrDefault(Object key, V defaultValue){
        V v;
        return (((v = get(key)) != null) || containsKey(key))
                ? v
                : defaultValue;
    }

    /**
     * 为映射中的每个条目执行给定的操作，直到处理完所有条目或操作引发异常为止。
     * 除非实现类另有指定，否则操作将按照条目集迭代的顺序执行(如果指定了迭代顺序)。该操作引发的异常将传递给调用者。
     * @param action 为每个条目执行的操作
     */
    default void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        for (MyMap.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        }
    }


    /**
     * 将每个条目的值替换为对该条目调用给定函数的结果，直到所有条目都被处理或函数抛出异常。函数抛出的异常将传递给调用者。
     * @param function 应用于每个条目的函数
     */
    default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function);
        for (MyMap.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }

            // ise thrown from function is not a cme.
            v = function.apply(k, v);

            try {
                entry.setValue(v);
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
        }
    }


    /**
     * 如果指定的键尚未与值关联(或映射为null)，则将其与给定值关联并返回null，否则将返回当前值。
     * @param key 键
     * @param value 值
     * @return 与指定键关联的前一个值，如果没有键的映射，则为空。(如果实现支持null值，null返回还可以指示以前将null与键关联的映射。)
     */
    default V putIfAbsent(K key, V value) {
        V v = get(key);
        if (v == null) {
            v = put(key, value);
        }

        return v;
    }


    /**
     * 仅当当前映射到指定值时，才删除指定键的项。
     * @param key 键
     * @param value 值
     * @return  如果值被删除，则为true
     */
    default boolean remove(Object key, Object value) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, value) ||
                (curValue == null && !containsKey(key))) {
            return false;
        }
        remove(key);
        return true;
    }

    default boolean replace(K key, V oldValue, V newValue) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, oldValue) ||
                (curValue == null && !containsKey(key))) {
            return false;
        }
        put(key, newValue);
        return true;
    }

    default V replace(K key, V value) {
        V curValue;
        if (((curValue = get(key)) != null) || containsKey(key)) {
            curValue = put(key, value);
        }
        return curValue;
    }

    /**
     * 如果指定的键尚未与值关联(或映射为null)，则尝试使用给定的映射函数计算其值，并将其输入到此映射中，除非为null。
     * 如果函数返回null，则不记录映射。如果函数本身抛出一个(未选中的)异常，则重新抛出异常，并且不记录映射。最常见的用法是构造一个新对象作为初始映射值或记忆结果
     * @param key 键
     * @param mappingFunction 计算值的函数
     * @return 与指定键关联的当前(现有的或已计算的)值，如果计算值为空，则为空
     */
    default V computeIfAbsent(K key,
                              Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                return newValue;
            }
        }

        return v;
    }


    /**
     * 如果指定键的值为present且非null，则尝试计算给定键及其当前映射值的新映射。
     * 如果函数返回null，则删除映射。如果函数本身抛出一个(未选中的)异常，则重新抛出异常，当前映射保持不变。
     * @param key 键
     * @param remappingFunction 计算值的函数
     * @return 与指定键关联的新值，如果没有，则为空
     */
    default V computeIfPresent(K key,
                               BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue;
        if ((oldValue = get(key)) != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                put(key, newValue);
                return newValue;
            } else {
                remove(key);
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * 尝试为指定的键及其当前映射值计算映射(如果没有当前映射，则为null)。
     * 如果函数返回null，映射将被删除(如果最初没有映射，映射将保持为空)。如果函数本身抛出一个(未选中的)异常，则重新抛出异常，当前映射保持不变。
     * @param key 键
     * @param remappingFunction 计算值的函数
     * @return 与指定键关联的新值，如果没有，则为空
     */
    default V compute(K key,
                      BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue = get(key);

        V newValue = remappingFunction.apply(key, oldValue);
        if (newValue == null) {
            // delete mapping
            if (oldValue != null || containsKey(key)) {
                // something to remove
                remove(key);
                return null;
            } else {
                // nothing to do. Leave things as they were.
                return null;
            }
        } else {
            // add or replace old mapping
            put(key, newValue);
            return newValue;
        }
    }


    /**
     * 如果指定的键尚未与值关联或与null关联，则将其与给定的非null值关联。否则，将关联值替换为给定映射函数的结果，如果结果为空，则删除关联值。此方法可用于组合键的多个映射值。
     * @param key 键
     * @param value 值
     * @param remappingFunction  计算值的函数
     * @return
     */
    default V merge(K key, V value,
                    BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Objects.requireNonNull(value);
        V oldValue = get(key);
        V newValue = (oldValue == null) ? value :
                remappingFunction.apply(oldValue, value);
        if(newValue == null) {
            remove(key);
        } else {
            put(key, newValue);
        }
        return newValue;
    }


}
