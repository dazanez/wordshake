/**
 * The `App` class in Java extends `Application` to create a JavaFX application that loads an initial scene from an FXML file titled "WordShake".
 */
package co.edu.poli.AndresGuzman.vista;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

   /**
    * The start method loads a FXML file to create the initial scene for a JavaFX application titled "WordShake".
    * 
    * @param stage The `Stage` parameter represents the main window or container for your JavaFX application. It is typically the top-level container that holds all the visual elements of your application, such as scenes, layouts, and controls. In the provided code snippet, the `start` method is an overridden method from the
    */
    @Override
    public void start(Stage stage) throws IOException {
        Scene escena = new Scene(loadFXML("paginaInicio"));
        stage.setTitle("WordShake");
        stage.setScene(escena);
        stage.show();
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/co/edu/poli/AndresGuzman/"+fxml+ ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * This method changes the root of the scene to the loaded FXML file. It is 
     * useful for navigating between different scenes in the application.
     * 
     * @param fxml The name of the FXML file to load, excluding the ".fxml" extension.
     * @throws IOException If there is an error loading the specified FXML file.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

   /**
    * The main function in Java calls the launch method.
    */
    public static void main(String[] args) {
        launch();
    }

}