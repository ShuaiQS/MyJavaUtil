package com.shuai.java.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
 *
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-sudoku
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ValidSudoku_36 {
    public boolean isValidSudoku(char[][] board){
        Map<Integer,Set<Character>> mapX = new HashMap<>();
        Map<Integer,Set<Character>> mapY = new HashMap<>();
        Map<Integer,Set<Character>> mapB = new HashMap<>();
        Set<Character> s;
        int bIndex;
        for(int i = 0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                if(board[i][j]!='.') {
                    if (mapX.containsKey(i)) {
                        s = mapX.get(i);
                        if (s.contains(board[i][j]))
                            return false;
                        else
                            s.add(board[i][j]);

                    } else {
                        Set<Character> set = new HashSet<>();
                        set.add(board[i][j]);
                        mapX.put(i, set);
                    }


                    if (mapY.containsKey(j)) {
                        s = mapY.get(j);
                        if (s.contains(board[i][j]))
                            return false;
                        else
                            s.add(board[i][j]);

                    } else {
                        Set<Character> set = new HashSet<>();
                        set.add(board[i][j]);
                        mapY.put(j, set);
                    }
                    bIndex = (i/3)*3 +j/3;
                    if (mapB.containsKey(bIndex)) {
                        s = mapB.get(bIndex);
                        if (s.contains(board[i][j]))
                            return false;
                        else
                            s.add(board[i][j]);

                    } else {
                        Set<Character> set = new HashSet<>();
                        set.add(board[i][j]);
                        mapB.put(bIndex, set);
                    }
                }
            }
        }
        return true;
    }
}
