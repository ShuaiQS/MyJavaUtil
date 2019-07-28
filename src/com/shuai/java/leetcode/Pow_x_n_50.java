package com.shuai.java.leetcode;

/**
 * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
 */
public class Pow_x_n_50 {
    public double myPow(double x,int n){
        double res;
        if(x==1)
            return x;
        if(x==-1){
            if((n&1)!=0)
                return -1;
            else
                return 1;
        }

        if(n == Integer.MIN_VALUE)
            return 0;
        if(n==0)
            return 1;

        if(n>0)
            res = powHelp(x,n);
        else{
            n=-n;
            res = powHelp(x,n);
            res = 1/res;
        }
        return res;
    }

    /**
     * 思路：
     * 以 x 的 10 次方举例。10 的 2 进制是 1010，然后用 2 进制转 10 进制的方法把它展成 2 的幂次的和。
     * x^{10}=x^{(1010)_2}=x^{1*2^3+0*2^2+1*2^1+0*2^0}=x^{1*2^3}*x^{0*2^2}x^{1*2^1}*x^{0*2^0}
     * 2 进制对应 1 0 1 0，我们把对应 1 的项进行累乘就可以了，而要进行累乘的项也是很有规律，前一项是后一项的自乘。
     * @param x 底数
     * @param n 幂
     * @return 积
     */
    private double powHelp(double x, int n){
        double ans = 1;
        while(n>0){
            if((n&1)==1){
                ans=ans*x;
            }
            x*=x;
            n=n>>1;
        }
        return ans;
    }
}
