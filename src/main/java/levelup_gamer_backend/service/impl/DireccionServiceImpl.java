package levelup_gamer_backend.service.impl;

import levelup_gamer_backend.entity.Direccion;
import levelup_gamer_backend.entity.Usuario;
import levelup_gamer_backend.repository.DireccionRepository;
import levelup_gamer_backend.repository.UsuarioRepository;
import levelup_gamer_backend.service.DireccionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@SuppressWarnings("null")
public class DireccionServiceImpl implements DireccionService {

    private final DireccionRepository direccionRepository;
    private final UsuarioRepository usuarioRepository;

    public DireccionServiceImpl(DireccionRepository direccionRepository, UsuarioRepository usuarioRepository) {
        this.direccionRepository = direccionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Direccion> obtenerPorUsuario(String email) {
        return direccionRepository.findByUsuarioEmail(email);
    }

    @Override
    @Transactional
    public Direccion agregarDireccion(String email, Direccion direccion) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        direccion.setUsuario(usuario);
        return direccionRepository.save(direccion);
    }

    @Override
    @Transactional
    public void eliminarDireccion(String email, Long idDireccion) {
        Direccion direccion = direccionRepository.findById(idDireccion)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        if (!direccion.getUsuario().getEmail().equals(email)) {
            throw new RuntimeException("No tiene permisos para eliminar esta dirección");
        }
        direccionRepository.delete(direccion);
    }
}