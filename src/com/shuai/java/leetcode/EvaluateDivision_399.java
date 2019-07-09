package com.shuai.java.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 给出方程式 A / B = k, 其中 A 和 B 均为代表字符串的变量， k 是一个浮点型数字。根据已知方程式求解问题，并返回计算结果。
 * 如果结果不存在，则返回 -1.0。
 * 示例 :
 * 给定 a / b = 2.0, b / c = 3.0
 * 问题: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? 
 * 返回 [6.0, 0.5, -1.0, 1.0, -1.0 ]
 */
public class EvaluateDivision_399 {


    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        HashMap<String, HashMap<String, Double>>  graph = buildGraph(equations, values);
        double[] res = new double[queries.size()];
        for(int i = 0; i < queries.size(); i++) {
            double tem = queryRes(graph, queries.get(i).get(0), queries.get(i).get(1), new HashSet<>());
            res[i] = tem;
        }
        return res;
    }

    private HashMap<String, HashMap<String, Double>> buildGraph(List<List<String>> equations, double[] values) {
        HashMap<String, HashMap<String, Double>>  graph = new HashMap<>();
        for(int i = 0; i < equations.size(); i++) {
            String eq1 = equations.get(i).get(0),eq2 = equations.get(i).get(1);
            if(!graph.containsKey(eq1)) {
                graph.put(eq1, new HashMap<>());
            }
            graph.get(eq1).put(eq2, values[i]);
            if(!graph.containsKey(eq2)) {
                graph.put(eq2, new HashMap<>());
            }
            graph.get(eq2).put(eq1,1/ values[i]);
        }
        return graph;
    }

    private double queryRes(HashMap<String, HashMap<String, Double>> graph, String start, String end, HashSet<String> visited) {
        if(!graph.containsKey(start))
            return -1.0;
        if(graph.get(start).containsKey(end))
            return graph.get(start).get(end);
        visited.add(start);
        for(Map.Entry<String, Double> item : graph.get(start).entrySet()) {
            if(!visited.contains(item.getKey())) {
                double ttt = queryRes(graph, item.getKey(), end, visited);
                if(ttt != -1.0) return ttt*item.getValue();
            }
        }
        return -1.0;
    }
}
