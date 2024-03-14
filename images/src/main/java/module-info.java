module com.example.images {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.images to javafx.fxml;
    exports com.example.images;
}