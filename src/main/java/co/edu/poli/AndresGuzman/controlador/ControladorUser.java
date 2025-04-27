/**
 * The class ControladorUser is a Java controller class for a user interface that includes a button for starting a game and a text field for entering a user name.
 */
package co.edu.poli.AndresGuzman.controlador;

import java.io.IOException;
import java.net.URI;
import java.awt.Desktop;

import javax.swing.JOptionPane;

import co.edu.poli.AndresGuzman.modelo.Player;
import co.edu.poli.AndresGuzman.servicio.DaoPlayer;
import co.edu.poli.AndresGuzman.vista.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ControladorUser {
    public static Player jugador = new Player("default");
    private DaoPlayer jugadorDao = new DaoPlayer();
    @FXML
    private Label ayuda, mejores;
    @FXML
    private Button iniciopartida,  bttAyuda, bttMejores;

    @FXML
    private TextField user_name;

    /**
     * The click function is an event handler in JavaFXML that takes an ActionEvent as a parameter.
     *
     * @param event The `event` parameter in the `click` method is of type `ActionEvent`. It represents the event that occurred, such as a button click or menu item selection, that triggered the method to be called. You can use this parameter to access information about the event or perform actions based on the
     * @throws IOException
     */
    @FXML
    void click(ActionEvent event) throws IOException {
        if(user_name.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Ingrese Un Usuario", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else{
            jugador = jugadorDao.buscar(user_name.getText());
            if(jugador != null) {
                jugadorDao.actualizar(jugador);
            }
            else{
                jugadorDao.insertar(new Player(user_name.getText()));
            }
            App.setRoot("partidaConTemp", "Partida de: " + user_name.getText());
            user_name.clear();
        }
    }

    private void accederLink(String url){
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
        System.out.println("Tabla De Mejores");
    }

    public static Player getJugador(){
        return jugador;
    }
}