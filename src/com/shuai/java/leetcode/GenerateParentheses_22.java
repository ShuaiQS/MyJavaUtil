package com.shuai.java.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给出n代表生成括号的对数，请写一个函数，时期能够生成所有可能的并且有效的括号组合
 */
public class GenerateParentheses_22 {

    private List<String> generateParenthesis1(int n){
        List<String> list = new ArrayList<>();
        generateParenthesis1(list,"",0,0,n);
        return list;
    }

    /**
     *
     * @param list 用来存放生成的有效括号
     * @param cur 现有括号组合字符串
     * @param open 左括号个数
     * @param close 右括号个数
     * @param max 生成括号的个数
     */
    private void generateParenthesis1(List<String> list, String cur,int open,int close,int max){
        //如果生成有效括号数为max,则将其添加到list并返回
        if(cur.length()==max*2) {
            list.add(cur);
            return;
        }
        if(open<max){
            generateParenthesis1(list,cur+"(",open+1,close,max);
        }
        if(close<open){
            generateParenthesis1(list,cur+")",open,close+1,max);
        }
    }




    public static void main(String[] args) {
        System.out.println(new GenerateParentheses_22().generateParenthesis1(3));
        Set<Integer> set = new HashSet<>();
         
    }
}
