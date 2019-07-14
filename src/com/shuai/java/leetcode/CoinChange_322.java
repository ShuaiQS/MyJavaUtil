package com.shuai.java.leetcode;

import java.util.Arrays;

/**
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
 */
public class CoinChange_322 {

    /**
     * 暴力递归方法
     * @param coins 硬币列表
     * @param amount 总金额
     * @return 最少硬币数组成总金额
     */
    private int coinChange1(int[] coins, int amount){
        if(amount == 0)
            return 0;
        int result = Integer.MAX_VALUE;
        for(int coin:coins){
            if(amount-coin<0)
                continue;
            int subProblem = coinChange1(coins,amount-coin);
            if(subProblem==-1)
                continue;
            result = Math.min(result,subProblem+1);
        }
        return result==Integer.MAX_VALUE? -1:result;
    }


    /**
     * 记忆搜索
     * @param coins 硬币列表
     * @param amount 总金额
     * @return 最少硬币数组成总金额
     */
    private int coinChange2(int[] coins, int amount){
        int[] store = new int[amount+1];
        return coinChange2(coins,amount,store);
    }
    private int coinChange2(int[] coins, int amount, int[] store){
        if(amount == 0)
            return 0;
        if(store[amount]!=0)
            return store[amount];
        int result = Integer.MAX_VALUE;
        for(int coin: coins){
            if(amount-coin<0)
                continue;
            int subProblem = coinChange2(coins,amount-coin,store);
            if(subProblem==-1)
                continue;
            result = Math.min(result,subProblem+1);
        }
        store[amount] = result==Integer.MAX_VALUE? -1:result;
        return store[amount];
    }

    /**
     * 动态规划法
     * @param coins 硬币列表
     * @param amount 总金额
     * @return 最少硬币数组成总金额
     */
    private int coinChange3(int[] coins,int amount){
        int len = coins.length;
        if(len==0)
            return -1;
        //dp[i]表示amount为i时的最少硬币数量
        int[] dp = new int[amount+1];

        Arrays.fill(dp,amount+1);
        dp[0] = 0;
        for(int i=1;i<=amount;i++){
            for(int coin:coins)
                if(i-coin>=0)
                    dp[i] = Math.min(dp[i],dp[i-coin]+1);
        }
        return dp[amount]>amount?-1:dp[amount];

    }

    public static void main(String[] args) {
        CoinChange_322 coinChange322 = new CoinChange_322();
        int[] coins = {1,3,5,10,20};
        int amount = 20;
        long startTime = System.currentTimeMillis();
        System.out.println(coinChange322.coinChange1(coins,amount));
        System.out.println("暴力递归所用时间为："+(System.currentTimeMillis()-startTime));
        startTime = System.currentTimeMillis();
        System.out.println(coinChange322.coinChange2(coins,amount));
        System.out.println("记忆递归时间："+(System.currentTimeMillis()-startTime));
        startTime = System.currentTimeMillis();
        System.out.println(coinChange322.coinChange3(coins,amount));
        System.out.println("动态规划所需时间"+(System.currentTimeMillis()-startTime));
    }
}
