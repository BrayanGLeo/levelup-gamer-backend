package levelup_gamer_backend.repository;

import levelup_gamer_backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {
    List<Producto> findByCategoriaNombre(String nombreCategoria);

    List<Producto> findByStockLessThanEqual(Integer stockCritico);
}