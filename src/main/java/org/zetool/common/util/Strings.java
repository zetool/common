package org.zetool.common.util;

import java.util.Arrays;
import java.util.function.IntPredicate;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class Strings {
    
    private static final IntPredicate ZERO_COUNT = count -> count == 0;
    
    /**
     * Decides whether two {@link String}s are true permutations of each other. Does take account of whitespaces,
     * symbols and is case distinctive.
     * 
     * @param s1 first input string
     * @param s2 second input string
     * @return whether the instances are permutations of each other
     */
    public static boolean isPermutation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        // Needs roughly 260 KiB 
        int[] charCount = new int[Character.MAX_VALUE];
        for(int i = 0; i < s1.length();++i) {
            charCount[s1.charAt(i)]++;
            charCount[s2.charAt(i)]--;
        }

        return Arrays.stream(charCount).allMatch(ZERO_COUNT);
    }
    
}
