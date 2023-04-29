package com.example.ti_lab3;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class HelloController {
    private int yValue;
    private int gValue;
    private int pValue;
    private int xValue;
    private int kValue;
    private int aValue;
    private int blockSize = 1;
    private int[] aValueExtended;
    private int[] bValue;
    private int[] inputSequence;
    private int[] resultSequence;
    private int inputLength;
    private String inputFilePath;
    @FXML
    private TextField kInput;
    @FXML
    private TextField xInput;
    @FXML
    private ComboBox primitiveRoot;
    @FXML
    private TextField pInput;
    @FXML
    private TextArea plainText;
    @FXML
    private TextArea AValue;
    @FXML
    private TextArea BValue;
    private void convertBytes(byte[] bytes) {
        inputSequence = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            inputSequence[i] = bytes[i] & 0xFF;
        }
    }
    @FXML
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открыть");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            inputFilePath = file.getPath();
            byte[] inputBytes = new byte[(int) file.length()];
            inputLength = inputBytes.length;
            try (FileInputStream fileInputStream = new FileInputStream(file.getPath())) {
                fileInputStream.read(inputBytes, 0, inputBytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            convertBytes(inputBytes);
            String inputString = Arrays.toString(inputSequence).substring(1,Arrays.toString(inputSequence).length()-1);
            inputString = inputString.replace("," ," ");
            plainText.setText(inputString.length() < 5000 ? inputString : inputString.substring(0, 5000));
        }
    }
    private boolean isInputCorrect(String input, String validRegEx, int maxValue, int minValue, boolean forPrime) {
        Pattern pattern = Pattern.compile(validRegEx);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            int intVal;
            try {
               intVal = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                return false;
            }
            return Integer.parseInt(input) < maxValue
                    && Integer.parseInt(input) > minValue && (OperationBox.isLittleNumberPrime(intVal) || !forPrime);
        }
        return false;
    }
    @FXML
    private void initialize () {
//        OperationBox.gcdExtended(479, 811);
        xInput.setDisable(true);
        kInput.setDisable(true);
    }
    private boolean isKCorrect (String text, String validRegEx, int maxValue) {
        Pattern pattern = Pattern.compile(validRegEx);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            int intVal;
            try {
                intVal = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return false;
            }
            return Integer.parseInt(text) < maxValue
                    && Integer.parseInt(text) > 1 && OperationBox.isPrimeWith(intVal, maxValue);
        }
        return false;
    }
    @FXML
    private void filerKInput(KeyEvent ev) {
        if (kInput.getText().length() > 0) {
            if (!isKCorrect(kInput.getText(), "\\d+",
                    Integer.parseInt(pInput.getText())-1)) {
                kInput.setStyle("-fx-text-box-border: #d60616; -fx-focus-color: #d60616;");
            } else {
                kInput.setStyle("-fx-text-box-border: ladder(\n" +
                        "        -fx-background,\n" +
                        "        black 10%,\n" +
                        "        derive(-fx-background, -15%) 30%\n" +
                        "    ); -fx-focus-color: #0093ff; -fx-border-width: 1px;");
            }
        } else {
            kInput.setStyle("-fx-text-box-border: ladder(\n" +
                    "        -fx-background,\n" +
                    "        black 10%,\n" +
                    "        derive(-fx-background, -15%) 30%\n" +
                    "    ); -fx-focus-color: #0093ff; -fx-border-width: 1px;");
        }
    }
    @FXML
    private void filerXInput(KeyEvent ev) {
        if (xInput.getText().length() > 0) {
            if (!isInputCorrect(xInput.getText(), "\\d+",
                    Integer.parseInt(pInput.getText())-1, 1, false)) {
                xInput.setStyle("-fx-text-box-border: #d60616; -fx-focus-color: #d60616;");
            } else {
                xInput.setStyle("-fx-text-box-border: ladder(\n" +
                        "        -fx-background,\n" +
                        "        black 10%,\n" +
                        "        derive(-fx-background, -15%) 30%\n" +
                        "    ); -fx-focus-color: #0093ff; -fx-border-width: 1px;");
            }
        } else {
            xInput.setStyle("-fx-text-box-border: ladder(\n" +
                    "        -fx-background,\n" +
                    "        black 10%,\n" +
                    "        derive(-fx-background, -15%) 30%\n" +
                    "    ); -fx-focus-color: #0093ff; -fx-border-width: 1px;");
        }
    }
    @FXML
    private void filterPInput(KeyEvent ev) {
        if (pInput.getText().length() > 0) {
            primitiveRoot.getItems().clear();
            if (!isInputCorrect(pInput.getText(), "\\d+", 100000, 256, true)) {
                pInput.setStyle("-fx-text-box-border: #d60616; -fx-focus-color: #d60616;");
            } else {
                pInput.setStyle("-fx-text-box-border: ladder(\n" +
                        "        -fx-background,\n" +
                        "        black 10%,\n" +
                        "        derive(-fx-background, -15%) 30%\n" +
                        "    ); -fx-focus-color: #0093ff; -fx-border-width: 1px;");
                primitiveRoot.getItems().addAll(OperationBox.findPrimitiveRoot(Integer.parseInt(pInput.getText())));
                System.out.println(primitiveRoot.getItems().size());
            }
        } else {
            pInput.setStyle("-fx-text-box-border: ladder(\n" +
                    "        -fx-background,\n" +
                    "        black 10%,\n" +
                    "        derive(-fx-background, -15%) 30%\n" +
                    "    ); -fx-focus-color: #0093ff; -fx-border-width: 1px;");
        }
    }
    @FXML
    private void rulesPrint() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Stage.getWindows().stream().filter(Window::isShowing).toList().get(0));
        alert.setTitle("Info");
        alert.setHeaderText("Input rules");
        alert.setContentText("""
                1. P is prime number and no bigger than 2 147 483 647 and no less than 3.
                2. G is autogenerated after P input, you just need to choose 1 variant.
                3. X is natural number less than P-1 and != 1.
                4. K is random natural number in range (1, P-1) and mutually simple with P-1.""");
        alert.showAndWait();
    }
    @FXML
    private void primitiveRootFilter() {
        if (primitiveRoot.getSelectionModel().getSelectedIndex() > -1) {
            xInput.setDisable(false);
            kInput.setDisable(false);
        } else {
            xInput.setText("");
            xInput.setDisable(true);
            kInput.setText("");
            kInput.setDisable(true);
        }
    }
    private boolean fieldFill() {
        return isInputCorrect(xInput.getText(), "\\d+", Integer.parseInt(pInput.getText())-1, 1, false)
                && isInputCorrect(pInput.getText(), "\\d+", Integer.MAX_VALUE, 2, true)
                && isKCorrect(kInput.getText(), "\\d+", Integer.parseInt(pInput.getText())-1)
                && primitiveRoot.getSelectionModel().getSelectedIndex() > -1
                && plainText.getText().length() > 0
                && inputFilePath != null;
    }
    private void setValuesFromInput(){
        this.pValue = Integer.parseInt(pInput.getText());
        this.gValue = (int) primitiveRoot.getSelectionModel().getSelectedItem();
        this.kValue = Integer.parseInt(kInput.getText());
        this.xValue = Integer.parseInt(xInput.getText());
    }
    private void writeDataToFile(int[] aValueExtended, int[] bValue) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(inputFilePath)) {
            for (int i = 0; i < bValue.length; i++) {
                for (int j = blockSize - 1; j > -1; j--) {
                    fileOutputStream.write((byte) (aValueExtended[i] >> (8 * j)));
                }
                for (int j = blockSize - 1; j > -1; j--) {
                    fileOutputStream.write((byte) (bValue[i] >> (8 * j)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeToFile() {
        try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\USER\\Desktop\\f.txt")) {
            for (int i = 0; i < 256; i++) {
                fileOutputStream.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onCypherClick() {
        if (fieldFill()) {
            setValuesFromInput();
            blockSize = calculateBlockSize(this.pValue);
            this.yValue = (int) OperationBox.fastExp(this.gValue, this.xValue, this.pValue);
            this.aValue = (int) OperationBox.fastExp(this.gValue, this.kValue, this.pValue);
            this.bValue = OperationBox.computeBValue(this.inputSequence, this.yValue, this.kValue, this.pValue);
            System.out.println("Открытый ключ - (p = " + pValue + ", g = " + gValue + ", y = " + yValue + ")");
            System.out.println("Закрытый ключ - х = " + xValue);
            aValueExtended = new int[this.inputLength];
            Arrays.fill(aValueExtended, aValue);
            String aValueStr = Arrays.toString(this.aValueExtended).substring(1,Arrays.toString(this.aValueExtended).length()-1).replace("," ," ");
            AValue.setText(aValueStr.length() < 5000 ? aValueStr : aValueStr.substring(0, 5000));
            String bValueStr = Arrays.toString(this.bValue).substring(1,Arrays.toString(this.bValue).length()-1).replace("," ," ");
            BValue.setText(bValueStr.length() < 5000 ? bValueStr : bValueStr.substring(0, 5000));
            writeDataToFile(aValueExtended, bValue);
        } else {
            return;
        }
    }
    private boolean getAAndBFromInput() {
        if (inputSequence.length % 2 != 0 || inputSequence.length % blockSize != 0) {
            return false;
        }
        int value = 0;
        for (int i = blockSize - 1; i > -1; i--) {
            value += (inputSequence[blockSize - 1 - i] & 0xff) << (8 * i);
        }
        this.aValue = value;
        this.bValue = new int[inputSequence.length/2/blockSize];
        for (int i = 0; i < inputSequence.length; i+=2*blockSize) {
            value = 0;
            byte iter = 0;
            for (int j = blockSize - 1; j > -1; j--) {
                value += (inputSequence[i+iter] & 0xff) << (8 * j);
                iter++;
            }
            if (this.aValue != value) {
                return false;
            }
            value = 0;
            for (int j = blockSize - 1; j > -1; j--) {
                value += (inputSequence[i+iter] & 0xff) << (8 * j);
                iter++;
            }
            this.bValue[i/2/blockSize] = value;
        }
        return true;
    }
    private void writeBackToFile(int[] result) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(inputFilePath)) {
            for (int k : result) {
                fileOutputStream.write(k);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onDecipherClick() {
        if (fieldFill()) {
            setValuesFromInput();
            blockSize = calculateBlockSize(this.pValue);
            boolean isAllOk = getAAndBFromInput();
            if (!isAllOk) {
                System.out.println("Ошибка ввода данных");
                return;
            }
            System.out.println("\nДанные получены - a = " + aValue + ", b[0-2] = " + bValue[0] + ", " + bValue[1] + ", " + bValue[2]);
            this.resultSequence = OperationBox.decipherData(this.aValue, this.bValue, this.xValue, this.pValue);
            writeBackToFile(this.resultSequence);
            System.out.println("Расшифровано");
        }
    }
    private byte calculateBlockSize(int pValue) {
        byte size = 1;
        int temp = pValue >> 8;
        while (temp > 0) {
            size++;
            temp = temp >> 8;
        }
        return size;
    }
}