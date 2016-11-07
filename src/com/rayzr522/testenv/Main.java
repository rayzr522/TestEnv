
package com.rayzr522.testenv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

import com.google.common.base.Strings;

public class Main {

    public Main() {

        Pattern pattern = Pattern.compile("(_([^_]+)_)+");

        System.out.println(String.format("(%s([^%s]+)%s)+", "_", "_", "_"));

        String[] input = new String[] { "&lHello world!", "&cHello _world!", "Hello &c&obacon _world!_", "&c&lHello __world!" };

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

    public static void main(String[] args) {

        log("Creating application");
        log(Strings.repeat("-", 50));
        Main app = new Main();

    }

}
