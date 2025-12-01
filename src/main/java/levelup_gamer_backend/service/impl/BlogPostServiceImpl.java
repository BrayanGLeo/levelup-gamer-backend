package levelup_gamer_backend.service.impl;

import levelup_gamer_backend.entity.BlogPost;
import levelup_gamer_backend.repository.BlogPostRepository;
import levelup_gamer_backend.service.BlogPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("null")
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
    }
}