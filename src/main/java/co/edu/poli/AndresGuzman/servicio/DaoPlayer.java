package co.edu.poli.AndresGuzman.servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import co.edu.poli.AndresGuzman.modelo.Player;

public class DaoPlayer implements IDao<Player> {
    private Connection conexion;
    public DaoPlayer(){
        this.conexion = ConexionBD.getConexion();
    }
    
    @Override
    public String insertar(Player player) {
        var sql = "INSERT INTO player(username) values(?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, player.getNombre());
            int filas = ps.executeUpdate();
            return (filas>0)?"Registro Hecho": "Registro No hecho";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error en el registro";
            
        }
    }
    @Override
    public List<Player> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }

    @Override
    public Player buscar(String nombre) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscar'");
    }
    @Override
    public String actualizar(Player t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }
    @Override
    public String eliminar(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }
    

}
