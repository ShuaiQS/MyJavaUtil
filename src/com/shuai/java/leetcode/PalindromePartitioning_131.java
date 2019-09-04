package com.shuai.java.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
 *
 * 返回 s 所有可能的分割方案。
 */
public class PalindromePartitioning_131 {
    /**
     * 分治法：
     * 比如 aab
     * 先把aab分为a|ab,可分为[a,b]再将a加在前面即[a,a,b]
     * 再把aab分为aa|b,可分为[b]再加上前面的aa,即[aa,b]
     * @param s 字符串
     * @return 返回字符串中所有回文串
     */
    private static List<List<String>> partition1(String s) {
        return getPart(0,s);

    }
    private static List<List<String>> getPart(int start, String s){
        if(start==s.length()){
            List<String> list = new ArrayList<>();
            List<List<String>> ans = new ArrayList<>();
            ans.add(list);
            return ans;
        }
        List<List<String>> ans = new ArrayList<>();
        for(int i=start;i<s.length();i++){
            if(isPalindrome(s.substring(start, i+1))){
                String left = s.substring(start, i+1);
                for(List<String> l: getPart(i+1, s)){
                    l.add(0,left);
                    ans.add(l);
                }
            }
        }
        return ans;
    }
    private static boolean isPalindrome(String s){
        int i = 0;
        int j = s.length()-1;
        while (i<j){
            if(s.charAt(i)!=s.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    /**
     * 分治优化
     * 上面分治方法判断一个字符串是否是回文串的时候，都会调用isPalindrome来判断，这就造成了许多重复性工作，所以我们就用dp[i][j]
     * 表示从第i个到第j个字符串是否为回文串，若dp[i][j]是回文串且s[i-1]==s[j+1]则dp[i-1][j+1]也是回文串
     * @param s 字符串
     * @return 返回字符串中所有回文串
     */
    private static List<List<String>> partition2(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        int length = s.length();
        for(int i=1;i<=length;i++){
            for(int j=0;j<=s.length()-i;j++){
                int n = j+i-1;
                dp[j][n] = s.charAt(j) == s.charAt(n)&&(i<3||dp[j+1][n-1]);
            }
        }
        return getPart(s, 0, dp);

    }

    private static List<List<String>> getPart(String s, int start, boolean[][] dp){
        if(start == s.length()){
            List<String> list = new ArrayList<>();
            List<List<String>> ans = new ArrayList<>();
            ans.add(list);
            return ans;
        }
        List<List<String>> ans = new ArrayList<>();
        for(int i = start;i<s.length();i++){
            if(dp[start][i]){
                String left = s.substring(start, i+1);
                for(List<String> l: getPart( s,i+1,dp)){
                    l.add(0,left);
                    ans.add(l);
                }
            }
        }
        return ans;
    }

    private static List<List<String>> partition3(String s){
        boolean[][] dp = new boolean[s.length()][s.length()];
        int length = s.length();
        for(int i=1;i<=length;i++){
            for(int j=0;j<=s.length()-i;j++){
                int n = j+i-1;
                dp[j][n] = s.charAt(j) == s.charAt(n)&&(i<3||dp[j+1][n-1]);
            }
        }
        List<List<String>> ans = new ArrayList<>();
        getPart(s, 0, dp, new ArrayList<>(), ans);
        return ans;

    }

    private static void getPart(String s, int start, boolean[][] dp, List<String> temp, List<List<String>> res) {
        //到了空串就加到最终的结果中
        if (start == s.length()) {
            res.add(new ArrayList<>(temp));
        }
        //在不同位置切割
        for (int i = start; i < s.length(); i++) {
            //如果是回文串就加到结果中
            if (dp[start][i]) {
                String left = s.substring(start, i + 1);
                temp.add(left);
                getPart(s, i + 1, dp, temp, res);
                temp.remove(temp.size() - 1);
            }

        }
    }



    public static void main(String[] args) {
        System.out.println(partition3("aab"));
    }
}
