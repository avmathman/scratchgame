package cyberspeed.task.components;

import cyberspeed.task.configs.Config;
import cyberspeed.task.configs.Symbol;

import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ZERO;

/**
 * @author abdurashid.vakhobov
 */
public class BoardAssessor {

    private final Config config;

    private Map<String, List<String>> winningCombinations;
    private Map<String, BigDecimal> rewardMultipliers;

    public BoardAssessor(Config config) {
        this.config = config;
    }

    public ScratchBoard assess(ScratchBoard scratchBoard, BigDecimal bettingAmount) {
        List<MatchResult> matched = new Matcher(config.getWinCombinations()).match(scratchBoard.matrix());
        BigDecimal reward = ZERO;

        if (!matched.isEmpty()) {
            filterMultipliers(matched);
            reward = applyStandard(rewardMultipliers, bettingAmount);
            reward = applyBonus(scratchBoard.bonusSymbol(), reward);
        }

        return new ScratchBoard(
                scratchBoard.matrix(),
                reward,
                winningCombinations,
                reward.equals(ZERO) ? null : scratchBoard.bonusSymbol()
        );
    }

    private void filterMultipliers(List<MatchResult> matched) {
        winningCombinations = new HashMap<>();
        rewardMultipliers = new HashMap<>();
        Set<String> covered = new HashSet<>();

        matched.stream().forEach(item -> {
            if (covered.add(item.symbol() + ":" + item.group())) {
                winningCombinations.computeIfAbsent(item.symbol(), k -> new ArrayList<>())
                        .add(item.combination());

                rewardMultipliers.compute(item.symbol(),
                        (k, v) -> v == null ? item.rewardMultiplier() : v.multiply(item.rewardMultiplier()));
            }
        });
    }

    public BigDecimal applyStandard(Map<String, BigDecimal> rewardMultipliers, BigDecimal reward) {
        BigDecimal rewardMultiplier = rewardMultipliers.keySet()
                .stream()
                .map(value -> config.getSymbols().get(value).rewardMultiplier().multiply(reward))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        return reward.multiply(rewardMultiplier);
    }

    public BigDecimal applyBonus(String bonusSymbol, BigDecimal reward) {
        Symbol symbol = config.getSymbols().get(bonusSymbol);

        switch (symbol.impact()) {
            case MULTIPLY_REWARD:
                return reward.multiply(symbol.rewardMultiplier());
            case EXTRA_BONUS:
                return reward.add(symbol.extra());
            case MISS:
        }

        return reward;
    }
}
