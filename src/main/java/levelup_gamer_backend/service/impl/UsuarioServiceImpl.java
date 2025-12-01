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

        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()
                && !passwordEncoder.matches(usuario.getPassword(), existingUser.getPassword())) {
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
}