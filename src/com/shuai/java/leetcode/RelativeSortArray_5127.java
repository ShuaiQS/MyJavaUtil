package com.shuai.java.leetcode;

import java.util.*;

/**
 * 给你两个数组，arr1 和 arr2，
 *
 * arr2 中的元素各不相同
 * arr2 中的每个元素都出现在 arr1 中
 * 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。
 * 未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾
 */
public class RelativeSortArray_5127 {
    public int[] relativeSortArray(int[] arr1, int[] arr2){
        Map<Integer,Integer> map = new HashMap<>();
        int temp,index=0,value;
        int[] result = new int[arr1.length];
        for(int a: arr1){
            if(map.containsKey(a)){
                map.put(a,map.get(a)+1);
            }else{
                map.put(a,1);
            }
        }
        for(int a:arr2){
            if(map.containsKey(a)){
                temp = map.get(a);
                for(int i=0;i<temp;i++){
                    result[index++] = a;
                }
                map.remove(a);
            }
        }
        List<Map.Entry<Integer,Integer>> list = new ArrayList<>(map.entrySet());
//      相对较慢
        list.sort((Map.Entry<Integer,Integer> o1,Map.Entry<Integer,Integer> o2)-> o1.getKey().compareTo(o2.getKey()));
//      相对较慢
        list.sort(Comparator.comparing(Map.Entry::getKey));
//      相对速度最快
        list.sort(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for(Map.Entry<Integer,Integer> m : list){
            temp = m.getValue();
            value = m.getKey();
            for(int j=0;j<temp;j++)
                result[index++] = value;
        }
        return result;
    }

}
