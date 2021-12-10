package de.menschomat.AoC2021JavaSolutions.day03;

import de.menschomat.AoC2021JavaSolutions.utils.InputFetcher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Day03 {

    public static final String testInput
            = "00100\n" +
            "11110\n" +
            "10110\n" +
            "10111\n" +
            "10101\n" +
            "01111\n" +
            "00111\n" +
            "11100\n" +
            "10000\n" +
            "11001\n" +
            "00010\n" +
            "01010";

    public static final String realInput = InputFetcher.getInput(2021, 3);

    public static long partOne(String inStr) {
        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();
        int[][] input = prepareInput(inStr);
        for (int i = 0; i < input[0].length; i++) {
            int zero = 0, one = 0;
            for (int[] ints : input) {
                if (ints[i] == 0) zero += 1;
                else one += 1;
            }
            gamma.append(zero > one ? 0 : 1);
            epsilon.append(zero > one ? 1 : 0);
        }
        return Long.parseLong(gamma.toString(), 2) * Long.parseLong(epsilon.toString(), 2);
    }

    public static long partTwo(String inStr) {
        return filterForGasLeve("oxygen", inStr) * filterForGasLeve("co2", inStr);
    }


    private static long filterForGasLeve(String gas, String inStr) {
        List<int[]> input = Arrays.asList(prepareInput(inStr));
        for (int i = 0; i < input.get(0).length; i++) {
            int zero = 0, one = 0;
            for (int[] ints : input) {
                if (ints[i] == 0) zero += 1;
                else one += 1;
            }
            int checker = gas.equals("oxygen") ? (one >= zero ? 1 : 0) : (zero <= one ? 0 : 1);
            int finalI = i;
            input = input.stream().filter(arr -> arr[finalI] == checker).collect(Collectors.toList());
            if (input.size() == 1) break;
        }
        return Long.parseLong(
                Arrays.stream(input.get(0))
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining()),2
        );
    }

    private static int[][] prepareInput(String inStr) {
        return Arrays.stream(inStr.split("\n"))
                .map(str -> Arrays.stream(str.split(""))
                        .mapToInt(Integer::parseInt)
                        .toArray()).toArray(int[][]::new);
    }


}
