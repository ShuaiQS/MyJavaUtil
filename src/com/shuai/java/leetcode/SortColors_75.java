package com.shuai.java.leetcode;

/**
 * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * 用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 */
public class SortColors_75 {
    public static void main(String[] args){
        int[] a = new int[]{0,1,2,0,2,1,0,2,1};
        new SortColors_75().sortColors(a);
        for(int i:a){
            System.out.print(i+" ");
        }

    }


    /**
     * 思路：定义三个指针，0界限指针，2界限指针，还有遍历指针，如果遍历的当前值是0，则与0界限指针的值交换，
     * 并将0的界限指针向后移动一位，如果遍历的当前值是2，则与2的界限值交换，并把2的界限指针向前移动一位。
     * @param nums 需要排序的数组
     */
    private void sortColors(int[] nums){
        int zeroLine = 0,current=0,twoLine = nums.length-1;
        while(current<=twoLine){
            if(current<zeroLine||nums[current]==1){
                current++;
            }
            else if(nums[current]==2){
                swap(nums,current,twoLine--);
            }else if(nums[current]==0){
                swap(nums,current,zeroLine++);
            }
        }
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;

    }
}
