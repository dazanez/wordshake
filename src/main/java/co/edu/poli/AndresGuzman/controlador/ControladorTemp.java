package co.edu.poli.AndresGuzman.controlador;

import java.io.IOException;

import javax.swing.JOptionPane;

import co.edu.poli.AndresGuzman.modelo.Game;
import co.edu.poli.AndresGuzman.modelo.Player;
import co.edu.poli.AndresGuzman.modelo.Words;
import co.edu.poli.AndresGuzman.servicio.DaoGame;
import co.edu.poli.AndresGuzman.servicio.DaoPlayer;
import co.edu.poli.AndresGuzman.servicio.DaoWords;
import co.edu.poli.AndresGuzman.vista.App;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

public class ControladorTemp {
    private Timeline tiempo = new Timeline();
    private static final int TIEMPO_PARTIDA = 10;
    private int tiempoRestante;
    private DaoWords palabras = new DaoWords();
    private int puntajeT = 0;
    private Alert.AlertType tipoAlerta;


    @FXML
    private Label temporizador;
    @FXML
    private Label barraPuntaje;

    @FXML
    private Button bttVerificar, bttIniciar;

    @FXML
    private TextField palabrasIngre;

    @FXML
    private Label puntaje;

    @FXML
    private Label temporizador1;

   

    
    private void iniciarTemporizador() {
        if (tiempo != null){
            tiempo.stop();
            tiempo.getKeyFrames().clear();
        }
        tiempoRestante = TIEMPO_PARTIDA;
        actualizarLabel();
            KeyFrame accion = new KeyFrame(Duration.seconds(1), e -> {
                tiempoRestante--;
                actualizarLabel();
                if (tiempoRestante <= 0) {
                    tiempo.stop();
                    barraPuntaje.setText(String.valueOf(puntajeT));
                    guardarPuntaje();
                    tipoAlerta = Alert.AlertType.INFORMATION;
                    Platform.runLater(() -> mostrarAlerta("Se Acabo el Tiempo", tipoAlerta));
                    try {
                        App.setRoot("paginaInicio", "Inicio");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        tiempo.getKeyFrames().add(accion);
        tiempo.setCycleCount(TIEMPO_PARTIDA);
        tiempo.play();
    }
    
    private void actualizarLabel() {
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        temporizador.setText(String.format("%02d:%02d", minutos, segundos));
    }
    

    @FXML
    void clickIniciar(ActionEvent event) {
        iniciarTemporizador();
        bttVerificar.setDisable(false);
        barraPuntaje.setText(String.valueOf(puntajeT));
        palabrasIngre.setDisable(false);
        bttVerificar.setDisable(false);
        bttIniciar.setDisable(true);
    }

    
    @FXML
    void clickReiniciar(ActionEvent event) {
        if (tiempo != null) {
            tiempo.stop();
            puntajeT = 0;
            barraPuntaje.setText(String.valueOf(puntajeT));
        }
        iniciarTemporizador();
    }

    @FXML
void clickVerificar(ActionEvent event) {
    Words palabra = palabras.buscar(palabrasIngre.getText());
    tiempo.pause();
    String mensaje;
    if (palabra != null) {
        mensaje = "¡Palabra encontrada!";
        puntajeT = Words.calcularPuntaje(palabra, puntajeT);
        tipoAlerta = Alert.AlertType.INFORMATION;
    } else {
        mensaje = "Palabra no encontrada";
        tipoAlerta = Alert.AlertType.ERROR;
    }
    mostrarAlerta(mensaje, tipoAlerta);
    barraPuntaje.setText(String.valueOf(puntajeT));
    palabrasIngre.clear();
    tiempo.play();
}

private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
    tiempo.pause();
    Alert alerta = new Alert(tipo);
    alerta.setTitle("Resultadoo");
    alerta.getDialogPane().getStylesheets().add(getClass().getResource("/co/edu/poli/AndresGuzman/estilosTiempo.css").toExternalForm());
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    Image imagen = new Image(getClass().getResourceAsStream("/co/edu/poli/AndresGuzman/icono.jpg"));
    ImageView icono = new ImageView(imagen);
    icono.setFitHeight(100); // Ajusta el tamaño según necesites
    icono.setFitWidth(100);
    alerta.setGraphic(icono);
    // Cambiar el ícono de la ventana de la alerta
    Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image(getClass().getResourceAsStream("/co/edu/poli/AndresGuzman/icono.jpg"))); 

    alerta.showAndWait();
}

  public void guardarPuntaje() {
        Game game = new Game(ControladorUser.jugador.getId(), puntajeT);
        new DaoGame().insertar(game);
    }

    public static void setJugador(String username) {
        ControladorUser.jugador = new DaoPlayer().buscar(username);
    }

}