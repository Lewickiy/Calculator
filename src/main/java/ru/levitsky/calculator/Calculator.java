package ru.levitsky.calculator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static ru.levitsky.calculator.constants.CalculatorConstants.ADDITION_OPERATOR;
import static ru.levitsky.calculator.constants.CalculatorConstants.CLEAR_FUNCTION;
import static ru.levitsky.calculator.constants.CalculatorConstants.DIVISION_OPERATOR;
import static ru.levitsky.calculator.constants.CalculatorConstants.DOT2_FUNCTION;
import static ru.levitsky.calculator.constants.CalculatorConstants.DOT_FUNCTION;
import static ru.levitsky.calculator.constants.CalculatorConstants.EMPTY_OPERATOR;
import static ru.levitsky.calculator.constants.CalculatorConstants.EQUALS_OPERATOR;
import static ru.levitsky.calculator.constants.CalculatorConstants.MULTIPLICATION_OPERATOR;
import static ru.levitsky.calculator.constants.CalculatorConstants.PERCENT_FUNCTION;
import static ru.levitsky.calculator.constants.CalculatorConstants.SUBTRACTION_OPERATOR;
import static ru.levitsky.calculator.constants.CalculatorConstants.TOGGLE_SIGN_FUNCTION;
import static ru.levitsky.calculator.constants.CalculatorConstants.HISTORY_FUNCTION; // Кнопка истории
import static ru.levitsky.calculator.constants.UIConstants.BG_COLOR;
import static ru.levitsky.calculator.constants.UIConstants.FUNCTION_BG;
import static ru.levitsky.calculator.constants.UIConstants.NUMBER_BG;
import static ru.levitsky.calculator.constants.UIConstants.OPERATOR_BG;
import static ru.levitsky.calculator.constants.UIConstants.SYSTEM_FONT;
import static ru.levitsky.calculator.constants.UIConstants.TITLE;
import static ru.levitsky.calculator.constants.UIStyleConstants.BUTTON_FONT_SIZE;
import static ru.levitsky.calculator.constants.UIStyleConstants.BUTTON_GRID_GAP;
import static ru.levitsky.calculator.constants.UIStyleConstants.BUTTON_GRID_PADDING;
import static ru.levitsky.calculator.constants.UIStyleConstants.DISPLAY_FIELD_STYLE;
import static ru.levitsky.calculator.constants.UIStyleConstants.DISPLAY_FONT_SIZE;
import static ru.levitsky.calculator.constants.UIStyleConstants.ERROR_MESSAGE;
import static ru.levitsky.calculator.constants.UIStyleConstants.FOCUSED_BUTTON_POSTFIX;
import static ru.levitsky.calculator.constants.UIStyleConstants.setButtonStyle;
import static ru.levitsky.calculator.util.Utils.isFunction;
import static ru.levitsky.calculator.util.Utils.isOperator;

public class Calculator extends Application {

    private TextField display;

    private String operator = EMPTY_OPERATOR;

    private boolean calculationDone = false;
    private boolean startNewNumber = true;

    private double num1 = 0, num2 = 0;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        display = createDisplay();

        GridPane buttonGrid = createButtonGrid();

        root.getChildren().addAll(display, buttonGrid);

        Scene scene = new Scene(root, 400, 600);
        scene.setFill(BG_COLOR);

        setupSimpleKeyboardSupport(root);

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        root.setFocusTraversable(true);

        primaryStage.show();

