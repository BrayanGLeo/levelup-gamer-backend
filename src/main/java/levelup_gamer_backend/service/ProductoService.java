package levelup_gamer_backend.service;

import levelup_gamer_backend.entity.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {

    Producto guardarProducto(Producto producto);

    Optional<Producto> obtenerPorCodigo(String codigo);

    List<Producto> obtenerTodos();

    void eliminarPorCodigo(String codigo);

    List<Producto> buscarPorCategoria(String nombreCategoria);

    List<Producto> obtenerProductosBajoStock();

    Producto actualizarStock(String codigo, int cantidadVendida);
}