package levelup_gamer_backend.service;

import levelup_gamer_backend.entity.BlogPost;
import java.util.List;
import java.util.Optional;

public interface BlogPostService {
    List<BlogPost> obtenerTodos();

    Optional<BlogPost> obtenerPorId(Long id);

    void inicializarDatosDemo();
}