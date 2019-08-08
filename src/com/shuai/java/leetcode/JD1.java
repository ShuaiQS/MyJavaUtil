package com.shuai.java.leetcode;



import java.util.Scanner;
import java.util.TreeSet;

public class JD1 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            TreeSet<Integer> set = new TreeSet<>();
            for(int i=0;i<n;i++){
                set.add(scanner.nextInt());
            }
            for(int i = 0; i < m; i++){
                set.add(scanner.nextInt());
            }

            for(int a:set)
                System.out.print(a+" ");
        }
    }
}
