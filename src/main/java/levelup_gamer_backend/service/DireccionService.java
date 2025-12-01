package levelup_gamer_backend.service;

import levelup_gamer_backend.entity.Direccion;
import java.util.List;

public interface DireccionService {
    List<Direccion> obtenerPorUsuario(String email);

    Direccion agregarDireccion(String email, Direccion direccion);

    void eliminarDireccion(String email, Long idDireccion);
}