        root.requestFocus();
    }

    private TextField createDisplay() {
        TextField displayField = new TextField("0");
        displayField.setEditable(false);
        displayField.setFont(Font.font(SYSTEM_FONT, FontWeight.BOLD, DISPLAY_FONT_SIZE));
        displayField.setStyle(DISPLAY_FIELD_STYLE);
        displayField.setPrefHeight(100);
        displayField.setAlignment(Pos.CENTER_RIGHT);
        return displayField;
    }

    private GridPane createButtonGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(BUTTON_GRID_GAP);
        grid.setVgap(BUTTON_GRID_GAP);
        grid.setPadding(new Insets(BUTTON_GRID_PADDING));

        String[][] buttonLabels = { // Изменил расположение кнопки сместив крайний правый ряд вниз
                {CLEAR_FUNCTION, TOGGLE_SIGN_FUNCTION, PERCENT_FUNCTION, HISTORY_FUNCTION},
                {"7", "8", "9", DIVISION_OPERATOR},
                {"4", "5", "6", MULTIPLICATION_OPERATOR},
                {"1", "2", "3", SUBTRACTION_OPERATOR},
                {"0", DOT_FUNCTION, EQUALS_OPERATOR, ADDITION_OPERATOR}
        };

        for (int row = 0; row < buttonLabels.length; row++) {
            for (int col = 0; col < buttonLabels[row].length; col++) {
                String label = buttonLabels[row][col];
                Button button = createButton(label);

                if (label.equals("0")) {
                    grid.add(button, col, row, 2, 1);
                } else {
                    grid.add(button, col, row);
                }
            }
        }

        return grid;
    }

    private Button createButton(String label) {
        Button button = new Button(label);
        button.setFont(Font.font(SYSTEM_FONT, FontWeight.BOLD, BUTTON_FONT_SIZE));
        button.setPrefSize(80, 70);
        button.setFocusTraversable(false);

        Color buttonColor;
        if (label.equals(HISTORY_FUNCTION)) {
            buttonColor = OPERATOR_BG; // Добавил цвет кнопке взяв из UIConstants оранжевый цвет
        } else if (isOperator(label)) {
            buttonColor = OPERATOR_BG;
        } else if (isFunction(label)) {
            buttonColor = FUNCTION_BG;
        } else {
            buttonColor = NUMBER_BG;
        }

        String buttonStyle = setButtonStyle(buttonColor);

        button.setStyle(buttonStyle);

        button.setOnMouseEntered(_ -> button.setStyle(buttonStyle + FOCUSED_BUTTON_POSTFIX));

        button.setOnMouseExited(_ -> button.setStyle(buttonStyle));

        button.setOnAction(_ -> {
            handleButtonClick(label);
            button.getParent().getParent().requestFocus();
        });

        return button;
    }

    private void setupSimpleKeyboardSupport(VBox root) {
        root.addEventFilter(KeyEvent.KEY_TYPED, this::handleKeyTyped);
    }

    private void handleKeyTyped(KeyEvent event) {
        String character = event.getCharacter();

        if (character.isEmpty()) {
            return;
        }

        switch (character) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9":
                handleButtonClick(character);
                break;

            case ADDITION_OPERATOR:
                handleButtonClick(ADDITION_OPERATOR);
                break;

            case SUBTRACTION_OPERATOR:
                handleButtonClick(SUBTRACTION_OPERATOR);
                break;

            case MULTIPLICATION_OPERATOR:
                handleButtonClick(MULTIPLICATION_OPERATOR);
                break;

            case DIVISION_OPERATOR:
                handleButtonClick(DIVISION_OPERATOR);
                break;

            case EQUALS_OPERATOR, "\r", "\n":
                handleButtonClick(EQUALS_OPERATOR);
                break;

            case DOT_FUNCTION, DOT2_FUNCTION:
                handleButtonClick(DOT_FUNCTION);
                break;

            case "\u001b":
                handleButtonClick(CLEAR_FUNCTION);
                break;

            case "\b":
                handleBackspace();
                break;
        }

        event.consume();
    }

    private void handleSpecialKeys(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.SHIFT || code == KeyCode.CONTROL || code == KeyCode.ALT) {
            return;
        }

        switch (code) {
            case ENTER:
                handleButtonClick(EQUALS_OPERATOR);
                event.consume();
                break;

            case ESCAPE:
            case DELETE:
                handleButtonClick(CLEAR_FUNCTION);
                event.consume();
                break;

            case BACK_SPACE:
                handleBackspace();
                event.consume();
                break;

            case ADD:
                handleButtonClick(ADDITION_OPERATOR);
                event.consume();
                break;

            case SUBTRACT:
                handleButtonClick(SUBTRACTION_OPERATOR);
                event.consume();
                break;

            case MULTIPLY:
                handleButtonClick(MULTIPLICATION_OPERATOR);
                event.consume();
                break;

            case DIVIDE:
                handleButtonClick(DIVISION_OPERATOR);
                event.consume();
                break;

            case DECIMAL:
                handleButtonClick(DOT_FUNCTION);
                event.consume();
                break;

            case NUMPAD0:
            case NUMPAD1:
            case NUMPAD2:
            case NUMPAD3:
            case NUMPAD4:
            case NUMPAD5:
            case NUMPAD6:
            case NUMPAD7:
            case NUMPAD8:
            case NUMPAD9:
                String digit = code.toString().replace("NUMPAD", EMPTY_OPERATOR);
                handleButtonClick(digit);
                event.consume();
                break;
        }
    }

    private void handleBackspace() {
        String currentText = display.getText();
        if (currentText.length() > 1 && !currentText.equals("0.")) {
            display.setText(currentText.substring(0, currentText.length() - 1));
        } else {
            display.setText("0");
            startNewNumber = true;
        }
    }

    private void handleButtonClick(String label) {
        if (calculationDone &&
                !isOperator(label) &&
                !label.equals(EQUALS_OPERATOR) &&
                !label.equals(CLEAR_FUNCTION)) {
            display.setText("0");
            calculationDone = false;
        }

        switch (label) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9":
                handleNumber(label);
                break;

            case DOT_FUNCTION:
                handleDecimal();
                break;

            case HISTORY_FUNCTION: // Добавил фукнцию кнопки (вывод "Not used")
                display.setText("Not used");
                startNewNumber = true;
                calculationDone = false;
                break;

            case ADDITION_OPERATOR, SUBTRACTION_OPERATOR, MULTIPLICATION_OPERATOR, DIVISION_OPERATOR:
                handleOperator(label);
                break;

            case EQUALS_OPERATOR:
                handleEquals();
                break;

            case CLEAR_FUNCTION:
                handleClear();
                break;

            case TOGGLE_SIGN_FUNCTION:
                handlePlusMinus();
                break;

            case PERCENT_FUNCTION:
                handlePercent();
                break;
        }
    }

    private void handleNumber(String number) {
        String currentText = display.getText();

        if (startNewNumber || currentText.equals("0") || calculationDone) {
            display.setText(number);
            startNewNumber = false;
            calculationDone = false;
        } else {
            display.setText(currentText + number);
        }
    }

    private void handleDecimal() {
        String currentText = display.getText();
        if (startNewNumber) {
            display.setText("0.");
            startNewNumber = false;
        } else if (!currentText.contains(DOT_FUNCTION)) {
            display.setText(currentText + DOT_FUNCTION);
        }
        calculationDone = false;
    }

    private void handleOperator(String op) {
        try {
            num1 = Double.parseDouble(display.getText());
            operator = op;
            startNewNumber = true;
            calculationDone = false;
        } catch (NumberFormatException e) {
            display.setText(ERROR_MESSAGE);
            resetCalculator();
        }
    }

    private void handleEquals() {
        if (operator.isEmpty()) return;

        try {
            num2 = Double.parseDouble(display.getText());
            double result = calculate(num1, num2, operator);

            if (result == (long) result) {
                display.setText(String.valueOf((long) result));
            } else {
                String formatted = String.format("%.12f", result);
                formatted = formatted.replaceAll("0*$", EMPTY_OPERATOR).replaceAll("\\.$", EMPTY_OPERATOR);
                display.setText(formatted);
            }

            num1 = result;
            startNewNumber = true;
            calculationDone = true;
            operator = EMPTY_OPERATOR;

        } catch (NumberFormatException | ArithmeticException e) {
            display.setText(ERROR_MESSAGE);
            resetCalculator();
        }
    }

    private double calculate(double a, double b, String op) {
        return switch (op) {
            case ADDITION_OPERATOR -> a + b;
            case SUBTRACTION_OPERATOR -> a - b;
            case MULTIPLICATION_OPERATOR -> a * b;
            case DIVISION_OPERATOR -> {
                if (b == 0) throw new ArithmeticException("Division by zero is not allowed");
                yield a / b;
            }
            default -> b;
        };
    }

    private void handleClear() {
        display.setText("0");
        resetCalculator();
    }

    private void handlePlusMinus() {
        String currentText = display.getText();
        if (!currentText.equals("0")) {
            if (currentText.startsWith(SUBTRACTION_OPERATOR)) {
                display.setText(currentText.substring(1));
            } else {
                display.setText(SUBTRACTION_OPERATOR + currentText);
            }
        }
    }

    private void handlePercent() {
        try {
            double value = Double.parseDouble(display.getText());
            display.setText(String.valueOf(value / 100));
            startNewNumber = true;
        } catch (NumberFormatException e) {
            display.setText(ERROR_MESSAGE);
        }
    }

    private void resetCalculator() {
        num1 = 0;
        num2 = 0;
        operator = EMPTY_OPERATOR;
        startNewNumber = true;
        calculationDone = false;
    }
}