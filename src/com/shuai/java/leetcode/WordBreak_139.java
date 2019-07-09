package com.shuai.java.leetcode;

import java.util.*;

/**
 * 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
 */
public class WordBreak_139 {

    /**
     * 暴力解法：
     * 检查字典单词中每一个单词的可能前缀，如果在字典中出现过，那么去掉这个前缀后剩余部分回归调用。
     * 同时，如果某次函数调用中发现整个字符串都已经被拆分且在字典中出现过了，函数就返回
     * @param s 字符串
     * @param wordDict 字典
     * @return 字符串是否可以拆分为字典中的单词
     * 时间复杂度为O(n^n),空间复杂度为O(n)
     */
    public boolean wordBreak1(String s, List<String> wordDict){
        return wordBreak1(s,new HashSet<>(wordDict),0);
    }

    private boolean wordBreak1(String s, Set<String> wordDict, int start){
        if(start == s.length())
            return true;

        for(int end = start+1;end<=s.length();end++)
            if (wordDict.contains(s.substring(start, end)) && wordBreak1(s, wordDict, end)) {
                return true;
            }
        return false;
    }


    /**
     * 记忆回溯法：
     * 使用记忆化的方法，其中一个 memo 数组会被用来保存子问题的结果。每当访问到已经访问过的后缀串，直接用 memo 数组中的值返回而不需要继续调用函数。
     * 通过记忆化，许多冗余的子问题可以极大被优化，回溯树得到了剪枝，因此极大减小了时间复杂度。
     * @param s 字符串
     * @param wordDict 字典
     * @return 字符串是否可以拆分为字典中的单词
     * 时间复杂度O(n^2) 空间复杂度O(n)
     */
    public boolean wordBreak2(String s,List<String> wordDict){
        return  wordBreak2(s, new HashSet<>(wordDict),0,new Boolean[s.length()]);
    }

    private boolean wordBreak2(String s,Set<String> wordDict, int start, Boolean[] memo){
        if(start == s.length())
            return true;
        if(memo[start]!=null)
            return memo[start];
        for(int end = start+1;end<=s.length();end++){
            if(wordDict.contains(s.substring(start,end))&&wordBreak2(s,wordDict,end,memo))
                return memo[start] = true;
        }
        return memo[start] = false;
    }


    /**
     * 宽度优化搜索
     * 将字符串可视化为一棵树，每一个节点是用end为结尾的前缀字符串。当两个节点之间的所有节点都对应了字典中一个有效字符串时，两个节点可以被连接。
     * 为了形成这样的一棵树，我们从给定字符串的第一个字符开始（比如s），将它作为树的根部，开始找所有可行的以改字为首字符的可行子串，进一步的，
     * 将每一个子字符串的结束字符的下标（比如说i）放在队列的尾部供宽搜索后续使用。
     * 每次我们从队列最前面弹出一个元素，并考虑字符串s(i+1,end)作为原始字符串，当将当前节点作为树的根。这个过程会一直重复，直到队列中没有元素。
     * 如果字符串最后的元素可以作为树的一个节点，这意味着初始字符串可以拆分成多个给定字典中的字符串
     * @param s 字符串
     * @param wordDict 字典
     * @return 字符串是否可以拆分为字典中的单词
     * 时间复杂度：O（n^2)对于每个开始的位置，搜索会直接到给定字符串的尾部结束
     * 空间复杂度：O（n）队列的大小最多为n
     */
    public boolean wordBreak3(String s,List<String> wordDict){
        Set<String> wordDictSet = new HashSet<>(wordDict);
        Queue<Integer> queue = new LinkedList<>();
        int[] visited = new int[s.length()];
        queue.add(0);
        while(!queue.isEmpty()){
            int start = queue.remove();
            if(visited[start] == 0){
                for(int end = start+1;end<=s.length();end++){
                    if(wordDictSet.contains(s.substring(start,end))){
                        queue.add(end);
                        if(end == s.length())
                            return true;
                    }
                }
                visited[start] = 1;
            }

        }
        return false;

    }

    /**
     * 使用动态规划
     * 对于给定的字符串s可以被 拆分为子问题s1,s2如果这些子问题都可以独立的被拆分成符合要求的子问题，那么整个问题s也可以被满足。也就是如果
     * “catsanddog”可以拆分为两个子字符串“catsand”和“dog”。子问题catsand可以进一步拆分为cats和and,这两个图例部分都是字典的一部分，所以
     * catsand满足题意条件，再往前，catsand和dog也分别满足条件，所以整个字符串catsanddog也满足条件。
     * 现在，我们考虑dp数组求解的过程。我们使用n+1大小数组的dp,其中n是给定字符串的长度。我们也使用2个下标指针i和j，其中i是当前字符串从头开始的子字符串
     * 的长度，j是当前子字符串的拆分位置，所以拆分为s(0,j)和s(j+1,i).
     * 为了求出dp数组，我们初始化dp[0]为true，这是因为空字符串总是字典的一部分。dp数组剩余的元素都是初始化为false
     * 我们通过用下标i来考虑所以当前字符串开始的可能子字符串，对于每一个子字符串，我们通过下标j将它拆分为s1和s2.为了将dp[i]数组求出来，我们依次检查每个
     * dp[j]是否为true，也就是每个子字符串是否满足题目要求。如果满足，我们接下来检查s1是否在字典中。如果包含，我们检查s2是否在字典中，如果两个
     * 字符串都满足要求，我们让dp[i]为true，否则另其为false
     * @param s 字符串
     * @param wordDict 字典
     * @return 判断字符串是否能拆分为字典中的单词
     */
    public boolean wordBreak4(String s,List<String> wordDict){
        Set<String> wordDictSet = new HashSet<>(wordDict);
        boolean dp[] = new boolean[s.length()+1];
        dp[0] = true;
        for(int i = 1;i<=s.length();i++){
            for (int j=0;j<i;j++){
                if(dp[j] && wordDictSet.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        for(int i=0;i<dp.length;i++){
            System.out.println("dp["+i+"]="+dp[i]);
        }
        return dp[s.length()];
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("leet");
        list.add("code");
        System.out.println(new WordBreak_139().wordBreak1("leetcode",list));
        System.out.println(new WordBreak_139().wordBreak2("leetcode",list));
        System.out.println(new WordBreak_139().wordBreak3("leetcode",list));
        System.out.println(new WordBreak_139().wordBreak4("leetcode",list));
    }
}
