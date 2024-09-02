package cyberspeed.task.configs;

import com.fasterxml.jackson.annotation.JsonCreator;

public record Coordinate(
        int row,
        int column
) {

    @JsonCreator
    public static Coordinate from(String s){
        String[] split = s.split(":");
        return new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }
}
