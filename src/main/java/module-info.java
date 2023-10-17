module com.example.final_dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires voicerss.tts;
    requires java.desktop;
    requires freetts;

    opens com.example.final_dictionary to javafx.fxml;
    exports com.example.final_dictionary;
}