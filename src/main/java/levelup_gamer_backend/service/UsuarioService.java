package levelup_gamer_backend.service;

import levelup_gamer_backend.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    
    Usuario registrarUsuario(Usuario usuario);
    Usuario actualizarUsuario(Usuario usuario);
    List<Usuario> obtenerTodos();
    Optional<Usuario> obtenerPorEmail(String email);
    Optional<Usuario> obtenerPorRut(String rut);
    void eliminarPorId(Long id);
    
    void inicializarAdminYVendedor(); // Lógica de inicialización
}