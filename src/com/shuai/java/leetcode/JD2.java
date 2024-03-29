package com.shuai.java.leetcode;

import java.util.*;

/**
 * 题目描述：
 * 过年啦！小B高兴的不行了，她收到了很多红包，可以实现好多的愿望呢。小B可是对商店货架上心仪的货物红眼好久了，只因囊中羞涩作罢，
 * 这次她可是要大大的shopping一番。小B想去购物时，总是习惯性的把要买的东西列在一个购买清单上，每个物品单独列一行（即便要买多个某种物品），这次也不例外。
 * 小B早早的来到了商店，由于她太激动，以至于她到达商店的时候，服务员还没有把各个商品的价签排好，所有的价签还都在柜台上。
 * 因此还需要一段时间，等服务器把价签放到对应的商品处，小B才能弄清她的购买清单所需的费用。
 *
 * 输入：
 * 输入中有多组测试数据。每组测试数据的第一行为两个整数n和m（1=＜n, m=＜1000），分别表示价签的数量以及小B的购买清单中所列的物品数。
 * 第二行为空格分隔的n个正整数，表示货架上各类物品的价格，每个数的大小不超过100000。随后的m行为购买清单中物品的名称，
 * 所有物品名称为非空的不超过32个拉丁字母构成的字符串，保证清单中不同的物品种类数不超过n，且商店有小B想要购买的所有物品。
 *
 * 输出：
 * 对每组测试数据，在单独的行中输出两个数a和b，表示购买清单上所有的物品可能需要的最小和最大费用。
 */
public class JD2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int[] price = new int[n];
            String[] goods = new String[m];

            for(int i=0;i<n;i++){
                price[i] = scanner.nextInt();
            }

            for(int i=0;i<m;i++){
                goods[i] = scanner.next();
            }

            getResult(price,goods);
            System.out.println();

        }
    }
    private static void getResult(int[] price,String[] goods){
        int i=0,j= price.length-1,max=0,min=0;
        Arrays.sort(price);
        Map<String,Integer> map = new HashMap<>();

        for(String good:goods){
            if(map.containsKey(good)){
                map.put(good,map.get(good)+1);
            }else{
                map.put(good,1);
            }
        }

        List<Map.Entry<String,Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });


        for(Map.Entry<String,Integer> entry:list ){
            min += entry.getValue()*price[i];
            max += entry.getValue()*price[j];
            i++;
            j--;
        }

        System.out.print(min+" "+max);


    }
}
