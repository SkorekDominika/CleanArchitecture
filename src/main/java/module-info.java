module com.gra.recist {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gra.recist to javafx.fxml;
    exports com.gra.recist;
}