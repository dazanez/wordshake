package co.edu.poli.AndresGuzman.servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            ps.setString(1, player.getUsername());
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
    public Player buscarId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscar'");
    }

    @Override
    public String actualizar(Player t) {
        var sql = "UPDATE player SET signup_date = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, t.getId());
            int exito = ps.executeUpdate();
            return exito > 0 ? "Actualizacion Exitosa" : "Paila";

        } catch (Exception e) {
            return "Problema con la base de Datos";
        }
    }
    @Override
    public String eliminar(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    @Override
    public Player buscar(String nombre) {
        Player player = null;
        var sql = "SELECT * FROM player WHERE username = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    player = new Player(nombre);
                    player.setId(rs.getInt("id"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }
}