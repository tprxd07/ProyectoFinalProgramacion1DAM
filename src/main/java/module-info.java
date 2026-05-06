module org.example.proyectofinalprogramacion1dam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.xml.bind;


    opens org.example.proyectofinalprogramacion1dam to javafx.fxml;
    exports org.example.proyectofinalprogramacion1dam;
    exports org.example.proyectofinalprogramacion1dam.controller;
    opens org.example.proyectofinalprogramacion1dam.controller to javafx.fxml;
}