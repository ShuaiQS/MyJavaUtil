package com.shuai.java.leetcode;

/**
 * 在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。
 * 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。
 * 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。
 */
public class HouseRobber3_337 {

    public static void main(String[] args){
        int a=new HouseRobber3_337().rob(new TreeNode(2));
        System.out.println(a);
    }

    private int rob(TreeNode root){
        int result[] = doRob(root);
        return Math.max(result[0],result[1]);
    }

    /**
     * result[0]表示不包括根节点的最大值，result[1]表示包括带根节点的最大值
     * @param root 树的根节点
     * @return 返回一个数组，里面存着带根节点的和与不带根节点的和
     */
    private int[] doRob(TreeNode root){
        int[] result = new int[2];
        if(root == null)
            return result;
        int[] left = doRob(root.left);
        int[] right = doRob(root.right);

        //不包括根节点的最大值等于该节点的左子树的最大值与右子树的最大值之和
        result[0] = Math.max(left[0],left[1])+Math.max(right[0],right[1]);
        //包括根节点的最大值等于左子树中不包含根节点的最大值加右子树中不包含根节点的最大值加根的值
        result[1] = left[0]+right[0]+root.val;

        return result;
    }
}


class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
}
