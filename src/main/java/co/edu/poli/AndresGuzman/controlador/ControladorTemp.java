package co.edu.poli.AndresGuzman.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControladorTemp {
    private Timeline tiempo = new Timeline();
    private static final int TIEMPO_PARTIDA = 180;
    private int tiempoRestante;
    private DaoWords palabras = new DaoWords();
    private int puntajeT = 0;
    private Alert.AlertType tipoAlerta;
    private List<Label> desactivados = new ArrayList<>();
    private int cantidadLetras = 0;


    @FXML
    private ImageView iconoMensaje;
    @FXML
    private GridPane boardGrid;

    @FXML
    private Label temporizador, mensajeExito;
    @FXML
    private Label barraPuntaje, nombreJugador;

    @FXML
    private Button bttVerificar, bttIniciar,bttBorrar, bttRefrescar, bttRei,bttVolver,  bttFacil,  bttHard,  bttMedium;

    @FXML
    private TextField palabraInput;

    @FXML
    private Label puntaje;

    @FXML
    private Label temporizador1;


    @FXML
    private void initialize(){
        nombreJugador.setText(nombreJugador.getText() + " " + ControladorUser.getJugador().getUsername());
    }
    @FXML
    void clickFacil(ActionEvent event) {
        cantidadLetras = 40;
        bttFacil.setVisible(false);
        bttMedium.setVisible(false);
        bttHard.setVisible(false);
        iniciar(cantidadLetras);
    }

    @FXML
    void clickHard(ActionEvent event) {
        cantidadLetras = 65;
        bttFacil.setVisible(false);
        bttMedium.setVisible(false);
        bttHard.setVisible(false);
        iniciar(cantidadLetras);
    }
    @FXML
    void clickMedium(ActionEvent event) {
        cantidadLetras = 50;
        bttFacil.setVisible(false);
        bttMedium.setVisible(false);
        bttHard.setVisible(false);
        iniciar(cantidadLetras);
    }

    private void iniciarTemporizador(int letras) {
        if (tiempo != null){
            tiempo.stop();
            tiempo.getKeyFrames().clear();
        }
        tiempoRestante = TIEMPO_PARTIDA;
        actualizarLabel();
        cargarLetras(letras);
        KeyFrame accion = new KeyFrame(Duration.seconds(1), e -> {
            tiempoRestante--;
            actualizarLabel();
            if (tiempoRestante <= 0) {
                tiempo.stop();
                barraPuntaje.setText(String.valueOf(puntajeT));
                guardarPuntaje();
                tipoAlerta = Alert.AlertType.INFORMATION;
                palabras.eliminar(1);
                Platform.runLater(() -> mostrarAlerta("Se Acabo el Tiempo " + ControladorUser.getJugador().getUsername(), tipoAlerta, "timeout.png"));
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

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo,String imagene) {
        tiempo.pause();
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Resultado");
        alerta.getDialogPane().getStylesheets().add(getClass().getResource("/co/edu/poli/AndresGuzman/estilosTiempo.css").toExternalForm());
        alerta.getDialogPane().setPrefWidth(450);
        alerta.getDialogPane().setPrefHeight(220);
        alerta.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje + "\n Tu Puntaje fue: " + puntajeT + "Pts.");
        Image imagen = new Image(getClass().getResourceAsStream("/co/edu/poli/AndresGuzman/"+imagene));
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
        bttFacil.setDisable(false);
        bttHard.setDisable(false);
        bttMedium.setDisable(false);
    }
    private void iniciar(int letras) {
        iniciarTemporizador(letras);
        bttVerificar.setDisable(false);
        barraPuntaje.setText(String.valueOf(puntajeT));
        palabraInput.setDisable(false);
        bttVerificar.setDisable(false);
        bttIniciar.setDisable(true);
        bttRefrescar.setDisable(false);
        bttBorrar.setDisable(false);
        bttRei.setDisable(false);
        bttIniciar.setVisible(false);
        bttVolver.setVisible(true);
    }


    @FXML
    void clickReiniciar(ActionEvent event) {
        if (tiempo != null) {
            tiempo.stop();
            puntajeT = 0;
            barraPuntaje.setText(String.valueOf(puntajeT));
            palabras.eliminar(1);
            palabraInput.clear();
            desactivados.clear();
        }
        iniciarTemporizador(cantidadLetras);
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
            reactivarLetras(palabraInput.getText());
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
            reactivarLetras(palabraInput.getText());
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

    public void cargarLetras(int totalLetras) {
    boardGrid.getChildren().clear();
    boardGrid.getRowConstraints().clear();
    boardGrid.getColumnConstraints().clear();
    
    // calcula el tamaño de la cuadrícula (asumimos siempre cuadrados perfectos)
    int gridSize = (int) Math.sqrt(totalLetras);
    double cellSize = 60;

    // 3) forzamos el tamaño total del GridPane
    boardGrid.setPrefWidth(gridSize * cellSize);
    boardGrid.setPrefHeight(gridSize * cellSize);

    // crea constraints para que cada celda ocupe un porcentaje igual
    for (int i = 0; i < gridSize; i++) {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100.0 / gridSize);
        boardGrid.getColumnConstraints().add(cc);

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100.0 / gridSize);
        boardGrid.getRowConstraints().add(rc);
    }

    // genera las letras y llena el GridPane
    List<Character> letras = Words.generarLetras(totalLetras);
    int index = 0;
    for (int row = 0; row < gridSize; row++) {
        for (int col = 0; col < gridSize; col++) {
            Label label = new Label(letras.get(index++).toString());
            label.setAlignment(Pos.CENTER);
            label.getStyleClass().add("label-letter");
            label.setOnMouseClicked(this::handleCellClick);
            label.setMaxSize(45, 45);
            boardGrid.add(label, col, row);
        }
    }
}


    // Método para manejar el clic en una celda
    @FXML
    public void handleCellClick(MouseEvent event) {
        Label clickedLabel = (Label) event.getSource();
        clickedLabel.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
        desactivados.add(clickedLabel);
        clickedLabel.setOpacity(0.5);
        clickedLabel.setOnMouseClicked(null);
        String letra = clickedLabel.getText();
        palabraInput.setText(palabraInput.getText() + letra);
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
            String letraBorrada = palabra.substring(palabra.length()-1);
            reactivarLetras(letraBorrada);
        }
        else{
            palabraInput.setPromptText("Ingrese Palabras");
        }
    }

    public void reactivarLetras(String palabra) {
        for (int i = palabra.length() - 1; i >= 0; i--) {
            char letra = palabra.charAt(i);
            Iterator<Label> iterator = desactivados.iterator();
            while (iterator.hasNext()) {
                Label label = iterator.next();
                if (label.getText().equalsIgnoreCase(String.valueOf(letra))) {
                    label.setStyle("-fx-background-color: #6a00f4; -fx-text-fill: white;");
                    label.setOpacity(1.0);
                    label.setOnMouseClicked(this::handleCellClick);
                    iterator.remove(); // Elimina de la lista para evitar duplicados
                    break; // Reactivamos solo un Label por letra
                }
            }
        }
}

    @FXML
    void clickRefresh(ActionEvent event) {
        cargarLetras(cantidadLetras);
        palabraInput.clear();
        desactivados.clear();
    }


    @FXML
    void clickVolver(ActionEvent event) throws IOException {
        mostrarAlerta("Volviendo al Inicio...", AlertType.INFORMATION, "volviendo.png");
        App.setRoot("paginaInicio", "WordShake");
    }

}