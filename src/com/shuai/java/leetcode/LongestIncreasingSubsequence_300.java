package com.shuai.java.leetcode;

import java.util.Arrays;

/**
 * 给定一个无序整数数组，找到其中最长上升子序列的长度
 */
public class LongestIncreasingSubsequence_300 {

    /**
     * 暴力递归算法
     * @param nums 数组
     * @return 最长上升子序列的长度
     * 思路：
     * 1.当前元素大于包含在 lis 中的前一个元素。在这种情况下，我们可以在 lis 中包含当前元素。因此，我们通过将其包含在内，得出了 lis 的长度。
     * 此外，我们还通过不在 lis 中包含当前元素来找出 lis 的长度。因此，当前函数调用返回的值是两个长度中的最大值。
     * 2.当前元素小于包含在 lis 中的前一个元素。在这种情况下，我们不能在 lis 中包含当前元素。
     * 因此，我们只通过不在 lis 中包含当前元素（由当前函数调用返回）来确定 lis 的长度。
     */
    private int lengthOfLIS1(int[] nums){
        return lengthOfLIS1(nums,0,Integer.MIN_VALUE);
    }

    private int lengthOfLIS1(int[] nums, int index, int pre){
        if(index == nums.length)
            return 0;
        //如果当前值大于前一个值，则取，如果小于等于前一个值，则不取，可以将它设置为0；
        int select = pre<nums[index]?(1+lengthOfLIS1(nums,index+1,nums[index])):0;
        int noSelect = lengthOfLIS1(nums,index+1,pre);
        return Math.max(select,noSelect);
    }

    /**
     * 带记忆的递归算法
     * @param nums 数组
     * @return 最长上升子序列
     * 思路：将存在上升序列中的前一项的下标和当前值的下标的值的组合存入二维数组中，在递归过程中若已经计算过直接返回，要是没算过，就算出来存起来。
     *
     */
    private int lengthOfLIS2(int[] nums){
        int[][] memo = new int[nums.length+1][nums.length];
        for(int[] ints:memo)
            Arrays.fill(ints,-1);
        return lengthOfLIS2(nums,0,-1,memo);
    }
    private int lengthOfLIS2(int[] nums, int index, int preIndex, int[][] memo){
        if(index == nums.length)
            return 0;
        if(memo[preIndex+1][index]>=0)
            return memo[preIndex+1][index];
        int select = preIndex<0||nums[index]>nums[preIndex]?(1+lengthOfLIS2(nums,index+1,index,memo)):0;
        int noSelect = lengthOfLIS2(nums, index+1,preIndex,memo);
        memo[preIndex+1][index]=Math.max(select,noSelect);
        return memo[preIndex+1][index];
    }

    /**
     * 动态规划算法
     * @param nums 数组
     * @return 最长上升子序列
     * 思路：用dp[i]来表示以下标i结尾的子序列中的最长公共子序列的长度，
     */
    private int lengthOfLIS3(int[] nums){
        int len = nums.length;
        if(len==0)
            return 0;
        int[] dp = new int[len];
        Arrays.fill(dp,1);
        int maxAns = 1;
        for(int i=0;i<len;i++){
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
            maxAns = Math.max(maxAns,dp[i]);
        }
        return maxAns;
    }



    public static void main(String[] args) {
        LongestIncreasingSubsequence_300 lis = new LongestIncreasingSubsequence_300();
        int[] sequence = {10,9,2,5,3,7,101,18};
        System.out.println(lis.lengthOfLIS1(sequence));
        System.out.println(lis.lengthOfLIS2(sequence));
        System.out.println(lis.lengthOfLIS3(sequence));
    }
}
