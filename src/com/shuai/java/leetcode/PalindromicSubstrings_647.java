package com.shuai.java.leetcode;

import java.util.Scanner;

/**
 * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被计为是不同的子串。
 */
public class PalindromicSubstrings_647 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        System.out.println(new PalindromicSubstrings_647().countSubstrings(s));
    }

    /**
     * 中心扩展：将每个字符从中间往两边扩展
     * @param s 字符串
     * @return 包含回文串的个数
     */
    private int countSubstrings(String s){
        int count = 0;
        char[] chars = s.toCharArray();
        for(int i=0;i<chars.length;i++){
            count+=countSubstrings(chars, i, i);
            count+=countSubstrings(chars, i, i+1);
        }
         return count;
    }
    private int countSubstrings(char[] chars, int start, int end){
        int count = 0,len = chars.length;
        while(start>=0&&end<len&&chars[start--]==chars[end++])
            count++;
        return count;
    }

}
