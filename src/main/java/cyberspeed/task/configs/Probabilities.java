package cyberspeed.task.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author abdurashid.vakhobov
 */
public record Probabilities(
        @JsonProperty("standard_symbols")
        List<Cell> standardSymbols,
        @JsonProperty("bonus_symbols")
        Cell bonusSymbols
){}
