# JavaFX Calculator

A modern calculator with a polished interface, written in JavaFX. It supports keyboard and mouse input, features a dark theme, and follows a professional architecture.

<div align="center">
  <figure>
    <img src="src/main/resources/ru/levitsky/calculator/calculator.png" 
         alt="JavaFX Calculator Interface" 
         width="400" 
         height="600"
         style="border: 1px solid #ddd; border-radius: 8px; padding: 5px;">
    <figcaption><em>Modern JavaFX calculator with a dark theme</em></figcaption>
  </figure>
</div>

---

## Features

### Functionality
- **Basic operations**: addition, subtraction, multiplication, division
- **Additional functions**: percentage (%), sign toggle (±), clear (C)
- **Keyboard control**: full support for keyboard and Numpad input
- **Error handling**: division by zero, invalid input
- **Correct rounding**: automatic removal of trailing zeros

### Design
- **Dark theme**: modern, minimalist design
- **Animations**: hover and press effects
- **Responsive layout**: fixed size of 400×600 px
- **Professional color scheme**: orange operators, gray digits

### Controls
- **Mouse**: click all buttons
- **Keyboard**: digits, Enter, Escape, Backspace
- **Numpad**: full numeric keypad support
- **Keyboard shortcuts**:
  - `Enter` or `=` — calculate
  - `Escape` or `Delete` — clear
  - `Backspace` — delete a character
  - `Shift + =` — plus sign

---

## Project Structure

```

calculator/
├── src/main/java/ru/levitsky/calculator/
│   ├── Calculator.java              # Main application class
│   ├── constants/
│   │   ├── CalculatorConstants.java # Mathematical constants
│   │   ├── UIConstants.java         # Colors and UI constants
│   │   └── UIStyleConstants.java    # Styles and dimensions
│   └── util/
│       └── Utils.java               # Helper utilities
├── pom.xml                          # Maven configuration
└── README.md                        # Documentation

````

---

## Quick Start

### Prerequisites
- Java 25 (JDK 25 or higher)
- JavaFX SDK 21.0.6 (automatically downloaded via Maven)
- Maven 3.8+

### Run from IDE (IntelliJ IDEA / Eclipse)
1. Clone the repository
2. Open the project in your IDE
3. Ensure JavaFX SDK is configured
4. Run `Calculator.java`

### Run from Command Line

#### Build with Maven:
```bash
mvn clean compile
mvn javafx:run
````

---

## Developer Setup

### Maven Configuration (`pom.xml`)

```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21</version>
    </dependency>
</dependencies>
```

---

## How to Use

### Basic Calculations

```
1. Enter the first number (e.g., 123.45)
2. Press an operator (+, -, ×, ÷)
3. Enter the second number (e.g., 67.89)
4. Press = or Enter to get the result
```

### Notes

* **Decimal point**: automatically added only once
* **Clear**: `C` resets all calculations
* **Sign toggle**: `±` inverts the sign of the current number
* **Percentage**: `%` divides the number by 100

---

## Testing

The calculator has been tested with the following scenarios:

* Basic arithmetic operations
* Division by zero (shows an error message)
* Decimal number handling
* Keyboard input
* Large number processing
* Correct rounding behavior

---

## Architectural Decisions

### Separation of Responsibilities

* **Calculator.java**: controller and view
* **Constants**: separate classes for UI and logic
* **Utilities**: reusable helper functions

### Event Handling

```java
// Two-level event handling system
addEventFilter(KeyEvent.KEY_TYPED, this::handleKeyTyped);       // Typed characters
addEventFilter(KeyEvent.KEY_PRESSED, this::handleSpecialKeys); // Special keys
```

### State Management

```java
private double num1, num2;          // Operands
private String operator;            // Current operation
private boolean startNewNumber;     // Start new number flag
private boolean calculationDone;    // Calculation completed flag
```

---

## Roadmap

### Version 2.0 (in progress)

* [ ] Calculation history
* [ ] Memory buttons (M+, M-, MR, MC)
* [ ] Ctrl+C / Ctrl+V copy & paste support

### Version 3.0 (planned)

* [ ] Scientific mode (sin, cos, sqrt, powers)
* [ ] Unit converter
* [ ] Light / dark theme toggle
* [ ] Expression support

---

## Contributing

Pull requests are welcome!
For major changes, please open an Issue first to discuss your ideas.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

---

## License

This project is licensed under the MIT License.
See the [LICENSE](LICENSE) file for details.

---

## Author

**Levitsky**

* GitHub: [Anatoliy Levitsky](https://github.com/Lewickiy)
* Project: [JavaFX Calculator](https://github.com/Lewickiy/calculator)
