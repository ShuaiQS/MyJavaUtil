package com.shuai.java.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 */
public class LetterCombinations_17 {

    private String map[] = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};

    private List<String> letterCombinations(String digits){
        List<String> list = new ArrayList<>();
        if(digits!=null&&digits.length()>0){
            helper(list,new StringBuilder(),digits);
        }
        return list;
    }

    /**
     * 将StringBuilder中的长度和digits的长度比较，如果两长度相等，则退出递归，并将StringBuilder中的值添加到list
     * 如果不相等，计算接下来通过StringBuilder中的长度来确定要添加的字母的数字键（也就是map对应的下标），
     * @param list 要返回的list
     * @param sb StringBuilder
     * @param digits 按过的数字键
     */
    private void helper(List<String> list, StringBuilder sb, String digits){
        int sbLen = sb.length();
        if(sbLen==digits.length()){
            list.add(sb.toString());
            return;
        }
        int index = digits.charAt(sbLen)-'0';
        for(int i=0;i<map[index].length();i++){
            helper(list,sb.append(map[index].charAt(i)),digits);
            //将添加过的删掉，循环添加下一个
            sb.delete(sbLen,sbLen+1);
        }
    }

    public static void main(String[] args){
        System.out.println(new LetterCombinations_17().letterCombinations("23"));
    }

}
