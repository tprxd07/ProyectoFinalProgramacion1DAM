module org.example.proyectofinalprogramacion1dam {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.proyectofinalprogramacion1dam to javafx.fxml;
    exports org.example.proyectofinalprogramacion1dam;
}