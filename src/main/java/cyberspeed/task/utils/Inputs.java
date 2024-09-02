package cyberspeed.task.utils;

import java.io.File;
import java.math.BigDecimal;

public class Inputs {

    private final String INPUT_CONFIG = "--config";
    private final String INPUT_BETTING_AMOUNT = "--betting-amount";
    private final String INPUT_HELP = "--help";

    private String _path;
    private BigDecimal _bettingAmount;

    public boolean initialize(String[] inputs) {
        try {
            for (int i = 0; i < inputs.length; i++) {
                String current = inputs[i];

                if (current.equals(INPUT_CONFIG) && !inputs[i+1].equals(INPUT_BETTING_AMOUNT) && !inputs[i+1].equals(INPUT_HELP)) {
                    _path = inputs[++i];
                } else if (current.equals(INPUT_BETTING_AMOUNT)) {
                    try {
                        _bettingAmount = new BigDecimal(inputs[++i]);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid betting amount: " + current);
                        return help();
                    }
                } else if (current.equals(INPUT_HELP)) {
                    return help();
                } else {
                    System.out.println("Invalid argument: " + current);
                    return help();
                }
            }

            if (!validate()) {
                return help();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid arguments");
            return help();
        }

        return true;
    }

    public boolean help() {
        System.out.println("Usage: java -jar scratch.jar [options]");
        System.out.println("Options:");
        System.out.println("  --config <path>            Path to config file");
        System.out.println("  --betting-amount <amount>  Betting amount");
        System.out.println("  --help                     Print this help");

        return false;
    }

    public boolean validate() {
        if (_path == null) {
            System.err.println("Invalid! Config path is not specified");
            return false;
        } else {
            if (!new File(_path).exists()) {
                System.err.println("Invalid! Config file does not exist: " + _path);
                return false;
            }
        }

        if (_bettingAmount == null || _bettingAmount.signum() <= 0) {
            System.err.println("Invalid! Betting amount is not specified");
            return false;
        }

        return true;
    }

    public String path() {
        return _path;
    }

    public BigDecimal bettingAmount() {
        return _bettingAmount;
    }
}
