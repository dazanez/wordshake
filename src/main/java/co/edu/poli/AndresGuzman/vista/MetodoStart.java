package co.edu.poli.AndresGuzman.vista;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MetodoStart extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox();
		root.getStyleClass().add("fondo");
		
		Scene escena = new Scene(root, 500,500);
		
		Label texto = new Label("Hola Mundo!!");
		texto.getStyleClass().add("label");
		
		Button boton1 = new Button("Hola Mundo Boton!");
		boton1.getStyleClass().add("boton");
		
		
		root.getChildren().add(texto);
		root.getChildren().add(boton1);
		escena.getStylesheets().add(getClass().getResource("/co/edu/poli/AndresGuzman/estilos.css").toExternalForm());
		primaryStage.setTitle("Ejemplo de Modificacion :)");
		primaryStage.setScene(escena);
		primaryStage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
