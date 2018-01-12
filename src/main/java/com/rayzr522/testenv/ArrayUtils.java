package com.rayzr522.testenv;

import java.lang.reflect.Array;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ArrayUtils {
    @SuppressWarnings("unchecked")
    public static <T> T[] generate(Class<T> componentType, int length, BiFunction<Integer, T[], T> generator) {
        T[] out = (T[]) Array.newInstance(componentType, length);
        for (int i = 0; i < out.length; i++) {
            out[i] = generator.apply(i, out);
        }
        return out;
    }

    public static <T> T[] generate(Class<T> componentType, int length, Function<Integer, T> generator) {
        return generate(componentType, length, (i, a) -> generator.apply(i));
    }

    public static Integer[] fibonacci(int count) {
        return generate(Integer.class, count, (i, a) -> i < 2 ? i : a[i - 1] + a[i - 2]);
    }

    public static Integer[] range(int from, int to) {
        int diff = to - from;
        int abs = Math.abs(diff);
        int sign = diff == 0 ? 1 : diff / abs;

        return generate(Integer.class, abs + 1, i -> from + i * sign);
    }
}
