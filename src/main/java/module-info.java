module com.example.final_dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.final_dictionary to javafx.fxml;
    exports com.example.final_dictionary;
}