package cyberspeed.task.configs;

import java.util.Map;

/**
 * @author abdurashid.vakhobov
 */
public record Cell(
        int column,
        int row,
        Map<String, Integer> symbols
) {
}
