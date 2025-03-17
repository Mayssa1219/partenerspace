module com.example.innosynergy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.innosynergy to javafx.fxml;
    exports com.example.innosynergy;
    module com.example.clock {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;


    opens com.example.clock to javafx.fxml;
    exports com.example.clock;
}
}