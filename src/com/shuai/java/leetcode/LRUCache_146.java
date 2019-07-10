package com.shuai.java.leetcode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。
 * 获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
 * 写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，它应该在写入新数据之前删除最近最少使用的数据值，从而为新的数据值留出空间。
 *
 *
 *
 *
 * LinkedHashMap的底层就是hashMap+双向链表，当AccessOrder为true时，可以按照访问顺序用链表把各个元素串起来，
 * 调用一次get(key)，则将链表中的元素删掉，再插入到链表尾部。
 *
 * 直接继承现成的数据结构LinkedHashMap，重写removeEldestEntry方法为当map中的元素的个数大于规定的容量时，返回true。
 * 在LinkedHashMap底层put完一个元素后会执行removeEldestEntry方法，若为true，则删除链表的头结点，即最近最少使用的LRU。
 */
public class LRUCache_146  extends LinkedHashMap<Integer,Integer> {
    private int capacity;

    public LRUCache_146(int capacity){
        super(capacity,0.75f,true);
        this.capacity = capacity;
    }

    public int get(int key){
        return super.getOrDefault(key,-1);
    }

    public void put(int key, int value){
        super.put(key,value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer,Integer> eldest){
        return size()>capacity;
    }
}
