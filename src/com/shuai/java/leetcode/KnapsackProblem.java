package com.shuai.java.leetcode;

/**
 * 01背包问题，完全背包，多重背包问题
 */
public class KnapsackProblem {

    public static void main(String[] args) {
        int[] price = {2,3,4};
        int[] weight = {3,4,5};
//        int[] nums = {4,3,2};
        int limit = 15;
//        System.out.println(new KnapsackProblem().knapsack_All(price, weight, limit));
        int[] coint = {1,5,10,20,50,100};
        int[] nums = {6,5,4,3,2,1};
        System.out.println(process(coint,nums,11));
//        System.out.println(new KnapsackProblem().knapsack_Multi(price, weight, nums, limit));
    }
    /**
     * 01背包问题：
     * 你只有一个容量有限的背包，总容量为limit，有n个可待选择的物品，
     * 每个物品只有一件，它们都有各自的重量和价值，你需要从中选择合适的组合来使得你背包中的物品总价值最大。
     * @param price 每件物品的价值
     * @param weight 每件物品的重量
     * @param limit 背包最大可承受重量
     * @return 背包所能装的最大价值
     */
    private int knapsack_01(int[] price, int[] weight, int limit){
        int n = price.length;
        int[][] dp = new int[n+1][limit+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=limit;j++){
                if(weight[i-1]>j)
                    dp[i][j] = dp[i-1][j];
                else
                    dp[i][j] = Math.max(dp[i-1][j-weight[i-1]]+price[i-1],dp[i-1][j]);
            }
        }

        return dp[n][limit];
    }


    /**
     * 完全背包问题
     * 有N种物品和一个容量为limit的背包，每种物品都就可以选择任意多个，第i种物品的价值为price[i]，重量为weight[i]，
     * 求解：选哪些物品放入背包，可使得这些物品的价值最大，并且体积总和不超过背包容量。
     * @param price 每件物品的价值
     * @param weight 每件物品的重量
     * @param limit 背包最大可承受重量
     * @return 背包所能装的最大价值
     */
    private int knapsack_All(int[] price, int[] weight, int limit){
        int n = price.length;
        int[][] dp = new int[n+1][limit+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=limit;j++){
                for(int k=0;k*weight[i-1]<=j;k++){
                    dp[i][j] = Math.max(dp[i][j],dp[i-1][j-k*weight[i-1]]+k*price[i-1]);
                }
            }
        }
        return dp[n][limit];
    }

    /**
     * 多重背包问题
     * 有N种物品和一个容量为limit的背包，第i种物品最多有nums[i]件可用，价值为price[i]，体积为weight[i]，
     * 求解：选哪些物品放入背包，可以使得这些物品的价值最大，并且体积总和不超过背包容量
     * @param price 每件物品的价值
     * @param weight 每件物品的重量
     * @param nums 每件物品的数量
     * @param limit 背包的承重能力
     * @return 背包所能装的最大价值
     */
    private int knapsack_Multi(int[] price, int[] weight,int[] nums, int limit){
        int n = price.length;
        int[][] dp = new int[n+1][limit+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=limit;j++){
                for(int k=0;k<=nums[i-1]&&k*weight[i-1]<=j;k++){
                    dp[i][j] = Math.max(dp[i][j],dp[i-1][j-k*weight[i-1]]+k*price[i-1]);
                }
            }
        }
        for(int i=0;i<=n;i++){
            for(int j=0;j<=limit;j++){
                System.out.print(dp[i][j]+" ");
            }
            System.out.println();
        }

        return dp[n][limit];
    }

    private static int process(int[] coins, int[] nums, int n){
        int[][] d = new int[coins.length+1][n+1];
        for(int i = 0;i<=coins.length;i++)
            d[i][0] = 1;
        for(int i = 1 ;i<=coins.length;i++){
            for(int sum = 1;sum<=n;sum++){
                for(int k=0;k<=nums[i-1]&&k<=sum/coins[i-1];k++){
                    d[i][sum] +=d[i-1][sum-k*coins[i-1]];
                }
            }
        }
        for(int i=0;i<coins.length+1;i++){
            for(int j=0;j<=n;j++){
                System.out.print(d[i][j]+" ");
            }
            System.out.println();
        }
        return d[coins.length][n];

    }

}
