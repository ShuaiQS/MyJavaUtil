package com.shuai.java.dataStructure;

public class RedBlackTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private class Node{
        K key;
        V value;
        Node left, right;
        boolean color;

        public Node(K key,V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            color = RED;
        }
    }
    private Node root;
    private int size;

    public RedBlackTree(){
        root = null;
        size = 0;
    }
}
