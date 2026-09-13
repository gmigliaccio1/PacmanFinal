module org.pacmanfinal {
    requires javafx.controls;
    requires javafx.fxml;
    opens org.pacmanfinal.game to javafx.fxml;
    exports org.pacmanfinal.game;
    exports org.pacmanfinal.characters;
    exports org.pacmanfinal.map;
    exports org.pacmanfinal.gui;
    exports org.pacmanfinal.movement;
}