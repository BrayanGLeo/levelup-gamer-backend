package levelup_gamer_backend.controller;

import levelup_gamer_backend.dto.AuthRequest;
import levelup_gamer_backend.dto.RegisterRequest;
import levelup_gamer_backend.entity.Usuario;
import levelup_gamer_backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            UsuarioService usuarioService,
            AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            Usuario nuevoUsuario = Usuario.builder()
                    .nombre(request.getNombre())
                    .apellido(request.getApellido())
                    .rut(request.getRut())
                    .email(request.getEmail().toLowerCase())
                    .password(request.getPassword())
                    .rol("Cliente")
                    .build();

            usuarioService.registrarUsuario(nuevoUsuario);
            return ResponseEntity.ok("Registro exitoso. Ahora puede iniciar sesión.");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail().toLowerCase(),
                            request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Usuario usuario = usuarioService.obtenerPorEmail(request.getEmail().toLowerCase())
                    .orElseThrow(() -> new RuntimeException("Error interno"));

            String responseBody = String.format("{\"nombre\":\"%s %s\", \"rol\":\"%s\"}",
                    usuario.getNombre(), usuario.getApellido(), usuario.getRol());
            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
        }
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfil(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }
        String email = authentication.getName();
        Optional<Usuario> optionalUsuario = usuarioService.obtenerPorEmail(email);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setPassword(null);
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PutMapping("/perfil")
    public ResponseEntity<?> actualizarPerfil(Authentication authentication, @RequestBody Usuario usuarioActualizado) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();
        Usuario usuario = usuarioService.obtenerPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());

        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
            usuario.setPassword(usuarioActualizado.getPassword());
        }

        usuarioService.actualizarUsuario(usuario);
        return ResponseEntity.ok("Perfil actualizado correctamente.");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }
}