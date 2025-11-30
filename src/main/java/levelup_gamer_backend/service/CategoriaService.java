package levelup_gamer_backend.service;

import levelup_gamer_backend.entity.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    Categoria guardarCategoria(Categoria categoria);
    Optional<Categoria> obtenerPorId(Long id);
    List<Categoria> obtenerTodas();
    void eliminarPorId(Long id);
}