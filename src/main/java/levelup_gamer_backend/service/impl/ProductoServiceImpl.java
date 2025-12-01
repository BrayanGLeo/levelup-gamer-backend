package levelup_gamer_backend.service.impl;

import levelup_gamer_backend.entity.Producto;
import levelup_gamer_backend.repository.ProductoRepository;
import levelup_gamer_backend.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> obtenerPorCodigo(String codigo) {
        return productoRepository.findById(codigo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional
    public void eliminarPorCodigo(String codigo) {
        productoRepository.deleteById(codigo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorCategoria(String nombreCategoria) {
        return productoRepository.findByCategoriaNombre(nombreCategoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerProductosBajoStock() {
        return productoRepository.findAll().stream()
                .filter(p -> p.getStock() <= p.getStockCritico())
                .toList();
    }

    @Override
    @Transactional
    public Producto actualizarStock(String codigo, int cantidadVendida) {
        Producto producto = productoRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con código: " + codigo));

        if (producto.getStock() < cantidadVendida) {
            throw new RuntimeException("Stock insuficiente. Solo hay " + producto.getStock() + " unidades de "
                    + producto.getNombre() + ".");
        }

        producto.setStock(producto.getStock() - cantidadVendida);
        return productoRepository.save(producto);
    }
}