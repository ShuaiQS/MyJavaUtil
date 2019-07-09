package com.shuai.java.leetcode;

/**
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 */
public class LongestPalindromicSubstring_5 {
    /**
     *循环将每个字符作为回文串的中心向两边扩展，找出最长的那个
     * @param s 含有回文串的字符串
     * @return 返回最长字符串
     */
    private String longestPalindrome(String s){
        char[] cs = s.toCharArray();
        String temp,max = "";
        for(int i=0;i<cs.length;i++){
            //当回文串长度为单数时，以一个数为中心
            temp = longestPalindrome(cs, i, i);
            if(temp.length()>max.length())
                max = temp;
            //当会问串为偶数时，以两个数为中心
            temp = longestPalindrome(cs, i, i+1);
            if(temp.length()>max.length())
                max = temp;
        }
        return max;
    }

    /**
     *
     * @param cs 字符数组
     * @param i 第一个回文串的中心
     * @param j 第二个回文串的中心
     * @return 返回回文串
     */
    private String longestPalindrome(char[] cs, int i, int j){
        int len = cs.length;
        while (i>=0&&j<len&&cs[i]==cs[j]){
            i--;
            j++;
        }
        return String.valueOf(cs).substring(i+1,j);
    }

}
