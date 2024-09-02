package cyberspeed.task.components;

import cyberspeed.task.configs.CombinationGroup;

import java.math.BigDecimal;

public record MatchResult(
        String symbol,
        String combination,
        CombinationGroup group,
        BigDecimal rewardMultiplier
) {
}
