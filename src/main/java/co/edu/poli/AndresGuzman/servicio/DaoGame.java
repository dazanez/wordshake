package co.edu.poli.AndresGuzman.servicio;

import co.edu.poli.AndresGuzman.modelo.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoGame implements IDao<Game> {

    private Connection connection;

    // Constructor para inicializar la conexión a la base de datos
    public DaoGame() {
        this.connection = ConexionBD.getConexion();
    }

    @Override
    public List<Game> listar() {
        List<Game> games = new ArrayList<>();
        String query = "SELECT * FROM games";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Game game = new Game();
                game.setId(rs.getInt("id"));
                game.setPlayerId(rs.getInt("playerId"));
                game.setTotalScore(rs.getInt("totalScore"));
                game.setStartDate(rs.getTimestamp("startDate").toLocalDateTime());
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    @Override
    public Game buscar(String nombre) {
        // Supongo que 'nombre' es el nombre del jugador. Si no es así, puedes modificar esta parte.
        String query = "SELECT * FROM games WHERE playerId = (SELECT id FROM players WHERE name = ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Game game = new Game();
                    game.setId(rs.getInt("id"));
                    game.setPlayerId(rs.getInt("playerId"));
                    game.setTotalScore(rs.getInt("totalScore"));
                    game.setStartDate(rs.getTimestamp("startDate").toLocalDateTime());
                    return game;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String insertar(Game game) {
        String query = "INSERT INTO games (playerId, totalScore, startDate) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, game.getPlayerId());
            stmt.setInt(2, game.getTotalScore());
            stmt.setTimestamp(3, Timestamp.valueOf(game.getStartDate()));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        game.setId(generatedKeys.getInt(1));
                    }
                }
                return "Game inserted successfully!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error inserting game!";
        }
        return "No rows affected!";
    }

    @Override
    public String actualizar(Game game) {
        String query = "UPDATE games SET playerId = ?, totalScore = ?, startDate = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, game.getPlayerId());
            stmt.setInt(2, game.getTotalScore());
            stmt.setTimestamp(3, Timestamp.valueOf(game.getStartDate()));
            stmt.setInt(4, game.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Game updated successfully!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating game!";
        }
        return "No rows affected!";
    }

    @Override
    public String eliminar(int id) {
        String query = "DELETE FROM games WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "Game deleted successfully!";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting game!";
        }
        return "No rows affected!";
    }
}

