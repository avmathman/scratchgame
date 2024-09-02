package cyberspeed.task.configs;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTest {

    @Test
    public void config_loadData_validateProperties() throws IOException {

        // Assign
        ObjectMapper mapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();

        // Act
        Config config = mapper.readValue(getClass().getClassLoader().getResource("config.json"), Config.class);
        config.rows(config.rows() - 1);
        config.columns(config.columns() - 1);

        // Assert
        assertNotNull(config);

        assertEquals(3, config.columns());
        assertEquals(3, config.rows());

        assertNotNull(config.symbols());
        assertFalse(config.symbols().isEmpty());
        assertNotNull(config.probabilities());
        assertNotNull(config.probabilities().standardSymbols());
        assertFalse(config.probabilities().standardSymbols().isEmpty());
        assertNotNull(config.probabilities().bonusSymbols());
        assertNotNull(config.winCombinations());
        assertFalse(config.winCombinations().isEmpty());
    }
}
