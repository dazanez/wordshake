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
import javafx.util.Duration;

public class App extends Application {

    private static Scene scene;
     private static final String path =
        Main.class
            .getResource("/co/edu/poli/AndresGuzman/camcion.mp3")
            .toExternalForm().toString();
    static javafx.scene.media.Media media = new javafx.scene.media.Media(path);
    public static javafx.scene.media.MediaPlayer mediaPlayer = new javafx.scene.media.MediaPlayer(media);

   /**
    * The start method loads a FXML file to create the initial scene for a JavaFX application titled "WordShake".
    * 
    * @param stage The `Stage` parameter represents the main window or container for your JavaFX application. It is typically the top-level container that holds all the visual elements of your application, such as scenes, layouts, and controls. In the provided code snippet, the `start` method is an overridden method from the
    */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("paginaInicio"));
        stage.setTitle("WordShake");
        mediaPlayer.setStartTime(Duration.seconds(2));
        mediaPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE); 
        mediaPlayer.setVolume(1);
        stage.setScene(scene);
        mediaPlayer.play();
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
    public static void setRoot(String fxml,String titulo) throws IOException {
        Stage stage = (Stage) scene.getWindow();
        stage.setTitle(titulo);
        scene.setRoot(loadFXML(fxml));
        stage.sizeToScene();
    }

   /**
    * The main function in Java calls the launch method.
    */
    public static void main(String[] args) {
        launch();
    }

}