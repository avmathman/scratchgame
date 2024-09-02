package cyberspeed.task.components;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author abdurashid.vakhobov
 */
public record ScratchBoard(
        String[][] matrix,
        BigDecimal reward,
        @JsonProperty("applied_winning_combinations")
        Map<String, List<String>> winningCombinations,
        @JsonProperty("applied_bonus_symbol")
        String bonusSymbol
) {
}
