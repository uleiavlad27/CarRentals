module com.example.lab4 {
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
    requires jdk.compiler;
    requires eu.hansolo.fx.countries;
    requires eu.hansolo.fx.heatmap;
    requires eu.hansolo.toolbox;
    requires eu.hansolo.toolboxfx;
    requires org.junit.jupiter.api;
    requires javafx.swing;
    requires org.xerial.sqlitejdbc;

    opens com.example.lab4 to javafx.fxml;
    exports com.example.lab4;
}