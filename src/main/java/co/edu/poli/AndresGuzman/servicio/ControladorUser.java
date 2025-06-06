/**
 * The class ControladorUser is a Java controller class for a user interface that includes a button for starting a game and a text field for entering a user name.
 */
package co.edu.poli.AndresGuzman.servicio;

import java.io.IOException;

import javax.swing.JOptionPane;

import co.edu.poli.AndresGuzman.modelo.Player;
import co.edu.poli.AndresGuzman.servicio.DaoPlayer;
import co.edu.poli.AndresGuzman.vista.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControladorUser {
    public static Player jugador = new Player("default");
    private DaoPlayer jugadorDao = new DaoPlayer();
    @FXML
    private Button iniciopartida;

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

    public Player getJugador(){
        return jugador;
    }
}