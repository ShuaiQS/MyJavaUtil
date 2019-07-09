package com.shuai.java.leetcode;

/**
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 */
public class ConvertSortedArrayToBinarySearchTree_108 {
    public static void main(String[] args){
        System.out.println(new ConvertSortedArrayToBinarySearchTree_108().sortedArrayToBST(new int[]{1,2,3,4,5,7,8,9}));
    }
    private TreeNode sortedArrayToBST(int[] nums){
        return sortedArrayToBST(nums,0,nums.length);
    }

    private TreeNode sortedArrayToBST(int[] nums, int left, int right){
        if(left>right)
            return null;
        int mid = (right-left)/2+left;
        TreeNode root = new TreeNode(mid);
        root.left = sortedArrayToBST(nums,left,mid-1);
        root.right = sortedArrayToBST(nums,mid+1,right);
        return root;
    }
}
