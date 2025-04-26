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
        var sql = "SELECT difficulty FROM words WHERE LOWER(word) = ?";
        var sqlPalabra = "SELECT * FROM palabraenc WHERE palabra =?";
        var sqlNuevo = "INSERT INTO palabraEnc VALUES(?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
            PreparedStatement psPalabra = conexion.prepareStatement(sqlPalabra);
            PreparedStatement psEncontrada = conexion.prepareStatement(sqlNuevo)) {
            ps.setString(1, palabra.toLowerCase());
            psPalabra.setString(1, palabra);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    try(ResultSet rs2 =psPalabra.executeQuery()){
                        if(rs2.next()){
                            return new Words("Ya Usada");
                        }
                    }
                    psEncontrada.setString(1, palabra);
                    psEncontrada.executeUpdate();
                    return new Words(palabra, Difficulty.valueOf(rs.getString("difficulty")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Words("No Encontrada");
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
    public String eliminar(int id){
        var sql = "DELETE FROM palabraenc";
        try(PreparedStatement ps = conexion.prepareStatement(sql)){
            int filas = ps.executeUpdate();
            return filas>0 ?"todo bien":"todo mal";
        }
        catch (Exception e) {
            return "Error con la bd";
        }
    }

    @Override
    public Words buscarId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarId'");
    }

}
