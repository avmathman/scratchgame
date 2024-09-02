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

    /**
     * Basic validation of config.
     * @return true if config is valid
     */
    boolean isValid() {
        return _columns > 0 && _rows > 0
                && !_symbols.isEmpty()
                && _probabilities != null
                && !_probabilities.standardSymbols().isEmpty()
                && _probabilities.standardSymbols().size() == _columns * _rows   //and we hope they cover all cells
                && !_winCombinations.isEmpty();
    }

    public int getColumns() {
        return _columns;
    }

    public void setColumns(int _columns) {
        this._columns = _columns;
    }

    public int getRows() {
        return _rows;
    }

    public void setRows(int _rows) {
        this._rows = _rows;
    }

    public Map<String, Symbol> getSymbols() {
        return _symbols;
    }

    public void setSymbols(Map<String, Symbol> _symbols) {
        this._symbols = _symbols;
    }

    public Probabilities getProbabilities() {
        return _probabilities;
    }

    public void setProbabilities(Probabilities _probabilities) {
        this._probabilities = _probabilities;
    }

    public Map<String, WinCombination> getWinCombinations() {
        return _winCombinations;
    }

    public void setWinCombinations(Map<String, WinCombination> _winCombinations) {
        this._winCombinations = _winCombinations;
    }
}
