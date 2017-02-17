
package com.rayzr522.testenv;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

import com.google.common.base.Strings;

public class Main {

    public Main() {

        System.out.println("Date: " + new DateCodeFormat().format(new Date()));

        // Breakpoint
        System.exit(0);

        Pattern pattern = Pattern.compile("(_([^_]+)_)+");

        System.out.println(String.format("(%s([^%s]+)%s)+", "_", "_", "_"));

        String[] input = new String[] { "&lHello world!", "&cHello _world!", "Hello &c&obacon _world!_", "&c&lHello __world!" };

        String[] args = new String[] { "hello", "world" };
        List<String> temp = new ArrayList<String>();
        temp.addAll(Arrays.asList(args));

        for (String str : input) {

            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                String end = findLastFormatting(str, matcher.start());
                str = str.replace(matcher.group(1), "&o" + matcher.group(2) + end);
            }
            str = colorize(str);

            System.out.println("Parsed: " + str);

        }

    }

    private static final List<String> prefixes = Arrays.asList("k", "m", "b", "t", "q", "Q", "s", "S", "o", "n", "d");

    /**
     * @param number The number to format
     * @return The formatted number
     */
    public static String formatNumberShort(long number) {
        int prefix = -1;
        long num = number;
        double length = Math.pow(10, (Long.toString(num).length() - 3));
        long dec = num;
        dec /= length;
        dec *= length;
        dec = num - dec;
        dec *= 1000;
        System.out.println(num + "," + dec);
        while (num > 1000) {
            num /= 1000;
            dec /= 1000;
            prefix++;
        }
        if (prefix > -1 && prefix < prefixes.size()) {
            return num + "." + dec + prefixes.get(prefix);
        } else {
            return Long.toString(number);
        }
    }

    /**
     * @param str the input string
     * @param end the maximum position to check up to
     * @return The color and formatting combo that accurately represents the
     *         last formatting codes
     */
    private String findLastFormatting(String str, int end) {
        str = str.replace(ChatColor.COLOR_CHAR, '&').substring(0, end);

        Matcher matcher = Pattern.compile("&([0-9a-f])").matcher(str);
        String color = "&r";
        int pos = -1;
        while (matcher.find()) {
            color = matcher.group();
            pos = matcher.start();
        }

        String format = "";
        matcher = Pattern.compile("&([klmnor])").matcher(str);
        while (matcher.find()) {
            if (matcher.start() < pos) {
                continue;
            }
            format = matcher.group();
        }

        return color + format;
    }

    /**
     * @param str
     * @return
     */
    private String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void err(Object obj) {
        System.err.println(obj);
        System.exit(1);
    }

    private static void log(Object obj) {
        System.out.println(obj);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        log("Creating application");
        log(Strings.repeat("-", 50));
        Main app = new Main();

    }

}
