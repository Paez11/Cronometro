module hilo.contador {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;
    requires java.xml.bind;
    requires java.sql;

    opens hilo.contador to javafx.fxml;
    opens hilo.contador.controller to javafx.fxml;
    opens hilo.contador.utils.Connection to java.xml.bind;
    exports hilo.contador;
    exports hilo.contador.controller;
}