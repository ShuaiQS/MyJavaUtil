package com.shuai.java.leetcode;

import java.util.HashMap;

/**
 * 买卖股票最佳时机
 * 我们用dp[i][k][s]来表示第i天，在允许最大交易次数k的,状态是s(0或1表示当前没持有股票和当前持有股票)获得的最大利润。
 * 用自然语言描述出每一个状态的含义，比如dp[3][2][1]表示：今天是第三天，我先在手上持有股票，至今还可以交易两次。
 * 我们最终要求的答案是dp[n-1][k][0],表示最后一天，我卖出手上的股票后的最大利润
 * 状态转移方程：dp[i][k][0] = max(dp[i-1][k][0],dp[i-1][k][1]+prices[i])表示，第i天没有持有股票得到的最大利润是
 * 前一天我没有持股票的最大利润和前天持了股票但今天卖了股票的最大利润中的最大值。同理，第i天持有股票达到的最大利润是
 * dp[i][k][1] = max(dp[i-1][k][1],dp[i-1][k-1][0]-prices[i])
 */
public class BestTimeToBuyAndSellStock_121_122_123_188_309_714 {

    /**
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
     * @param prices 第 i 个元素是一支给定股票第 i 天的价格
     * @return 最大利润
     *思路：套用上面的状态转移方程:
     * dp[i][k][0] = max(dp[i-1][k][0],dp[i-1][k][1]+prices[i])
     * dp[i][k][1] = max(dp[i-1][k][1],dp[i-1][k-1][0]-prices[i])
     * 其中k=1不变，锁状态转移方程组可以简化为：
     * dp[i][0] = max(dp[i-1][0],dp[i-1][1]+prices[i]);
     * dp[i][1] = max(dp[i-1][1],-prices[i])
     */
    private static int maxProfit1_0(int[] prices) {
        int len = prices.length;
        int[][] dp = new int[len][2];
        for(int i=0;i<len;i++){
            if(i==0){
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
            dp[i][1] = Math.max(dp[i-1][1],-prices[i]);
        }
        return dp[len-1][0];
    }

    /**
     * 方法maxProfit1_0(int[] prices)的改进版,空间复杂度由O(n)变为O(1);
     * @param prices 第 i 个元素是一支给定股票第 i 天的价格
     * @return 最大利润
     *思路：
     */
    private static int maxProfit1_1(int[] prices) {
        int dp_i_0 = 0,dp_i_1 = Integer.MIN_VALUE;
        for(int price:prices){

            //dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
            dp_i_0 = Math.max(dp_i_0,dp_i_1+price);
            //dp[i][1] = Math.max(dp[i-1][1],-prices[i]);
            dp_i_1 = Math.max(dp_i_1,-price);
        }
        return dp_i_0;
    }


    /**
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     * @param prices 第 i 个元素是一支给定股票第 i 天的价格
     * @return 返回最大利润
     *思路：套用上面的状态转移方程:
     * dp[i][k][0] = max(dp[i-1][k][0],dp[i-1][k][1]+prices[i])
     * dp[i][k][1] = max(dp[i-1][k][1],dp[i-1][k-1][0]-prices[i])
     * 其中k为多次，所以dp[i-1][k-1][0] = dp[i-1][k][0];因为k已经不会改变了，因此上述式子可以改写为
     * dp[i][0] = max(dp[i-1][0],dp[i-1][1]+prices[i])
     * dp[i][1] = max(dp[i-1][1],dp[i-1][0]-prices[i])
     * 时间复杂度O(n),空间复杂度O(n)
     *
     */
    private static int maxProfit2_0(int[] prices){
        int len = prices.length;
        int dp[][] = new int[len][2];
        for(int i=0;i<len;i++){
            if(i==0) {
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0]-prices[i]);
        }

        return dp[len-1][0];
    }

