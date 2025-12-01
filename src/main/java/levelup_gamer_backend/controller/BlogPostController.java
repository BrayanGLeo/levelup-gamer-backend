package levelup_gamer_backend.controller;

import levelup_gamer_backend.entity.BlogPost;
import levelup_gamer_backend.service.BlogPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
        this.blogPostService.inicializarDatosDemo();
    }

    @GetMapping
    public ResponseEntity<List<BlogPost>> obtenerTodos() {
        return ResponseEntity.ok(blogPostService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> obtenerPorId(@PathVariable Long id) {
        return blogPostService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}