package com.example;

import java.util.function.Predicate;

public class StringUtility {

    public static boolean isPalindrom(String input) {
        Predicate<String> isPalindrome = str -> {
            String cleanedStr = str.replaceAll("\\s+", "").toLowerCase(); // Remove spaces and convert to lower case
            int length = cleanedStr.length();
            for (int i = 0; i < length / 2; i++) {
                if (cleanedStr.charAt(i) != cleanedStr.charAt(length - 1 - i)) {
                    return false;
                }
            }
            return true;
        };
        return isPalindrome.test(input);
    }
}
