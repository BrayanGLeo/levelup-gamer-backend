package levelup_gamer_backend.controller;

import levelup_gamer_backend.entity.Producto;
import levelup_gamer_backend.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/tienda/productos")
    public ResponseEntity<List<Producto>> obtenerProductosTienda() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/tienda/productos/{codigo}")
    public ResponseEntity<Producto> obtenerProductoDetalle(@PathVariable String codigo) {
        return productoService.obtenerPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listarProductosAdmin() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/productos/{codigo}")
    public ResponseEntity<Producto> obtenerProductoAdmin(@PathVariable String codigo) {
        return productoService.obtenerPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/productos")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.guardarProducto(producto));
    }

    @PutMapping("/productos/{codigo}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable String codigo, @RequestBody Producto producto) {
        return productoService.obtenerPorCodigo(codigo)
                .map(p -> {
                    p.setNombre(producto.getNombre());
                    p.setDescripcion(producto.getDescripcion());
                    p.setPrecio(producto.getPrecio());
                    p.setStock(producto.getStock());
                    p.setStockCritico(producto.getStockCritico());
                    p.setImagenUrl(producto.getImagenUrl());
                    p.setCategoria(producto.getCategoria());
                    return ResponseEntity.ok(productoService.guardarProducto(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/productos/{codigo}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String codigo) {
        productoService.eliminarPorCodigo(codigo);
        return ResponseEntity.noContent().build();
    }
}