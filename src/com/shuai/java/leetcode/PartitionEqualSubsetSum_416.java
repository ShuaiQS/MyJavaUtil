package com.shuai.java.leetcode;

/**
 * 给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 */
public class PartitionEqualSubsetSum_416 {

    private boolean canPartition(int[] nums){
        if(nums.length<=1)
            return false;
        int sum=0;
        for(int i:nums){
            sum+=i;
        }

        if((sum&1)==1)
            return false;
        else{
            return canPartition(nums,sum>>1,nums.length-1);
        }

    }

    //递归回溯
    private boolean canPartition(int[] nums,int target,int index){

        for(int i=index;i>=0;i--){
            if(nums[i] == target)
                return true;
            if(nums[i]>target)
                return false;
            if(canPartition(nums,target-nums[i],i-1))
                return true;
        }
        return false;

    }

    //动态规划
    private boolean canPartition(int[] nums, int target){
        int len = nums.length;
        //dp[i][j]表示从0到i中取一些数，只能取一次，如果这些数的和为j,则dp[i][j]=true,否则为false
        boolean[][] dp = new boolean[len][target+1];
        for(int i=1;i<target+1;i++){
            if(nums[0]==i)
                dp[0][i]=true;
        }

        for(int i=1;i<len;i++){
            for(int j=0;j<=target;j++){
                if(j-nums[i]>=0){
                    dp[i][j] = dp[i-1][j]||dp[i-1][j-nums[i]];
                }
            }
        }
        return dp[len-1][target];
    }


    public static void main(String[] args) {
        int[] a = { 2,2,3, 5};
        System.out.println(new PartitionEqualSubsetSum_416().canPartition(a));
    }

}
