package levelup_gamer_backend.controller;

import levelup_gamer_backend.entity.Direccion;
import levelup_gamer_backend.service.DireccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios/direcciones")
public class DireccionController {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    @GetMapping
    public ResponseEntity<List<Direccion>> obtenerMisDirecciones(Authentication auth) {
        return ResponseEntity.ok(direccionService.obtenerPorUsuario(auth.getName()));
    }

    @PostMapping
    public ResponseEntity<?> agregarDireccion(Authentication auth, @RequestBody Direccion direccion) {
        try {
            return ResponseEntity.ok(direccionService.agregarDireccion(auth.getName(), direccion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDireccion(Authentication auth, @PathVariable Long id) {
        try {
            direccionService.eliminarDireccion(auth.getName(), id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}