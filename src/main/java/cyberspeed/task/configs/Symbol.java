package cyberspeed.task.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author abdurashid.vakhobov
 */
public record Symbol(
        @JsonProperty("reward_multiplier")
        BigDecimal rewardMultiplier,
        BigDecimal extra,
        SymbolType type,
        ImpactType impact
) {
}
