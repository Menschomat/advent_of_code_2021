package de.menschomat.AoC2021JavaSolutions.day09;

import de.menschomat.AoC2021JavaSolutions.utils.InputFetcher;

import java.util.*;

public abstract class Day09 {

    public static final String testInput
            = "2199943210\n" +
            "3987894921\n" +
            "9856789892\n" +
            "8767896789\n" +
            "9899965678";

    public static final String realInput = InputFetcher.getInput(2021, 9);

    public static long partOne(String inStr) {
        long out = 0;
        int[][] map = generateInput(inStr);
        for (int[] cord : getLowsCords(map)) {

            out += map[cord[1]][cord[0]] + 1;
        }
        return out;
    }

    public static long partTwo(String inStr) {
        int[][] map = generateInput(inStr);
        List<int[]> cords = getLowsCords(map);
        return cords
                .stream()
                .map(cord -> flood(9, cord[0], cord[1], map))
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(1, (a, b) -> a * b);
    }

    private static int flood(int border, int x, int y, int[][] map) {
        int out = 0;
        if (map[y][x] == border) {
            return out;
        } else {
            out++;
            map[y][x] = border;
            if (x - 1 > -1) {
                out += flood(border, x - 1, y, map);
            }
            if (x + 1 < map[y].length) {
                out += flood(border, x + 1, y, map);
            }
            if (y - 1 > -1) {
                out += flood(border, x, y - 1, map);
            }
            if (y + 1 < map.length) {
                out += flood(border, x, y + 1, map);
            }
        }
        return out;
    }

    private static List<int[]> getLowsCords(int[][] map) {
        List<int[]> out = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if ((i - 1 < 0 || map[i - 1][j] > map[i][j]) &&
                        (j - 1 < 0 || map[i][j - 1] > map[i][j]) &&
                        (i + 1 == map.length || map[i + 1][j] > map[i][j]) &&
                        (j + 1 == map[i].length || map[i][j + 1] > map[i][j])) {
                    out.add(new int[]{j, i});
                }
            }
        }
        return out;
    }


    private static int[][] generateInput(String inStr) {
        return Arrays
                .stream(inStr.split("\n"))
                .map(str -> Arrays
                        .stream(str.split(""))
                        .mapToInt(Integer::parseInt)
                        .toArray()
                ).toArray(int[][]::new);
    }
}
