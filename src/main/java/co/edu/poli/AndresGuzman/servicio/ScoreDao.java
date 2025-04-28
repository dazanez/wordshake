package co.edu.poli.AndresGuzman.servicio;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.edu.poli.AndresGuzman.modelo.ScoreView;

public class ScoreDao implements IDao {
    private final Connection conn;

    public ScoreDao() {
        this.conn = ConexionBD.getConexion();
    }

    @Override
    public List<ScoreView> listar() {
        List<ScoreView> list = new ArrayList<>();

        String sql = "SELECT username, totalscore, startdate " +
                     "FROM games JOIN players ON games.playerid = players.id " +
                     "ORDER BY totalscore DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("totalscore");
                LocalDateTime playedAt = rs.getTimestamp("startdate").toLocalDateTime();

                list.add(new ScoreView(username, score, playedAt));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public ScoreView buscar(String nombre) {
        String sql = "SELECT username, totalscore, startdate " +
                     "FROM games JOIN players ON games.playerid = players.id " +
                     "WHERE username = ? " +
                     "ORDER BY totalscore DESC LIMIT 1";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    int score = rs.getInt("totalscore");
                    LocalDateTime playedAt = rs.getTimestamp("startdate").toLocalDateTime();

                    return new ScoreView(username, score, playedAt);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String insertar(Object t) {
        if (!(t instanceof ScoreView)) {
            return "Objeto inválido.";
        }

        ScoreView score = (ScoreView) t;

        String sql = "INSERT INTO games (playerid, totalscore, startdate) " +
                     "VALUES ((SELECT id FROM players WHERE username = ?), ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, score.getUsername());
            stmt.setInt(2, score.getScore());
            stmt.setTimestamp(3, Timestamp.valueOf(score.getPlayedAt()));

            int rows = stmt.executeUpdate();
            return rows > 0 ? "Insertado correctamente." : "No se pudo insertar.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al insertar: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(Object t) {
        if (!(t instanceof ScoreView)) {
            return "Objeto inválido.";
        }

        ScoreView score = (ScoreView) t;

        String sql = "UPDATE games SET totalscore = ?, startdate = ? " +
                     "WHERE playerid = (SELECT id FROM players WHERE username = ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, score.getScore());
            stmt.setTimestamp(2, Timestamp.valueOf(score.getPlayedAt()));
            stmt.setString(3, score.getUsername());

            int rows = stmt.executeUpdate();
            return rows > 0 ? "Actualizado correctamente." : "No se encontró el jugador para actualizar.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int id) {
        String sql = "DELETE FROM games WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            return rows > 0 ? "Eliminado correctamente." : "No se encontró el registro para eliminar.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar: " + e.getMessage();
        }
    }

    @Override
    public ScoreView buscarId(int id) {
        String sql = "SELECT username, totalscore, startdate " +
                     "FROM games JOIN players ON games.playerid = players.id " +
                     "WHERE games.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    int score = rs.getInt("totalscore");
                    LocalDateTime playedAt = rs.getTimestamp("startdate").toLocalDateTime();

                    return new ScoreView(username, score, playedAt);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
