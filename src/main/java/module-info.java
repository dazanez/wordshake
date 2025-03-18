module co.edu.poli.AndresGuzman {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
    requires java.sql;

    opens co.edu.poli.AndresGuzman.vista to javafx.fxml;
    opens co.edu.poli.AndresGuzman.controlador to javafx.fxml;
    exports co.edu.poli.AndresGuzman.controlador;
    exports co.edu.poli.AndresGuzman.vista;
}
