package levelup_gamer_backend.service.impl;

import levelup_gamer_backend.entity.BlogPost;
import levelup_gamer_backend.repository.BlogPostRepository;
import levelup_gamer_backend.service.BlogPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogPost> obtenerTodos() {
        return blogPostRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlogPost> obtenerPorId(Long id) {
        return blogPostRepository.findById(id);
    }

    @Override
    @Transactional
    public void inicializarDatosDemo() {
        if (blogPostRepository.count() == 0) {
            blogPostRepository.save(BlogPost.builder()
                    .title("PlayStation anuncia los 8 juegos gratis de septiembre de 2025")
                    .date(LocalDate.of(2025, 9, 10))
                    .image("/img/Play plus.jpg")
                    .content(
                            "El Catálogo de PS Plus Extra y Premium se actualiza el 16 de septiembre con una variedad de géneros: lucha libre, estrategia y supervivencia.")
                    .build());

            blogPostRepository.save(BlogPost.builder()
                    .title("¡Madrid y Barcelona dominan! EA SPORTS FC 26 anuncia los mejores jugadores")
                    .date(LocalDate.of(2025, 9, 10))
                    .image("/img/FC26.jpg")
                    .content(
                            "La Semana de las Valoraciones de EA SPORTS FC 26 pone el foco en estrellas como Mbappé, Lamine Yamal, Aitana Bonmatí y Alexia Putellas.")
                    .build());

            blogPostRepository.save(BlogPost.builder()
                    .title("NVIDIA RTX Remix recibe un sistema avanzado de partículas")
                    .date(LocalDate.of(2025, 9, 9))
                    .image("/img/RTXs.jpg")
                    .content(
                            "NVIDIA ha anunciado una nueva actualización de RTX Remix, su plataforma de modding para PC con GPU GeForce RTX.")
                    .build());
        }
    }
}