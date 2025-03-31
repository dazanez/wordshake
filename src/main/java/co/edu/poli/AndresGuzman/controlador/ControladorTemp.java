package co.edu.poli.AndresGuzman.controlador;

import java.io.IOException;

import javax.swing.JOptionPane;

import co.edu.poli.AndresGuzman.modelo.Words;
import co.edu.poli.AndresGuzman.servicio.DaoWords;
import co.edu.poli.AndresGuzman.vista.App;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class ControladorTemp {
    private Timeline tiempo = new Timeline();
    private static final int TIEMPO_PARTIDA = 180;
    private int tiempoRestante;
    private DaoWords palabras = new DaoWords();
    private int puntajeT = 0;

    @FXML
    private Label temporizador;
    @FXML
    private Label barraPuntaje;

    @FXML
    private Button bttVerificar;

    @FXML
    private TextField palabrasIngre;

    @FXML
    private Label puntaje;

    @FXML
    private Label temporizador1;

   

    public void initialize() {
        iniciarTemporizador();
        barraPuntaje.setText(String.valueOf(puntajeT));
    }
    
    private void iniciarTemporizador() {
        tiempoRestante = TIEMPO_PARTIDA;
        actualizarLabel();
            KeyFrame accion = new KeyFrame(Duration.seconds(1), e -> {
                tiempoRestante--;
                actualizarLabel();
                if (tiempoRestante <= 0) {
                    tiempo.stop();
                    barraPuntaje.setText(String.valueOf(puntajeT));
                    JOptionPane.showMessageDialog(null, "Se Acabo el Tiempo, tu Puntaje fue: " + puntajeT);
                    try {
                        App.setRoot("paginaInicio");
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
        if (palabra != null) {
            JOptionPane.showMessageDialog(null, "Palabra encontrada");
            String dificultad =palabra.getDifficulty();
            switch (dificultad) {
                case "easy":
                    puntajeT += 10;
                    break;
                case "medium":
                    puntajeT += 20;
                    break;
                case "hard":
                    puntajeT += 30;
                    break;
            
                default:
                    break;
            }
            puntajeT += asignarPuntaje(palabrasIngre.getText());
        }
        else {
            JOptionPane.showMessageDialog(null, "Palabra no encontrada");
        }
        barraPuntaje.setText(String.valueOf(puntajeT));
        palabrasIngre.clear();
        tiempo.play();
    }

    public static int asignarPuntaje(String palabra) {
        int longitud = palabra.length();

        if (longitud >= 7) {
            return 20;
        } else if (longitud >= 5) {
            return 10;
        } else if (longitud >= 3) {
            return 5;
        } else {
            return 0; // Para palabras con menos de 3 letras
        }
    }
}