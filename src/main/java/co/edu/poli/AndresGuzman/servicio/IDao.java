package co.edu.poli.AndresGuzman.servicio;

import java.util.List;

public interface IDao<T> {
    List<T> listar();
    T buscar(String nombre);
    String insertar(T t);
    String actualizar(T t);
    String eliminar(int id);
}
