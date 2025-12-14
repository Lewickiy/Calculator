module ru.levitsky.calculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;

    opens ru.levitsky.calculator to javafx.fxml;
    exports ru.levitsky.calculator;
    exports ru.levitsky.calculator.constants;
    opens ru.levitsky.calculator.constants to javafx.fxml;
}