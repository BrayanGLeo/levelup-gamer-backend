package levelup_gamer_backend.repository;

import levelup_gamer_backend.entity.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    List<Boleta> findByUsuarioEmailOrderByFechaCompraDesc(String email);

    @Query("SELECT MAX(b.numeroOrden) FROM Boleta b")
    Long findMaxNumeroOrden();
}