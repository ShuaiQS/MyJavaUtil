package com.shuai.java.leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 题目：有这样一道智力题：“某商店规定：三个空汽水瓶可以换一瓶汽水。小张手上有十个空汽水瓶，她最多可以换多少瓶汽水喝？”答案是5瓶，
 * 方法如下：先用9个空瓶子换3瓶汽水，喝掉3瓶满的，喝完以后4个空瓶子，用3个再换一瓶，喝掉这瓶满的，这时候剩2个空瓶子。
 * 然后你让老板先借给你一瓶汽水，喝掉这瓶满的，喝完以后用3个空瓶子换一瓶满的还给老板。如果小张手上有n个空汽水瓶，最多可以换多少瓶汽水喝？
 *
 *
 * 要求：输入文件最多包含10组测试数据，每个数据占一行，仅包含一个正整数n（1<=n<=100），
 * 表示小张手上的空汽水瓶数。n=0表示输入结束，你的程序不应当处理这一行。
 */
public class HUAWEI1 {
    static int[] dp;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>(10);
        int n,max=0;
        while( (n=scanner.nextInt())!=0&&list.size()<=10){
            max=Math.max(n,max);
            list.add(n);
        }
        dp = new int[max+1];
        dpValue(max);
        getNum(list);

    }

    private static void getNum(List<Integer> list){
        for(int num:list){
            System.out.println(dp[num]);
        }
    }
    private static void dpValue(int n){
        dp[2]=1;
        for(int i=3;i<=n;i++){
            dp[i] = i/3+dp[i%3+i/3];
        }
    }


}
