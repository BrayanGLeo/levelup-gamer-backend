package levelup_gamer_backend.controller;

import levelup_gamer_backend.entity.Categoria;
import levelup_gamer_backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodas() {
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }

    @PostMapping
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria nuevaCategoria = categoriaService.guardarCategoria(categoria);
            return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        if (categoria.getId() == null || !categoria.getId().equals(id)) {
            return ResponseEntity.badRequest().body("El ID del cuerpo debe coincidir con el ID de la URL.");
        }
        if (!categoriaService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            Categoria categoriaActualizada = categoriaService.guardarCategoria(categoria);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        if (!categoriaService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        categoriaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}