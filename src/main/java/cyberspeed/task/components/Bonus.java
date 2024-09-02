package cyberspeed.task.components;

import cyberspeed.task.configs.Cell;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author abdurashid.vakhobov
 */
public class Bonus {

    private final NavigableMap<Double, String> map = new TreeMap<>();
    private final Random random = new Random();
    private double total = 0;

    public Bonus(
            Cell cell
    ){
        cell.symbols().forEach((symbol, weight) -> add(weight, symbol));
    }

    private void add(double weight, String symbol) {
        if (weight > 0) {
            total += weight;
            map.put(total, symbol);
        }
    }

    public String next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
