module HeadsHands {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.deadog to javafx.fxml;
    exports org.deadog;
    opens org.deadog.controllers to javafx.fxml;
    exports org.deadog.controllers;
}