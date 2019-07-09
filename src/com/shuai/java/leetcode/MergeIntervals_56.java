package com.shuai.java.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 给出一个区间的集合，请合并所以重叠的区域
 */
public class MergeIntervals_56 {
    public int[][] merge(int[][] intervals) {
        if(intervals.length==1)
            return intervals;
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]>o2[0])
                    return 1;
                else if(o1[0]<o2[0])
                    return -1;
                else
                    return 0;
            }
        });
        List<int[]> list = new ArrayList<>();

        for(int i=1;i<intervals.length;i++){
            if(intervals[i-1][1]>=intervals[i][0]){
                intervals[i][0]=intervals[i-1][0];
                intervals[i][1]=Math.max(intervals[i][1],intervals[i-1][1]);

            }else{
                list.add(intervals[i-1]);
            }
            if(i==intervals.length-1)

                list.add(intervals[i]);

        }
        return list.toArray(new int[list.size()][]);
    }

    public static void main(String[] args) {
        new MergeIntervals_56().merge(new int[][] {{1,5},{5,6},{5,8},{6,10} });
    }
}
