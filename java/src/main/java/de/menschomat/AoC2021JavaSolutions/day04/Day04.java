package de.menschomat.AoC2021JavaSolutions.day04;

import de.menschomat.AoC2021JavaSolutions.utils.InputFetcher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Day04 {

    @Getter
    @AllArgsConstructor
    private static class BingoInput {
        private List<Integer> calls;
        private List<int[][]> boards;
    }

    @Getter
    @NoArgsConstructor
    private static class BingoOutput {
        private final List<List<Integer>> callsForNow = new ArrayList<>();
        private final List<int[][]> winnerBoards = new ArrayList<>();
    }

    public static final String testInput = "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n" + "\n" + "22 13 17 11  0\n" + " 8  2 23  4 24\n" + "21  9 14 16  7\n" + " 6 10  3 18  5\n" + " 1 12 20 15 19\n" + "\n" + " 3 15  0  2 22\n" + " 9 18 13 17  5\n" + "19  8  7 25 23\n" + "20 11 10 24  4\n" + "14 21 16 12  6\n" + "\n" + "14 21 17 24  4\n" + "10 16 15  9 19\n" + "18  8 23 26 20\n" + "22 11 13  6  5\n" + " 2  0 12  3  7";

    public static final String realInput = InputFetcher.getInput(2021, 4);

    public static long partOne(String inStr) {
        BingoInput bingoInput = prepareInput(inStr);
        BingoOutput out = playBingo(bingoInput);
        return (long) out.getCallsForNow().get(0).get(out.getCallsForNow().get(0).size() - 1) *
                Arrays.stream(out.getWinnerBoards().get(0))
                        .flatMapToInt(IntStream::of)
                        .filter(pos -> !out.getCallsForNow().get(0).contains(pos))
                        .sum();
    }

    public static long partTwo(String inStr) {
        BingoInput bingoInput = prepareInput(inStr);
        BingoOutput out = playBingo(bingoInput);
        int last = out.getCallsForNow().size() - 1;
        return (long) out.getCallsForNow().get(last).get(out.getCallsForNow().get(last).size() - 1) *
                Arrays.stream(out.getWinnerBoards().get(last))
                        .flatMapToInt(IntStream::of)
                        .filter(pos -> !out.getCallsForNow().get(last).contains(pos))
                        .sum();
    }

    private static BingoOutput playBingo(BingoInput bingoInput) {
        BingoOutput out = new BingoOutput();
        for (int round = 0; round < bingoInput.calls.size(); round++) {
            List<Integer> callsForNow = bingoInput.calls.subList(0, round);
            bingoInput.boards.forEach(board -> {
                        for (int idx = 0; idx < board.length; idx++) {
                            final int finalIdx = idx;
                            if (callsForNow.containsAll(Arrays.stream(board[finalIdx])
                                    .boxed()
                                    .collect(Collectors.toList())) ||
                                    callsForNow.containsAll(Arrays.stream(board)
                                            .mapToInt(ints -> ints[finalIdx])
                                            .boxed()
                                            .collect(Collectors.toList()))) {
                                out.getCallsForNow().add(new ArrayList<>(callsForNow));
                                out.getWinnerBoards().add(board);
                            }
                        }
                    });
            bingoInput.boards.removeAll(out.getWinnerBoards());
        }
        return out;
    }

    private static BingoInput prepareInput(String inStr) {
        String[] inBlocks = inStr.split("\n\n");
        return new BingoInput(Arrays.stream(inBlocks[0].split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList()),
                Arrays.stream(inBlocks)
                        .skip(1)
                        .map(block -> Arrays.stream(block.split("\n"))
                                .map(str -> Arrays.stream(str.trim().split("\\s+"))
                                        .mapToInt(Integer::parseInt)
                                        .toArray())
                                .toArray(int[][]::new))
                        .collect(Collectors.toList()));
    }
}
