package levelup_gamer_backend.service.impl;

import levelup_gamer_backend.entity.Usuario;
import levelup_gamer_backend.repository.UsuarioRepository;
import levelup_gamer_backend.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }
        if (usuarioRepository.existsByRut(usuario.getRut())) {
            throw new RuntimeException("El RUT ya está registrado.");
        }
        
        // Cifrar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("Cliente");
        }
        
        return usuarioRepository.save(usuario);
    }
    
    @Override
    @Transactional
    public Usuario actualizarUsuario(Usuario usuario) {
        Usuario existingUser = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado para actualización."));
        
        // Si la contraseña fue modificada, codificarla.
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty() && !passwordEncoder.matches(usuario.getPassword(), existingUser.getPassword())) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            usuario.setPassword(existingUser.getPassword());
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorRut(String rut) {
        return usuarioRepository.findByRut(rut);
    }
    
    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    // Lógica para inicializar roles (Admin y Vendedor)
    @Override
    @Transactional
    public void inicializarAdminYVendedor() {
        if (usuarioRepository.findByEmail("admin@admin.cl").isEmpty()) {
            Usuario admin = Usuario.builder()
                .nombre("Admin")
                .apellido("LevelUp")
                .rut("12345678-9")
                .email("admin@admin.cl")
                .password(passwordEncoder.encode("admin")) // Contraseña: admin
                .rol("Administrador")
                .build();
            usuarioRepository.save(admin);
        }
        
        if (usuarioRepository.findByEmail("vendedor@vendedor.cl").isEmpty()) {
             Usuario vendedor = Usuario.builder()
                .nombre("Vendedor")
                .apellido("LevelUp")
                .rut("11111111-1")
                .email("vendedor@vendedor.cl")
                .password(passwordEncoder.encode("vendedor")) // Contraseña: vendedor
                .rol("Vendedor")
                .build();
            usuarioRepository.save(vendedor);
        }
    }
}