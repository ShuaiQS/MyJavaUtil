package com.shuai.java.dataStructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 树的层级遍历(广度优先算法）
 * 树的层级遍历最常用的方法就是广度优先遍历，使用了队列的FIFO的方式，将所有暂未访问的节点存入一个队列，一次遍历。伪代码如下：
 * queue = [root]       // 新建一个队列，并将根节点放入队列
 * while queue.length!=0
 *      item = queue.shift      //弹出队列的头部元素
 *      do_something(item)      //操作该节点：比如存入一个数组或者打印
 *      if item.left
 *          queue.push(item.left)       //将左节点存入队列
 *      if item.right
 *          queue.push(item.right)      //将右节点存入队列
 *但是层次遍历的难点在于层次的比较，也就是说，我们需要对不同层次的节点做隔离
 *
 */
public class TreeLevelOrder {
    /**
     * 数组长度做隔离
     * 思路：获取当前的队列的长度len，一次只遍历len个节点，后续加入的元素在下一次循环遍历
     * @param root 根节点
     * @return 按各层输出
     */
    private List<List<Integer>> getLevel(TreeNode root){
        List<List<Integer>> lists =new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        if(root==null)
            return lists;
        int len;
        TreeNode temp;
        queue.addFirst(root);

        while (!queue.isEmpty()){
            len = queue.size();
            List<Integer> list = new ArrayList<>();
            while(len>0){
                //弹出队首元素
                temp = queue.removeFirst();
                list.add(temp.val);
                if(temp.left!=null)
                    queue.addLast(temp.left);
                if(temp.right!=null)
                    queue.addLast(temp.right);
                len--;

            }

            lists.add(list);
        }
        return lists;

    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left=new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(new TreeLevelOrder().getLevel(root));

    }
}

class TreeNode {
      int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
}