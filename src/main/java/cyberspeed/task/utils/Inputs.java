package cyberspeed.task.utils;

import java.io.File;
import java.math.BigDecimal;

public class Inputs {

    private String _path;
    private BigDecimal _bettingAmount;

    public void initialize(String[] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            String current = inputs[i];

            if (current.equals("--config")) {
                _path = inputs[++i];
            } else if (current.equals("--betting-amount")) {
                try {
                    _bettingAmount = new BigDecimal(inputs[++i]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid betting amount: " + current);
                }
            } else if (current.equals("--help")) {
                help();
            } else {
                System.out.println("Invalid argument: " + current);
                help();
            }
        }

        if (!validate()) {
            help();
        }
    }

    public void help() {
        System.out.println("Usage: java -jar scratch.jar [options]");
        System.out.println("Options:");
        System.out.println("  --config <path>            Path to config file");
        System.out.println("  --betting-amount <amount>  Betting amount");
        System.out.println("  --help                     Print this help");
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
