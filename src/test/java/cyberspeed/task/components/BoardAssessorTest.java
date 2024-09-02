package cyberspeed.task.components;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import cyberspeed.task.configs.Config;
import cyberspeed.task.configs.ImpactType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static java.math.BigDecimal.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardAssessorTest {

    private Config config;

    @BeforeEach
    void setUp() throws IOException {
        ObjectMapper mapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();

        config = mapper.readValue(getClass().getClassLoader().getResource("config.json"), Config.class);
    }

    @Test
    void assess_passMatch_shouldWin() {

        // Assign
        int expected = 150000;
        String[][] matrix = new String[][]{
                {"B", "C", "D"},
                {"E", "B", "5x"},
                {"B", "D", "A"}
        };

        BoardAssessor analyzer = new BoardAssessor(config);
        ScratchBoard gameBoard = new ScratchBoard(
                matrix,
                ONE,
                null,
                "5x"
        );

        // Act
        gameBoard = analyzer.assess(gameBoard, new BigDecimal("100"));

        //Assert
        assertEquals(expected, gameBoard.reward().intValue());
        assertEquals("same_symbol_3_times", gameBoard.winningCombinations().get("B").get(0));
        assertNotNull(gameBoard.winningCombinations());
        assertNotNull(gameBoard.bonusSymbol());
    }

    @Test
    void assess_passNoMatch_shouldFail() {

        // Assign
        String[][] matrix = new String[][]{
                {"B", "C", "D"},
                {"E", "B", "5x"},
                {"F", "D", "C"}
        };

        BoardAssessor analyzer = new BoardAssessor(config);
        ScratchBoard gameBoard = new ScratchBoard(
                matrix,
                ZERO,
                null,
                "5x"
        );

        // Act
        gameBoard = analyzer.assess(gameBoard, new BigDecimal("100"));

        // Assert
        assertEquals(ZERO, gameBoard.reward());
        assertNull(gameBoard.winningCombinations());
        assertNull(gameBoard.bonusSymbol());
    }

    @Test
    void assess_passTwoDifferentSymbolsMatch_validateTwoCombinations() {

        // Assign
        int expected = 150000;
        String[][] matrix = new String[][]{
                {"B", "C", "D"},
                {"E", "B", "5x"},
                {"A", "D", "B"}
        };

        BoardAssessor analyzer = new BoardAssessor(config);
        ScratchBoard gameBoard = new ScratchBoard(
                matrix,
                ONE,
                null,
                "5x"
        );

        // Act
        gameBoard = analyzer.assess(gameBoard, new BigDecimal("100"));

        //Assert
        assertEquals(expected, gameBoard.reward().intValue());
        assertEquals(gameBoard.winningCombinations().size(), 1);
        assertEquals(gameBoard.winningCombinations().get("B").size(), 2);
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbol_3_times"));
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbols_diagonally_left_to_right"));
    }

    @Test
    void assess_passTwoSymbolsMatchAndMissReward_validateCombinationsAndReward() {

        // Assign
        int expected = 55000;
        String[][] matrix = new String[][]{
                {"D", "C", "B"},
                {"C", "B", "MISS"},
                {"C", "D", "B"}
        };

        BoardAssessor analyzer = new BoardAssessor(config);
        ScratchBoard gameBoard = new ScratchBoard(
                matrix,
                ONE,
                null,
                "MISS"
        );

        // Act
        gameBoard = analyzer.assess(gameBoard, new BigDecimal("100"));

        //Assert
        assertEquals(expected, gameBoard.reward().intValue());
        assertEquals(gameBoard.winningCombinations().size(), 2);
        assertEquals(gameBoard.winningCombinations().get("B").size(), 1);
        assertEquals(gameBoard.winningCombinations().get("C").size(), 1);
        assertTrue(gameBoard.winningCombinations().get("B").contains("same_symbol_3_times"));
        assertTrue(gameBoard.winningCombinations().get("C").contains("same_symbol_3_times"));
        assertEquals("MISS", gameBoard.bonusSymbol());
    }

    @Test
    void applyBonus_passDifferentValues_validateImpactType() {
        assertEquals(config.symbols().get("5x").impact(), ImpactType.MULTIPLY_REWARD);
        assertEquals(config.symbols().get("+500").impact(), ImpactType.EXTRA_BONUS);
        assertEquals(config.symbols().get("MISS").impact(), ImpactType.MISS);
    }
}
