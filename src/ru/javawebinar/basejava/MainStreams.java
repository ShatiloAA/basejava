package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


public class MainStreams {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1,2,3,3,2,3}));
        System.out.println(minValue(new int[]{9,8}));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 3, 2, 3)));
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 3, 2, 4)));
    }

    private static int minValue(int[] values) {
        int[] digits = stream(values).sorted().distinct().toArray();
        AtomicInteger size = new AtomicInteger(digits.length);
        return stream(digits).reduce(0,
                (x, y) -> x + (y * exponentiationOfTen(size.decrementAndGet())));
    }

    private static int exponentiationOfTen(int e) {
        return (int) Math.pow(10, e);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(0, (x, y) -> x + y);
        if (sum % 2 == 0) {
            return integers.stream().filter(a -> a % 2 != 0).collect(Collectors.toList());
        } else {
            return integers.stream().filter(a -> a % 2 == 0).collect(Collectors.toList());
        }
    }
}
