package levelup_gamer_backend.controller;

import levelup_gamer_backend.entity.Usuario;
import levelup_gamer_backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Usuario> obtenerPorRut(@PathVariable String rut) {
        return usuarioService.obtenerPorRut(rut)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/rut/{rut}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable String rut, @RequestBody Usuario usuario) {
        if (!usuario.getRut().equals(rut)) {
            return ResponseEntity.badRequest().body("El RUT del cuerpo debe coincidir con el RUT de la URL.");
        }

        Usuario usuarioExistente = usuarioService.obtenerPorRut(rut)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado para actualización."));

        usuario.setId(usuarioExistente.getId());

        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/rut/{rut}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String rut) {
        Usuario usuario = usuarioService.obtenerPorRut(rut).orElse(null);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminarPorId(usuario.getId());
        return ResponseEntity.noContent().build();
    }
}