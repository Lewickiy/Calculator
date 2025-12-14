package ru.levitsky.calculator.constants;

import javafx.scene.paint.Color;

import static ru.levitsky.calculator.constants.UIConstants.DISPLAY_BG;
import static ru.levitsky.calculator.constants.UIConstants.DISPLAY_TEXT;
import static ru.levitsky.calculator.util.Utils.toHex;

public final class UIStyleConstants {

    public static final String FOCUSED_BUTTON_POSTFIX = " -fx-effect: dropshadow(three-pass-box, rgba(255,255,255,0.3), 10, 0, 0, 0);";
    public static final String ERROR_MESSAGE = "Error";

    public static final int BUTTON_GRID_PADDING = 10;
    public static final int DISPLAY_FONT_SIZE = 36;
    public static final int BUTTON_FONT_SIZE = 24;
    public static final int BUTTON_GRID_GAP = 15;


    public static final String DISPLAY_FIELD_STYLE = "-fx-background-color: " + toHex(DISPLAY_BG) + "; " +
            "-fx-text-fill: " + toHex(DISPLAY_TEXT) + "; " +
            "-fx-border-color: #555555; " +
            "-fx-border-width: 2px; " +
            "-fx-border-radius: 10px; " +
            "-fx-background-radius: 10px; " +
            "-fx-padding: 10px;";

    public static String setButtonStyle(Color color) {
        return "-fx-background-color: " + toHex(color) + "; " +
                "-fx-text-fill: white; " +
                "-fx-border-radius: 35px; " +
                "-fx-background-radius: 35px; " +
                "-fx-cursor: hand;";
    }
}
