package com.shuai.java.leetcode;

import java.util.Stack;

/**
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 */
public class DecodeString_394 {
    private String decodeString(String s){
        Stack<String> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder sbTemp = new StringBuilder();
        String temp;
        //1.将字符串转为字符数组
        char[] cs = s.toCharArray();
        //2.将数字字符转为整数压入栈中，将字符依次压入栈中，直到遇到']'，开始出栈，将出栈的
        //字符拼接起来，直到遇到'['，然后弹出数字，进行解码，解码完，再压入栈中
        for(int i=0;i<cs.length;i++){
            while(cs[i]>='0'&&cs[i]<='9'){
                sbTemp.append(cs[i]);
                i++;
            }
            if(sbTemp.length()>0) {
                stack.push(sbTemp.toString());
                sbTemp.delete(0,sbTemp.length());
            }
            if(cs[i]==']'){
                while(!"[".equals(stack.peek())){
                    sb.insert(0,stack.pop());
                }
                temp = sb.toString();
                sb.delete(0,sb.length());
                //弹出“[”
                stack.pop();
                //弹出数字
                int n=Integer.valueOf(stack.pop());
                for(int j=0;j<n;j++){
                    sb.append(temp);
                }
                stack.push(sb.toString());
                sb.delete(0,sb.length());

            }else{
                stack.push(Character.toString(cs[i]));
            }
        }
        //3.遍历完后，将栈中的值弹出，便是此题答案

        while(!stack.isEmpty()){
            sb.insert(0,stack.pop());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new DecodeString_394().decodeString("3[z]2[2[y]pq4[2[jk]e1[f]]]ef"));
    }

}