    /**
     * 空间复杂度由O(n)变为O(1)
     * @param prices 第 i 个元素是一支给定股票第 i 天的价格
     * @return 最大利润
     */
    private static int maxProfit2_1(int[] prices){
        int dp_i_0=0,dp_i_1 = Integer.MIN_VALUE;
        for(int price:prices){
            int temp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0,dp_i_1+price);
            dp_i_1 = Math.max(dp_i_1,temp-price);
        }
        return dp_i_0;
    }

    /**
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
     * @param prices 第 i 个元素是一支给定的股票在第 i 天的价格
     * @return 最大利润
     *思路：套用上面的状态转移方程:
     * dp[i][k][0] = max(dp[i-1][k][0],dp[i-1][k][1]+prices[i])
     * dp[i][k][1] = max(dp[i-1][k][1],dp[i-1][k-1][0]-prices[i])
     */
    private static int maxProfit3_0(int[] prices){
        int max_k = 2,len = prices.length;
        int[][][] dp = new int[len][max_k+1][2];
        for(int i=0;i<len;i++){
            for(int k=max_k;k>=1;k--){
                if(i==0){
                    dp[i][k][0] = 0;
                    dp[i][k][1] = -prices[i];
                    continue;
                }
                dp[i][k][0] = Math.max(dp[i-1][k][0],dp[i-1][k][1]+prices[i]);
                dp[i][k][1] = Math.max(dp[i-1][k][1],dp[i-1][k-1][0]-prices[i]);
            }
        }
        return dp[len-1][max_k][0];
    }

    /**
     * @param prices 第 i 个元素是一支给定的股票在第 i 天的价格
     * @return 最大利润
     * 思路： k 取值范围比较小，所以可以不用 for 循环，直接把 k = 1 和 2 的情况手动列举出来也可以
     * dp[i][2][0] = max(dp[i-1][2][0], dp[i-1][2][1] + prices[i])
     * dp[i][2][1] = max(dp[i-1][2][1], dp[i-1][1][0] - prices[i])
     * dp[i][1][0] = max(dp[i-1][1][0], dp[i-1][1][1] + prices[i])
     * dp[i][1][1] = max(dp[i-1][1][1], -prices[i])
     */
    private static int maxProfit3_1(int[] prices){
        int dp_i10 = 0,dp_i20 = 0,dp_i11 = Integer.MIN_VALUE,dp_i21 = Integer.MIN_VALUE;
        for(int price:prices){
            dp_i20 = Math.max(dp_i20,dp_i21+price);
            dp_i21 = Math.max(dp_i21,dp_i10-price);
            dp_i10 = Math.max(dp_i10,dp_i11+price);
            dp_i11 = Math.max(dp_i11,-price);
        }
        return dp_i20;

    }

    /**
     * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格.设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     * @return 最大利润
     * 思路：套用上面的状态转移方程组
     * dp[i][k][0] = max(dp[i-1][k][0],dp[i-1][k][1]+prices[i])
     * dp[i][k][1] = max(dp[i-1][k][1],dp[i-1][k-1][0]-prices[i])
     * 因为可以尽可能的完成更多的交易，所以dp[i-1][k-1][0] = dp[i-1][k][0];而且每次交易完之后都要等一天，所以状态转移方程变为：
     * dp[i][0] = max(dp[i-1][0],dp[i-1][1]+prices[i];
     * dp[i][1] = max(dp[i-1][1],dp[i-2][0]-prices[i];
     */
    private static int maxProfit309_0(int[] prices){
        int dp_i0 = 0, dp_i1 = Integer.MIN_VALUE,dp_i_2=0,temp;
        for(int price:prices){
            temp = dp_i0;
            dp_i0 = Math.max(dp_i0,dp_i1+price);
            dp_i1 = Math.max(dp_i1,dp_i_2-price);
            dp_i_2 = temp;
        }
        return dp_i0;
    }
    public static void main(String[] args){
        System.out.println(maxProfit1_0(new int[]{7,1,5,3,6,4}));
        System.out.println(maxProfit1_1(new int[]{7,1,5,3,6,4}));
        System.out.println(maxProfit2_0(new int[]{7,1,5,3,6,4}));
        System.out.println(maxProfit2_1(new int[]{7,1,5,3,6,4}));
        System.out.println(maxProfit3_1(new int[]{1,2,3,4,5}));
        System.out.println(maxProfit309_0(new int[]{1,2,3,0,2}));
        HashMap<Character,Integer> map = new HashMap<>();

    }
}
