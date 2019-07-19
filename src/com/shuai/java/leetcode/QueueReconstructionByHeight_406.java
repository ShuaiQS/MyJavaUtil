package com.shuai.java.leetcode;

import java.util.*;

/**
 * 假设有打乱顺序的一群人站成一个队列。 每个人由一个整数对(h, k)表示，其中h是这个人的身高，k是排在这个人前面且身高大于或等于h的人数。
 * 编写一个算法来重建这个队列。
 */
public class QueueReconstructionByHeight_406 {
    /**
     * 先将原数组根据子数组第一个元素进行降序排序，得到一个身高从高到矮的列表。
     * 贪心算法求解：从头开始遍历，以子数组的第二个元素作为索引插入到列表中相应位置（插队），直到遍历完成，则队列重建完毕。
     */
    private int[][] reconstructQueue(int[][] people){
        if(people.length==0||people[0].length==0)
            return new int[0][0];
        //按照升高降序 k升序排序
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]==o2[0]?o1[1]-o2[1]:o2[0]-o1[0];
            }
        });

        List<int[]> list = new LinkedList<>();
        for(int[] p : people) {
            list.add(p[1], p);
        }
        return list.toArray(new int[list.size()][]);

        }
    }

