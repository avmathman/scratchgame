package cyberspeed.task.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author abdurashid.vakhobov
 */
public class Config {

    private int _columns;

    private int _rows;

    private Map<String, Symbol> _symbols;

    private Probabilities _probabilities;

    private Map<String, WinCombination> _winCombinations;

    public Config(
            @JsonProperty("columns")
            int columns,
            @JsonProperty("rows")
            int rows,
            @JsonProperty("symbols")
            Map<String, Symbol> symbols,
            @JsonProperty("probabilities")
            Probabilities probabilities,
            @JsonProperty("win_combinations")
            Map<String, WinCombination> winCombinations
    ) {
        this._columns = columns;
        this._rows = rows;
        this._symbols = symbols;
        this._probabilities = probabilities;
        this._winCombinations = winCombinations;
    }

    public int columns() {
        return _columns;
    }

    public void columns(int _columns) {
        this._columns = _columns;
    }

    public int rows() {
        return _rows;
    }

    public void rows(int _rows) {
        this._rows = _rows;
    }

    public Map<String, Symbol> symbols() {
        return _symbols;
    }

    public void symbols(Map<String, Symbol> _symbols) {
        this._symbols = _symbols;
    }

    public Probabilities probabilities() {
        return _probabilities;
    }

    public void probabilities(Probabilities _probabilities) {
        this._probabilities = _probabilities;
    }

    public Map<String, WinCombination> winCombinations() {
        return _winCombinations;
    }

    public void winCombinations(Map<String, WinCombination> _winCombinations) {
        this._winCombinations = _winCombinations;
    }
}
