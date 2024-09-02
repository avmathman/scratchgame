package cyberspeed.task.components;

import cyberspeed.task.configs.CombinationMatch;
import cyberspeed.task.configs.WinCombination;

import java.util.*;
import java.util.stream.Collectors;

public class Matcher {

    private final Map<String, WinCombination> winCombinations;

    protected Matcher() {
        winCombinations = Map.of();
    }

    public Matcher(Map<String, WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }

    public List<MatchResult> match(String[][] matrix) {
        List<MatchResult> result = new ArrayList<>();

        result.addAll(matchSameSymbol(matrix));
        result.addAll(matchLinearSymbol(matrix));

        return result;
    }

    private List<MatchResult> matchSameSymbol(String[][] matrix) {
        Map<Integer, CombinationDescriptor> combinations = new HashMap<>();
        Map<String, Integer> symbols = new HashMap<>();

        winCombinations.entrySet().stream()
                .filter(e -> e.getValue().when() == CombinationMatch.SAME_SYMBOLS)
                .forEach(e -> combinations.put(e.getValue().count(), new CombinationDescriptor(e.getKey(), e.getValue())));

        for (String[] row : matrix) {
            for (String symbol : row) {
                symbols.compute(symbol, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        List<MatchResult> result = new ArrayList<>();
        for (Map.Entry<String, Integer> e : symbols.entrySet()) {
            CombinationDescriptor descriptor = combinations.get(e.getValue());
            if (descriptor != null) {
                MatchResult matchingResult = new MatchResult(
                        e.getKey(),
                        descriptor.name(),
                        descriptor.winCombination().group(),
                        descriptor.winCombination().rewardMultiplier()
                );
                result.add(matchingResult);
            }
        }
        return result;
    }

    private List<MatchResult> matchLinearSymbol(String[][] matrix) {
        List<CombinationDescriptor> combinations = winCombinations.entrySet().stream()
                .filter(e -> e.getValue().when() == CombinationMatch.LINEAR_SYMBOLS)
                .map(e -> new CombinationDescriptor(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return combinations.stream()
                .flatMap(cd -> cd.winCombination().coveredAreas().stream()
                        .filter(coveredArea -> matchCoveredArea(matrix, coveredArea))
                        .map(coveredArea -> new MatchResult(
                                matrix[coveredArea.get(0).row()][coveredArea.get(0).column()],
                                cd.name(),
                                cd.winCombination().group(),
                                cd.winCombination().rewardMultiplier()
                        )))
                .collect(Collectors.toList());
    }

    private boolean matchCoveredArea(String[][] matrix, List<WinCombination.Coordinate> coveredArea) {
        Iterator<WinCombination.Coordinate> iterator = coveredArea.iterator();
        WinCombination.Coordinate first = iterator.next();
        String symbol = matrix[first.row()][first.column()];
        while (iterator.hasNext()) {
            WinCombination.Coordinate next = iterator.next();
            if (!symbol.equals(matrix[next.row()][next.column()])) {
                return false;
            }
        }
        return true;
    }

    protected record CombinationDescriptor(
            String name,
            WinCombination winCombination
    ){}
}
