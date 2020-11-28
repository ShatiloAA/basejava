package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


public class MainStreams {

    public static void main(String[] args) {

        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));

        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 3, 2, 3)));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 3, 2, 4)));
    }

    private static int minValue(int[] values) {
        return stream(values)
                .distinct()
                .sorted()
                .reduce(0,
                        (x, y) -> x * 10 + y);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return integers.stream().filter(sum % 2 == 0 ? a -> a % 2 != 0 : a -> a % 2 == 0).collect(Collectors.toList());
    }
}
