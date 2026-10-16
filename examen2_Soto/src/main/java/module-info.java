module co.edu.poli.Parcial_2_Practica {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires io.github.cdimascio.dotenv.java;

    opens co.edu.poli.Parcial_2_Practica.vista to javafx.fxml, javafx.graphics;
    opens co.edu.poli.Parcial_2_Practica.controlador to javafx.fxml;
    exports co.edu.poli.Parcial_2_Practica.controlador;
    
}
