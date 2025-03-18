package co.edu.poli.AndresGuzman.controlador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ControladorTemp {
    private Timeline tiempo = new Timeline();
    private static final int TIEMPO_PARTIDA = 10;
    private int tiempoRestante;
    
    public void initialize() {
        iniciarTemporizador();
    }
    
    private void iniciarTemporizador() {
        tiempoRestante = TIEMPO_PARTIDA;
        actualizarLabel();
            KeyFrame accion = new KeyFrame(Duration.seconds(1), e -> {
                tiempoRestante--;
                actualizarLabel();
                if (tiempoRestante <= 0) {
                    tiempo.stop();
                }
            });
            tiempo.getKeyFrames().add(accion);
        tiempo.setCycleCount(TIEMPO_PARTIDA);
        tiempo.play();
    }
    
    private void actualizarLabel() {
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        temporizador.setText(String.format("%12d:%02d", minutos, segundos));
    }

    @FXML
    private Label temporizador;
    
    @FXML
    void clickReiniciar(ActionEvent event) {
        // Detener el temporizador actual si está en marcha
        if (tiempo != null) {
            tiempo.stop();
        }
        
        // Reiniciar el temporizador
        iniciarTemporizador();
    }
}