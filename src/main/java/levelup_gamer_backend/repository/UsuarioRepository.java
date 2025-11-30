package levelup_gamer_backend.repository;

import levelup_gamer_backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByRut(String rut);
    Boolean existsByEmail(String email);
    Boolean existsByRut(String rut);
}