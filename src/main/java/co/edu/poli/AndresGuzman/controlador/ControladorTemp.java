package co.edu.poli.AndresGuzman.controlador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ControladorTemp {
    private Timeline tiempo;
    private static final int TIEMPO_PARTIDA = 180; // 3 minutos en segundos
    private int tiempoRestante;
    
    public void initialize() {
        // Inicializar el temporizador cuando se carga el controlador
        iniciarTemporizador();
    }
    
    private void iniciarTemporizador() {
        // Establecer el tiempo inicial
        tiempoRestante = TIEMPO_PARTIDA;
        actualizarLabel();
        
        // Crear la línea de tiempo con KeyFrames que se ejecutan cada segundo
        tiempo = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                tiempoRestante--;
                actualizarLabel();
                
                // Verificar si el tiempo se ha agotado
                if (tiempoRestante <= 0) {
                    tiempo.stop();
                    // Aquí puedes agregar código para cuando el tiempo se agote
                }
            })
        );
        
        // Configurar para que se repita indefinidamente (hasta que lo detengamos)
        tiempo.setCycleCount(TIEMPO_PARTIDA);
        
        // Iniciar el temporizador
        tiempo.play();
    }
    
    private void actualizarLabel() {
        // Convertir segundos a formato minutos:segundos
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        temporizador.setText(String.format("%02d:%02d", minutos, segundos));
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