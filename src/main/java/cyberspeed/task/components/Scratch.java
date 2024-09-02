package cyberspeed.task.components;

import cyberspeed.task.configs.Cell;
import cyberspeed.task.configs.Config;

import java.math.BigDecimal;
import java.util.Random;

import static java.math.BigDecimal.ZERO;

/**
 * @author abdurashid.vakhobov
 */
public record Scratch(
        Config config
) {
    public ScratchBoard generate() {
        boolean isBonus = config.probabilities().bonusSymbols() != null;

        String[][] board = new String[config.rows()][config.columns()];

        String bonusSymbol = null;

        for (Cell cell : config.probabilities().standardSymbols()) {
            if (isBonus && new Random().nextBoolean()) {
                bonusSymbol = new Bonus(config.probabilities().bonusSymbols()).next();   //save one look across the board
                board[cell.row()][cell.column()] = bonusSymbol;
                isBonus = false;
            } else {
                board[cell.row()][cell.column()] = new Bonus(cell).next();
            }
        }
        return new ScratchBoard(board, ZERO, null, bonusSymbol);
    }

    public ScratchBoard scratch(BigDecimal bettingAmount) {
        return new BoardAssessor(config).assess(generate(), bettingAmount);
    }
}
