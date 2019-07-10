package com.shuai.java.leetcode;

/**
 * 给定一个整数数组 nums ，找出一个序列中乘积最大的连续子序列（该序列至少包含一个数）。
 */
public class MaximumProductSubarray_152 {
    /**
     *
     * @param nums 整数数组
     * @return 乘积最大的连续子序列
     * 思路：遍历数组计算当前最大值，不断更新令maxTemp为当前最大值，则当前最大值为maxTemp = Math.max(maxTemp*num,num);
     *      由于存在负数，就会导致最小值变最大值，最大值变最小值，所以还得维护当前最小值minTemp = Math.min(minTemp*num,num);
     *      当出现负数时，利用temp当中间变量交换最大值与最小值，再进行下一步计算
     *      时间复杂度为O(n)
     */
    private int maxProduct(int[] nums){
        int max = Integer.MIN_VALUE, len = nums.length;
        if(len==1)
            return nums[0];
        int maxTemp=1,minTemp=1,temp;
        for(int num:nums){
            if(num<0){
                temp = maxTemp;
                maxTemp = minTemp;
                minTemp = temp;
            }

            maxTemp = Math.max(maxTemp*num,num);
            minTemp = Math.min(minTemp*num,num);
            max = Math.max(maxTemp,max);
        }
        return max;

    }
}
