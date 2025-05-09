package co.edu.poli.AndresGuzman.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.awt.Desktop;
import java.time.LocalDateTime;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.swing.JOptionPane;

import co.edu.poli.AndresGuzman.modelo.Player;
import co.edu.poli.AndresGuzman.modelo.ScoreView;
import co.edu.poli.AndresGuzman.servicio.DaoPlayer;
import co.edu.poli.AndresGuzman.servicio.ScoreDao;
import co.edu.poli.AndresGuzman.vista.App;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControladorUser implements Initializable {
    public static Player jugador = new Player("default");
    private DaoPlayer jugadorDao = new DaoPlayer();
    private ScoreDao mejorPuntDao = new ScoreDao(); 
    private boolean mostrarMejores = false;
    @FXML
    private TableView<ScoreView> mejorPunt;
    @FXML
    private TableColumn<ScoreView, String> colUsuario;
    @FXML
    private TableColumn<ScoreView, Integer> colPuntaje;
    @FXML
    private TableColumn<ScoreView, LocalDateTime> colFecha;

    @FXML
    private Label ayuda, mejores;
    @FXML
    private Button iniciopartida, bttAyuda, bttMejores;

    @FXML
    private TextField user_name;

    @FXML
    private AnchorPane rootPane;  

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPuntaje.setCellValueFactory(new PropertyValueFactory<>("score"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("playedAt"));

        String path = getClass().getResource("/co/edu/poli/AndresGuzman/camcion.mp3").toString();
        javafx.scene.media.Media media = new javafx.scene.media.Media(path);
        javafx.scene.media.MediaPlayer mediaPlayer = new javafx.scene.media.MediaPlayer(media);
        mediaPlayer.setStartTime(Duration.seconds(2));
        mediaPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE); 
        mediaPlayer.setVolume(1);
        mediaPlayer.play();

        // Animación sincronizada con la música
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> {
                    double currentTime = mediaPlayer.getCurrentTime().toSeconds();
                    double hue = (currentTime % 60) / 15;
                    rootPane.setStyle("-fx-background-color: hsb(" + (hue * 360) + ", 100%, 100%);");
                }),
                new KeyFrame(Duration.seconds(0.1))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    void click(ActionEvent event) throws IOException {
        if (user_name.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese Un Usuario", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            jugador = jugadorDao.buscar(user_name.getText());
            if (jugador != null) {
                jugadorDao.actualizar(jugador);
            } else {
                jugadorDao.insertar(new Player(user_name.getText()));
                jugador = jugadorDao.buscar(user_name.getText());
            }
            App.setRoot("partidaConTemp", "Partida de: " + user_name.getText());
            user_name.clear();
        }
    }

    private void accederLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickAyuda(ActionEvent event) {
        accederLink("https://drive.google.com/file/d/1jbAHtIF-jdMmj1zvYeUpSghwDB2dIBg_/view?usp=sharing");
    }

    @FXML
    void muestraAyuda(MouseEvent event) {
        ayuda.setVisible(true);
    }

    @FXML
    void quitaAyuda(MouseEvent event) {
        ayuda.setVisible(false);
    }

    @FXML
    void mostrarMejores(MouseEvent event) {
        mejores.setVisible(true);
    }

    @FXML
    void ocultarMejores(MouseEvent event) {
        mejores.setVisible(false);
    }

    @FXML
    void clickMejores(ActionEvent event) {
        mostrarMejores = !mostrarMejores;
        List<ScoreView> lista = mejorPuntDao.listar();
        ObservableList<ScoreView> data = FXCollections.observableArrayList(lista);
        mejorPunt.setItems(data);
        mejorPunt.setVisible(mostrarMejores);
    }

    public static Player getJugador() {
        return jugador;
    }
}
