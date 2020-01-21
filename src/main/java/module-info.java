module com.laoiseearle {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;

    opens com.laoiseearle to javafx.fxml;
    exports com.laoiseearle;
}