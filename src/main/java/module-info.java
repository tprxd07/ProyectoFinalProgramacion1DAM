module org.example.proyectofinalprogramacion1dam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;
    requires java.desktop;
    requires javafx.media;

    opens org.example.proyectofinalprogramacion1dam.dataAccess to java.xml.bind;
    opens org.example.proyectofinalprogramacion1dam to javafx.fxml;
    exports org.example.proyectofinalprogramacion1dam;
    exports org.example.proyectofinalprogramacion1dam.controller;
    opens org.example.proyectofinalprogramacion1dam.controller to javafx.fxml;
    exports org.example.proyectofinalprogramacion1dam.view;
    opens org.example.proyectofinalprogramacion1dam.view to javafx.fxml;
    exports org.example.proyectofinalprogramacion1dam.utils;
    opens org.example.proyectofinalprogramacion1dam.utils to javafx.fxml;
    exports org.example.proyectofinalprogramacion1dam.model;
    opens org.example.proyectofinalprogramacion1dam.model to javafx.fxml;
}