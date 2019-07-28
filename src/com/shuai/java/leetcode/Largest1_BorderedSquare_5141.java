package com.shuai.java.leetcode;

/**
 * 给你一个由若干 0 和 1 组成的二维网格 grid，请你找出边界全部由 1 组成的最大 正方形 子网格，
 * 并返回该子网格中的元素数量。如果不存在，则返回 0。
 */
public class Largest1_BorderedSquare_5141 {
    /**
     * 思路，用dp[i][j][0]表示从上到下以grid[i][j]结尾的连续一的个数，dp[i][j][1]表示从左到右以grid[i][j]结尾的连续一的个数
     * 先取dp[i][j][0]和dp[i][j][1]的最小值，先比较min是否比已经找出的正方形的边长大，
     * 如果比找出来的大，就以min为边长检查正方形的上边长和左边长是否也满足有连续min个一，
     * 如果有将min*min赋给max,若是没有，则将min减一，再判断，直到min*min<max
     * @param grid 二维网格
     * @return 元素数量
     */
    public int largest1BorderedSquare(int[][] grid) {
        int x_len=grid.length;
        if(x_len==0)
            return 0;
        int y_len=grid[0].length,left,up,max=0,min,temp;
        //dp[i][j][0]表示从上到下以grid[i][j]结尾的连续一的个数
        //dp[i][j][1]表示从左到右以grid[i][j]结尾的连续一的个数
        int[][][] dp= new int[x_len][y_len][2];

        for(int i=0;i<x_len;i++){
            for(int j=0;j<y_len;j++){
                if(i==0)
                    up=0;
                else
                    up=dp[i-1][j][0];
                if(j==0)
                    left=0;
                else
                    left=dp[i][j-1][1];
                if(grid[i][j]==1){
                    dp[i][j][0]=up+1;
                    dp[i][j][1]=left+1;
                }
                //找出最小边长
                min=Math.min(dp[i][j][0],dp[i][j][1]);
                //如果找出的最小边长大于目前为止找到的最大边长
                while(min*min>max){
                    if(dp[i-min-1][j][1]>=min&&dp[i][j-min-1][0]>=min){
                        temp=min*min;
                        max=Math.max(max,temp);
                        break;
                    }else
                        min--;

                }
            }
        }

        return max;
    }
}
