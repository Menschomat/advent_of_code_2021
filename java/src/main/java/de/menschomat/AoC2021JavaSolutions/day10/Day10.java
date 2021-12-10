package de.menschomat.AoC2021JavaSolutions.day10;

import de.menschomat.AoC2021JavaSolutions.utils.InputFetcher;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Day10 {

    public static final String testInput
            = "[({(<(())[]>[[{[]{<()<>>\n" +
            "[(()[<>])]({[<{<<[]>>(\n" +
            "{([(<{}[<>[]}>{[]{[(<()>\n" +
            "(((({<>}<{<{<>}{[]{[]{}\n" +
            "[[<[([]))<([[{}[[()]]]\n" +
            "[{[{({}]{}}([{[{{{}}([]\n" +
            "{<[[]]>}<{[{[{[]{()[[[]\n" +
            "[<(<(<(<{}))><([]([]()\n" +
            "<{([([[(<>()){}]>(<<{{\n" +
            "<{([{{}}[<[[[<>{}]]]>[]]";

    public static final String realInput = InputFetcher.getInput(2021, 10);

    private static final Map<Character, Integer> errorPoints = Map.of(
            ')', 3,
            ']', 57,
            '}', 1197,
            '>', 25137
    );
    private static final Map<Character, Integer> closePoints = Map.of(
            ')', 1,
            ']', 2,
            '}', 3,
            '>', 4
    );
    private static final List<Character> openings = Arrays.asList('(', '{', '[', '<');
    private static final List<Character> closings = Arrays.asList(')', '}', ']', '>');

    public static long partOne(String inStr) {
        long out = 0;
        for (char[] input : prepareInput(inStr)) {
            List<Character> opens = new ArrayList<>();
            for (Character c : input) {
                if (openings.contains(c))
                    opens.add(c);
                else if (closings.contains(c)) {
                    if (opens.get(opens.size() - 1).compareTo(openings.get(closings.indexOf(c))) == 0) {
                        opens.remove(opens.size() - 1);
                    } else {
                        out += errorPoints.get(c);
                        break;
                    }
                }
            }
        }
        return out;
    }

    public static long partTwo(String inStr) {
        List<Long> scores = new ArrayList<>();
        for (char[] input : prepareInput(inStr)) {
            List<Character> opens = new ArrayList<>();
            for (int i = 0; i < input.length; i++) {
                char c = input[i];
                if (openings.contains(c)) opens.add(c);
                else if (closings.contains(c)) {
                    if (opens.get(opens.size() - 1).compareTo(openings.get(closings.indexOf(c))) == 0) {
                        opens.remove(opens.size() - 1);
                    } else break;
                }
                if (i == input.length - 1 && opens.size() > 0) {
                    long lineScore = 0;
                    Collections.reverse(opens);
                    for (Character open : opens) {
                        lineScore = lineScore * 5 + closePoints.get(closings.get(openings.indexOf(open)));
                    }
                    scores.add(lineScore);
                }
            }
        }
        return scores.stream().sorted().collect(Collectors.toList()).get((scores.size() - 1) / 2);
    }

    private static List<char[]> prepareInput(String inStr) {
        return Arrays.stream(inStr.split("\n"))
                .map(String::toCharArray)
                .collect(Collectors.toList());
    }
}
