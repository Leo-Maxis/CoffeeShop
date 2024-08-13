module org.example.coffeeshop {
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
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires jasperreports;

    opens org.example.coffeeshop to javafx.fxml;
    exports org.example.coffeeshop;
    exports org.example.controller;
    opens org.example.controller to javafx.fxml;
    exports org.example.entity;
    opens org.example.entity to javafx.fxml;
}