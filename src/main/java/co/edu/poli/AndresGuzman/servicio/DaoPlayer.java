package co.edu.poli.AndresGuzman.servicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.AndresGuzman.modelo.Player;

public class DaoPlayer implements IDao<Player> {
    private Connection connection;
    public DaoPlayer(){
        this.connection = ConexionBD.getConexion();
    }
    
    @Override
    public String insertar(Player player) {
        var sql = "INSERT INTO players(username) values(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, player.getUsername());
            int filas = ps.executeUpdate();
            return (filas>0)?"Registro Hecho": "Registro No hecho";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error en el registro";
            
        }
    }
    public List<Player> listar() {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM players";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Player player = new Player("default");
                player.setId(rs.getInt("id"));
                player.setUsername(rs.getString("username"));
                players.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public Player buscar(String username) {
        String query = "SELECT * FROM players WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Player player = new Player("default");
                    player.setId(rs.getInt("id"));
                    player.setUsername(rs.getString("username"));
                    return player;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public String actualizar(Player player) {
        String query = "UPDATE players SET username = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, player.getUsername());
            stmt.setInt(2, player.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Player updated successfully!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating player!";
        }
        return "No rows affected!";
    }

    @Override
    public String eliminar(int id) {
        String query = "DELETE FROM players WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Player deleted successfully!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting player!";
        }
        return "No rows affected!";
    }
}
