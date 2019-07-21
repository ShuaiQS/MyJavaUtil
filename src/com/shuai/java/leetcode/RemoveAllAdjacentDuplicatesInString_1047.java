package com.shuai.java.leetcode;

/**
 * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 * 在 S 上反复执行重复项删除操作，直到无法继续删除。
 */
public class RemoveAllAdjacentDuplicatesInString_1047 {
    /**
     * 删除相邻重复的字符
     * @param S 字符串
     * @return 删除完重复字符后的字符串
     * 思路：先将字符串转化为字符数组，初始化符合要求字符串的边界为-1,
     * 接着遍历字符数组，如果边界所指的值等于要遍历的值，则表示相邻两个字符重复，
     * 将边界值向前退一位，如果不相等，则遍历所得的值赋给边界值所指的位置。
     * 最后返回从0到边界值加1的字符串。
     */
    private String removeDuplicates(String S){
        char[] cs = S.toCharArray();
        int border = -1;
        if(cs.length<2)
            return S;
        for(char c:cs){
            if(border>-1&&cs[border]==c){
                border--;
            }else{
                cs[++border]=c;
            }
        }
        return  new String(cs,0,border+1);
    }
}
