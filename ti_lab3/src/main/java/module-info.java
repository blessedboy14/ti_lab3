module com.example.ti_lab3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ti_lab3 to javafx.fxml;
    exports com.example.ti_lab3;
}