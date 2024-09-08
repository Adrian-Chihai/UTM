package org.example;

public class CustomMath {
    public static int sum(int x, int y) {
        return x+y;
    }
    public static int division(int x, int y) {
        if (y==0) {
            throw new IllegalArgumentException("devider is 0");
        }
        return(x/y);
    }

    public static boolean isPerfectSquare(int nr) {
        if (nr < 0) {
            return false;
        }
        int sqrt = (int) Math.sqrt(nr);
        return sqrt * sqrt == nr;

    }
    public static void main (String[] args) {

    }
}
