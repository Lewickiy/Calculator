package ru.levitsky.calculator.util;

import javafx.scene.paint.Color;

import static ru.levitsky.calculator.constants.CalculatorConstants.ADDITION_OPERATOR;
import static ru.levitsky.calculator.constants.CalculatorConstants.CLEAR_FUNCTION;
import static ru.levitsky.calculator.constants.CalculatorConstants.DIVISION_OPERATOR;
import static ru.levitsky.calculator.constants.CalculatorConstants.DOT_FUNCTION;
import static ru.levitsky.calculator.constants.CalculatorConstants.EQUALS_OPERATOR;
import static ru.levitsky.calculator.constants.CalculatorConstants.MULTIPLICATION_OPERATOR;
import static ru.levitsky.calculator.constants.CalculatorConstants.PERCENT_FUNCTION;
import static ru.levitsky.calculator.constants.CalculatorConstants.SUBTRACTION_OPERATOR;
import static ru.levitsky.calculator.constants.CalculatorConstants.TOGGLE_SIGN_FUNCTION;

public class Utils {
    public static String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public static boolean isOperator(String label) {
        return label.equals(ADDITION_OPERATOR) ||
                label.equals(SUBTRACTION_OPERATOR) ||
                label.equals(MULTIPLICATION_OPERATOR) ||
                label.equals(DIVISION_OPERATOR) ||
                label.equals(EQUALS_OPERATOR);
    }

    public static boolean isFunction(String label) {
        return label.equals(CLEAR_FUNCTION) ||
                label.equals(TOGGLE_SIGN_FUNCTION) ||
                label.equals(PERCENT_FUNCTION) ||
                label.equals(DOT_FUNCTION);
    }
}
