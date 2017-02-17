/**
 * 
 */
package com.rayzr522.testenv;

import java.util.Random;

/**
 * @author Rayzr
 *
 */
public class HashCheck {
    public static char[] possible = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '!', '@', '#', '$', '%',
            '^', '&', '*', '(', ')' };

    public static Random random = new Random();
    public static final int ITERATIONS = 1000000;

    public static void main(String[] args) {

        int target = "Hello".hashCode();
        for (int l = 1; l <= 250; l++) {
            for (int i = 0; i < ITERATIONS; i++) {
                String test = randomString(l);
                int hash = test.hashCode();
                if (hash == target) {
                    System.out.println("+ Found match! " + test);
                    System.exit(0);
                }
                if (Math.abs(target - hash) < 10) {
                    System.out.println("% Almost a match! " + test);
                }
            }
        }

        System.err.println("- No match found!");
        System.exit(1);
    }

    public static String randomString(int len) {
        StringBuilder str = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            str.append(possible[random.nextInt(possible.length)]);
        }
        return str.toString();
    }

}
