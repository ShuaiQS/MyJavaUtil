package com.shuai.java.leetcode;

/**
 * 编写一个函数，输入是一个无符号整数，返回其二进制表达式中数字位数为 ‘1’ 的个数（也被称为汉明重量）。
 */
public class NumberOf1Bit_191 {

    public static void main(String[] args) {

    }
    public int hammingWeight(int n) {
        int res=0;
        for(int i=0;i<32;i++){
            if((n&1)==1)
                res++;
            n=n>>1;
        }
        return res;
    }
}
