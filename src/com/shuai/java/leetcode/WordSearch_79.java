package com.shuai.java.leetcode;

/**
 * 给定一个二维网格和一个单词，找出该单词是否存在于网格中。单词必须按照字母顺序，通过相邻的单元格内的字母构成，
 * 其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
 *
 */
public class WordSearch_79 {
    /**
     * 遍历二维数组，找到单词的第一个字母，开始深度搜索，如果匹配，返回true，否则继续遍历下一个单词首字符
     * @param board 二维数组
     * @param word 单词
     * @return 二维数组中是否存在单词
     */
    private boolean exist(char[][] board,String word){
        int xLen = board.length;
        if(xLen<1)
            return false;
        int yLen = board[0].length;
        //将单词字符串转化为字符数组，方便读取
        char[] words = word.toCharArray();
        //标志位，二维数组中是否存在单词
        boolean isExist = false;
        //与二维数组同结构的数组，表示每个字符是否被访问过
        boolean[][] isVisited = new boolean[xLen][yLen];
        //遍历二维数组
        for(int i=0;i<xLen;i++){
            for(int j=0;j<yLen;j++){
                //找到与需要查找的单词首字符相同的字母，开始深搜
                if(board[i][j]==words[0]){
                    isVisited[i][j] = true;
                    isExist = exist(board,xLen,yLen,words,0,i,j,isVisited);
                    isVisited[i][j] = false;
                }
                //如果搜到，直接返回true，不再向后遍历
                if(isExist)
                    return true;
            }
        }
        return false;
    }

    /**
     *
     * @param board 二维数组
     * @param xLen 二维数组的行数
     * @param yLen 二维数组的列数
     * @param word 单词字符串数组
     * @param index 比较字符串的第index个字符
     * @param i 二维数组中的x坐标
     * @param j 二维数组中的y坐标
     * @param isVisited 访问二维数组，表示是否访问过了
     * @return 是否存在某单词
     */
    private boolean exist(char[][] board,int xLen,int yLen,char[] word,int index,int i,int j,boolean[][] isVisited){

        //如果现在查找的字符与单词中的字符不等，直接false,不再往下判断
        if(word[index]!=board[i][j])
            return false;
        //如果查找的字符与单词中的字符相等，判断此时的字符是不是单词的最后的一个字符，若是，则表示已经找到，返回true
        if(index==word.length-1)
            return true;
        //向下查
        if(i+1<xLen&&!isVisited[i+1][j]){
            isVisited[i+1][j]=true;
            if(exist(board,xLen,yLen,word,index+1,i+1,j,isVisited))
                return true;
            isVisited[i+1][j]=false;
        }
        //向上查
        if(i-1>=0&&!isVisited[i-1][j]){
            isVisited[i-1][j]=true;
            if(exist(board,xLen,yLen,word,index+1,i-1,j,isVisited))
                return true;
            isVisited[i-1][j]=false;
        }
        //向右查
        if(j+1<yLen&&!isVisited[i][j+1]){
            isVisited[i][j+1]=true;
            if(exist(board,xLen,yLen,word,index+1,i,j+1,isVisited))
                return true;
            isVisited[i][j+1]=false;
        }
        //向左查
        if(j-1>=0&&!isVisited[i][j-1]){
            isVisited[i][j-1]=true;
            if(exist(board,xLen,yLen,word,index+1,i,j-1,isVisited))
                return true;
            isVisited[i][j-1]=false;
        }
        return false;

    }
}
