package cyberspeed.task.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InputsTest {

    @Test
    public void initialize_passCorrectValues_ValidateResult() {
        String[] config = new String[4];

        config[0] = "--config";
        config[1] = "./docs/config.json";
        config[2] = "--betting-amount";
        config[3] = "100";

        Inputs inputs = new Inputs();
        inputs.initialize(config);

        assertNotNull(inputs.path());
        assertNotNull(inputs.bettingAmount());
        assertEquals(inputs.path(), "./docs/config.json");
        assertEquals(0, inputs.bettingAmount().compareTo(BigDecimal.valueOf(100)));
    }

    @Test
    public void initialize_passInCorrectValues_failsValidation() {

        // Assign
        String[] config = new String[2];
        config[0] = "--config";
        config[1] = "--betting-amount";

        Inputs inputs = new Inputs();

        // Act
        boolean result = inputs.initialize(config);

        // Assert
        assertFalse(result);
        assertNull(inputs.path());
        assertNull(inputs.bettingAmount());
    }

    @Test
    public void validate_passCorrectValues_shouldReturnTrue() {

        // Assign
        String[] config = new String[4];
        config[0] = "--config";
        config[1] = "./docs/config.json";
        config[2] = "--betting-amount";
        config[3] = "100";

        Inputs inputs = new Inputs();

        // Act
        inputs.initialize(config);
        boolean validation = inputs.validate();

        // Assert
        assertTrue(validation);
        assertNotNull(inputs.path());
        assertNotNull(inputs.bettingAmount());
    }

    @Test
    public void validate_passNullValues_shouldReturnFalse() {

        // Assign
        String[] config = new String[2];
        config[0] = "--config";
        config[1] = "--betting-amount";

        Inputs inputs = new Inputs();

        // Act
        inputs.initialize(config);
        boolean validation = inputs.validate();

        // Assert
        assertFalse(validation);
        assertNull(inputs.path());
        assertNull(inputs.bettingAmount());
    }



}
