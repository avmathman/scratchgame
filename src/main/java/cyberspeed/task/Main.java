package cyberspeed.task;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import cyberspeed.task.components.ScratchBoard;
import cyberspeed.task.components.Scratch;
import cyberspeed.task.configs.Config;
import cyberspeed.task.utils.Inputs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String[] temp = new String[4];

        temp[0] = "--config";
        temp[1] = "./docs/a/config.json";
        temp[2] = "--betting-amount";
        temp[3] = "100";

        Inputs inputs = new Inputs();

        inputs.initialize(temp);

        ObjectMapper mapper = JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();

        try {
            Config config = mapper.readValue(new File(inputs.getPath()), Config.class);
            config.setRows(config.getRows() - 1);
            config.setColumns(config.getColumns() - 1);

            ScratchBoard result = new Scratch(config).scratch(inputs.getBettingAmount());

            mapper.writerWithDefaultPrettyPrinter().writeValue(System.out, result);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}