package com.shuai.java.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 我们从一块字母板上的位置 (0, 0) 出发，该坐标对应的字符为 board[0][0]。
 *
 * 在本题里，字母板为board = ["abcde", "fghij", "klmno", "pqrst", "uvwxy", "z"].
 *
 * 我们可以按下面的指令规则行动：
 *
 * 如果方格存在，'U' 意味着将我们的位置上移一行；
 * 如果方格存在，'D' 意味着将我们的位置下移一行；
 * 如果方格存在，'L' 意味着将我们的位置左移一列；
 * 如果方格存在，'R' 意味着将我们的位置右移一列；
 * '!' 会把在我们当前位置 (r, c) 的字符 board[r][c] 添加到答案中。
 * 返回指令序列，用最小的行动次数让答案和目标 target 相同。你可以返回任何达成目标的路径。
 */
public class AlphabetBoardPath_5140 {
    public String alphabetBoardPath(String target) {
        StringBuilder sb = new StringBuilder();
        Map<Character, int[]> map = new HashMap<>();
        char[] targets = target.toCharArray();
        int i=0,current_x=0,current_y=0;
        for (int x = 0; x < 6; x++) {
            for(int y=0;y<5;y++,i++) {
                if (i == 26)
                    break;
                map.put((char) ('a' + i), new int[]{x, y});
            }
        }
        for(char c:targets){
            int[] temp = map.get(c);
            int target_x = temp[0],target_y = temp[1];
            if(c=='z'){
                while (target_y != current_y) {
                    if (current_y < target_y) {
                        sb.append("R");
                        current_y++;

                    }
                    if (current_y > target_y) {
                        sb.append("L");
                        current_y--;
                    }
                }
                while (target_x != current_x) {
                    if (current_x < target_x) {
                        sb.append("D");
                        current_x++;

                    }
                    if (current_x > target_x) {
                        sb.append("U");
                        current_x--;
                    }
                }
                sb.append("!");
            }else {
                while (target_x != current_x) {
                    if (current_x < target_x) {
                        sb.append("D");
                        current_x++;

                    }
                    if (current_x > target_x) {
                        sb.append("U");
                        current_x--;
                    }
                }
                while (target_y != current_y) {
                    if (current_y < target_y) {
                        sb.append("R");
                        current_y++;

                    }
                    if (current_y > target_y) {
                        sb.append("L");
                        current_y--;
                    }
                }
                sb.append("!");
            }
        }
        return sb.toString();


    }

    public static void main(String[] args) {
        System.out.println(new AlphabetBoardPath_5140().alphabetBoardPath("zdz"));
    }
}
