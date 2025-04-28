package co.edu.poli.AndresGuzman.controlador;

import java.io.IOException;
import java.util.List;

import javax.print.DocFlavor.STRING;

import co.edu.poli.AndresGuzman.modelo.Game;
import co.edu.poli.AndresGuzman.modelo.Words;
import co.edu.poli.AndresGuzman.servicio.DaoGame;
import co.edu.poli.AndresGuzman.servicio.DaoPlayer;
import co.edu.poli.AndresGuzman.servicio.DaoWords;
import co.edu.poli.AndresGuzman.vista.App;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControladorTemp {
    private Timeline tiempo = new Timeline();
    private static final int TIEMPO_PARTIDA = 130;
    private int tiempoRestante;
    private DaoWords palabras = new DaoWords();
    private int puntajeT = 0;
    private Alert.AlertType tipoAlerta;

    @FXML
    private ImageView iconoMensaje;
    @FXML
    private GridPane boardGrid;

    @FXML
    private Label temporizador, mensajeExito;
    @FXML
    private Label barraPuntaje;

    @FXML
    private Button bttVerificar, bttIniciar,bttBorrar, bttRefrescar, bttRei;

    @FXML
    private TextField palabraInput;

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
        cargarLetras();
        KeyFrame accion = new KeyFrame(Duration.seconds(1), e -> {
            tiempoRestante--;
            actualizarLabel();
            if (tiempoRestante <= 0) {
                tiempo.stop();
                barraPuntaje.setText(String.valueOf(puntajeT));
                guardarPuntaje();
                tipoAlerta = Alert.AlertType.INFORMATION;
                palabras.eliminar(1);
                Platform.runLater(() -> mostrarAlerta("Se Acabo el Tiempo " + ControladorUser.getJugador().getUsername(), tipoAlerta));
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

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        tiempo.pause();
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Resultadoo");
        alerta.getDialogPane().getStylesheets().add(getClass().getResource("/co/edu/poli/AndresGuzman/estilosTiempo.css").toExternalForm());
        alerta.getDialogPane().setPrefWidth(450);
        alerta.getDialogPane().setPrefHeight(220);
        alerta.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje + "\n Tu Puntaje fue: " + puntajeT + "Pts.");
        Image imagen = new Image(getClass().getResourceAsStream("/co/edu/poli/AndresGuzman/timeout.png"));
        ImageView icono = new ImageView(imagen);
        icono.setFitHeight(150);
        icono.setFitWidth(100);
        alerta.setGraphic(icono);
        // Cambiar el ícono de la ventana de la alerta
        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/co/edu/poli/AndresGuzman/icono.jpg")));

        alerta.showAndWait();
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
        palabraInput.setDisable(false);
        bttVerificar.setDisable(false);
        bttIniciar.setDisable(true);
        bttRefrescar.setDisable(false);
        bttBorrar.setDisable(false);
        bttRei.setDisable(false);
    }


    @FXML
    void clickReiniciar(ActionEvent event) {
        if (tiempo != null) {
            tiempo.stop();
            puntajeT = 0;
            barraPuntaje.setText(String.valueOf(puntajeT));
            palabras.eliminar(1);
        }
        iniciarTemporizador();
    }

    @FXML
    void clickVerificar(ActionEvent event) {
        Boolean esExito;
        Words palabra = palabras.buscar(palabraInput.getText());
        tiempo.pause();
        String mensaje;

        if (palabra.getWord().equals("Ya Usada")) {
            mensaje = "Palabra Ya Usada";
            tipoAlerta = Alert.AlertType.ERROR;
            esExito = false;
        }
        else if (!palabra.getWord().equals("No Encontrada")) {
            mensaje = "¡Palabra encontrada!";
            puntajeT = Words.calcularPuntaje(palabra, puntajeT);
            tipoAlerta = Alert.AlertType.INFORMATION;
            esExito = true;
        } 
        else {
            mensaje = "Palabra no encontrada";
            tipoAlerta = Alert.AlertType.ERROR;
            esExito = false;
        }
        mostrarMensajeAnimado(mensaje, esExito);
        barraPuntaje.setText(String.valueOf(puntajeT));
        palabraInput.clear();
        tiempo.play();
    }

    private void mostrarMensajeAnimado(String mensaje, boolean esExito) {
        Image imagen = new Image(getClass().getResource(esExito? "/co/edu/poli/AndresGuzman/aprobacion.png" : "/co/edu/poli/AndresGuzman/desapruebo.png").toExternalForm());
        iconoMensaje.setImage(imagen);
        iconoMensaje.setFitWidth(115);
        iconoMensaje.setFitHeight(86);
        mensajeExito.setText(mensaje);
        iconoMensaje.setOpacity(1.0);
        iconoMensaje.setVisible(true);

        mensajeExito.setOpacity(1.0);
        mensajeExito.setVisible(true);

        FadeTransition fadeTexto = new FadeTransition(Duration.seconds(5), mensajeExito);
        fadeTexto.setFromValue(1.0);
        fadeTexto.setToValue(0.0);

        FadeTransition fadeIcono = new FadeTransition(Duration.seconds(5), iconoMensaje);
        fadeIcono.setFromValue(1.0);
        fadeIcono.setToValue(0.0);

        ParallelTransition parallel = new ParallelTransition(fadeTexto, fadeIcono);
        parallel.setOnFinished(e -> {
            mensajeExito.setVisible(false);
            iconoMensaje.setVisible(false);
        });
        parallel.play();

    }


    public void guardarPuntaje() {
        Game game = new Game(ControladorUser.getJugador().getId(), puntajeT);
        new DaoGame().insertar(game);
    }

    public void cargarLetras() {
        List<Character> letras = Words.generarLetras(25);
        int index = 0; // Para llevar un seguimiento del índice de letras en el ArrayList
        int totalRows = boardGrid.getRowCount();
        int totalColumns = boardGrid.getColumnCount();

        // Recorremos las filas (0 a 3) y las columnas (0 a 3) para agregar las celdas
        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < totalColumns; col++) {
                // Crear un nuevo Label y asignar la letra de la lista
                Label label = new Label(letras.get(index).toString());
                label.getStyleClass().add("label-letter");
                label.setOnMouseClicked(this::handleCellClick);  // Asociamos el evento de clic
                boardGrid.add(label, col, row);  // Añadimos el label en la posición correspondiente
                index++;  // Avanzamos al siguiente índice de la lista
            }
        }
    }

    // Método para manejar el clic en una celda
    @FXML
    public void handleCellClick(MouseEvent event) {
        // Obtener el Label que fue clickeado
        Label clickedLabel = (Label) event.getSource();


        // Obtener el texto (letra) de la celda
        String letra = clickedLabel.getText();
        palabraInput.setText(palabraInput.getText() + letra);


        // Aquí puedes hacer lo que necesites con la letra seleccionada
//        System.out.println("¡Se ha hecho clic en la letra: " + letra + "!");
    }

    public static void setJugador(String username) {
        ControladorUser.jugador = new DaoPlayer().buscar(username);
    }

    
    @FXML
    void clickBorrar(ActionEvent event) {
        if(!palabraInput.getText().isEmpty()){
            String palabra = palabraInput.getText();
            String palabraError = palabra.substring(0,palabra.length()-1);
            palabraInput.setText(palabraError);
        }
        else{
            palabraInput.setPromptText("Ingrese Palabras");
        }
    }

    @FXML
    void clickRefresh(ActionEvent event) {
        cargarLetras();
        palabraInput.clear();
    }

}