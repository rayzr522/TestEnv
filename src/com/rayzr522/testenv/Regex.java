
package com.rayzr522.testenv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static int numMatches(String text, String regex) {

        Matcher matcher = Pattern.compile(regex).matcher(text);
        int count = 0;

        while (matcher.find()) {
            count++;
        }

        return count;

    }

}
