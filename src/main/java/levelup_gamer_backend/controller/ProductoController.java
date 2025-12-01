package levelup_gamer_backend.controller;

import levelup_gamer_backend.entity.Producto;
import levelup_gamer_backend.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/tienda/productos")
    public ResponseEntity<List<Producto>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/tienda/productos/{codigo}")
    public ResponseEntity<Producto> obtenerPorCodigo(@PathVariable String codigo) {
        return productoService.obtenerPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/productos")
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.guardarProducto(producto);
            return ResponseEntity.ok(nuevoProducto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/productos/{codigo}")
    public ResponseEntity<?> actualizarProducto(@PathVariable String codigo, @RequestBody Producto producto) {
        if (!productoService.obtenerPorCodigo(codigo).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            producto.setCodigo(codigo);
            Producto productoActualizado = productoService.guardarProducto(producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/productos/{codigo}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable String codigo) {
        if (!productoService.obtenerPorCodigo(codigo).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productoService.eliminarPorCodigo(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/reportes/stock-critico")
    public ResponseEntity<List<Producto>> obtenerProductosBajoStock() {
        return ResponseEntity.ok(productoService.obtenerProductosBajoStock());
    }
}