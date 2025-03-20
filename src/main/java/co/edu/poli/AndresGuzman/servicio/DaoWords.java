package co.edu.poli.AndresGuzman.servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import co.edu.poli.AndresGuzman.modelo.Words;
import co.edu.poli.AndresGuzman.modelo.Words.Difficulty;

public class DaoWords implements IDao<Words>{
    private Connection conexion;
    public DaoWords(){
        this.conexion = ConexionBD.getConexion();
    }

    @Override
    public List<Words> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }

    @Override
    public Words buscar(String palabra) {
        var sql = "SELECT difficulty FROM words WHERE LOWER(word) = LOWER(?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, palabra.toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Words(palabra, Difficulty.valueOf(rs.getString("difficulty")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String insertar(Words t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertar'");
    }

    @Override
    public String actualizar(Words t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public String eliminar(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

}
