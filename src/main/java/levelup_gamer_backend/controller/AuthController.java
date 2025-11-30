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
            AuthenticationManager authenticationManager
    ) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        // Inicializar usuarios (Admin y Vendedor) al iniciar la app
        this.usuarioService.inicializarAdminYVendedor(); 
    }

    // Endpoint de REGISTRO (PÚBLICO)
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
            // Maneja errores de RUT o Email duplicado desde el servicio
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint de LOGIN (PÚBLICO)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // 1. Autenticar al usuario (esto genera la cookie de sesión JSESSIONID)
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail().toLowerCase(),
                    request.getPassword()
                )
            );
            
            // 2. Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Obtener el usuario para devolver la información (nombre y rol)
            Usuario usuario = usuarioService.obtenerPorEmail(request.getEmail().toLowerCase())
                    .orElseThrow(() -> new RuntimeException("Error interno al buscar usuario después de la autenticación."));

            // Retornamos un JSON con la data crucial para React (nombre completo y rol)
            String responseBody = String.format("{\"nombre\":\"%s %s\", \"rol\":\"%s\"}", 
                                                usuario.getNombre(), usuario.getApellido(), usuario.getRol());
            
            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            // Manejar errores de autenticación (credenciales inválidas)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
        }
    }
    
    // Endpoint para obtener el perfil completo (protegido por 'authenticated')
    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfil(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autenticado");
        }
        
        String email = authentication.getName(); // El email es el 'name' en la autenticación
        Optional<Usuario> optionalUsuario = usuarioService.obtenerPorEmail(email);

        // Uso de if/else para manejar la respuesta de Optional sin error de tipos
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setPassword(null); // No exponer la contraseña
            return ResponseEntity.ok(usuario); // Retorna 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"); // Retorna 404 NOT FOUND
        }
    }
    
    // Endpoint para cerrar sesión (PÚBLICO)
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Limpiar el contexto de seguridad (la cookie JSESSIONID expira automáticamente o debe ser eliminada por el cliente/servidor)
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }
}