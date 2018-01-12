package com.rayzr522.testenv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    public static final File FOLDER = new File("~/Desktop/Temp/");
    private File file;
    private HashMap<String, String> props = new HashMap<String, String>();

    private Main() {
        log("Loading file...");
        file = new File(getClass().getResource("/test.txt").getPath());
        log("File loaded @ " + file.getAbsolutePath());

        log(ArrayUtils.fibonacci(10));
        log(ArrayUtils.range(1, 10));
        log(ArrayUtils.range(10, 1));
    }

    private static void err(Object obj) {
        System.err.println(obj);
        System.exit(1);
    }

    private static <T> void log(T[] array) {
        log("[" + Arrays.stream(array).map(Objects::toString).collect(Collectors.joining(", ")) + "]");
    }

    private static void log(Object obj) {
        System.out.println(obj);
    }

    public static void main(String[] args) {
        log("Creating application");
        Main app = new Main();

        log("Creating and starting thread");
        new Thread(() -> {
            try {
                app.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        log("Thread started");
    }

    private void run() throws Exception {
        if (!file.exists()) {
            err("File doesn't exist!");
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));

        log("Reading lines:");

        reader.lines()
                .filter(Objects::nonNull)
                .filter(line -> !line.isEmpty() && !line.startsWith("#") && !line.startsWith("//"))
                .forEach(line -> {
                    if (!line.contains(":") || line.split(":").length <= 1) {
                        err("Invalid config line: '" + line + "'");
                        return;
                    }

                    int sep = line.indexOf(':');

                    props.put(
                            line.substring(0, sep).trim(),
                            line.substring(sep + 1, line.length()).trim()
                    );
                });

        props.forEach((key, value) -> {
            log(key + ": " + value);
        });

        reader.close();
    }

}
