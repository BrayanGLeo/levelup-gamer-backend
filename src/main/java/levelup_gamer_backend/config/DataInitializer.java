package levelup_gamer_backend.config;

import levelup_gamer_backend.entity.*;
import levelup_gamer_backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

        @Bean
        @Transactional
        @SuppressWarnings("null")
        public CommandLineRunner initData(
                        UsuarioRepository usuarioRepository,
                        ProductoRepository productoRepository,
                        CategoriaRepository categoriaRepository,
                        BlogPostRepository blogPostRepository,
                        DireccionRepository direccionRepository,
                        PasswordEncoder passwordEncoder) {
                return args -> {
                        // 1. INICIALIZAR CATEGORÍAS
                        if (categoriaRepository.count() == 0) {
                                crearCategoria(categoriaRepository, "juegos");
                                crearCategoria(categoriaRepository, "accesorios");
                                crearCategoria(categoriaRepository, "consolas");
                        }

                        // 2. INICIALIZAR USUARIOS
                        if (usuarioRepository.count() == 0) {
                                Usuario admin = Usuario.builder()
                                                .nombre("Admin")
                                                .apellido("LevelUp")
                                                .rut("12345678-9")
                                                .email("admin@admin.cl")
                                                .password(passwordEncoder.encode("admin"))
                                                .rol("Administrador")
                                                .build();
                                usuarioRepository.save(admin);

                                Direccion dirAdmin = Direccion.builder()
                                                .alias("Casa (Ejemplo SP Digital)")
                                                .region("Biobío")
                                                .comuna("Hualpén")
                                                .calle("Pasaje Greenlandia")
                                                .numero("#2457")
                                                .depto("")
                                                .recibeNombre("Brayan")
                                                .recibeApellido("Godoy")
                                                .recibeTelefono("+56978979900")
                                                .usuario(admin)
                                                .build();
                                direccionRepository.save(dirAdmin);

                                Usuario vendedor = Usuario.builder()
                                                .nombre("Vendedor")
                                                .apellido("LevelUp")
                                                .rut("11111111-1")
                                                .email("vendedor@vendedor.cl")
                                                .password(passwordEncoder.encode("vendedor"))
                                                .rol("Vendedor")
                                                .build();
                                usuarioRepository.save(vendedor);
                        }

                        // 3. INICIALIZAR PRODUCTOS
                        if (productoRepository.count() == 0) {
                                // Juegos
                                Categoria catJuegos = categoriaRepository.findByNombre("juegos").orElseThrow();
                                crearProducto(productoRepository, "JM001", "Catan",
                                                "Un clásico juego de estrategia donde compites por colonizar la isla de Catan. Reúne recursos, comercia y construye.",
                                                29990, 20, 5,
                                                "https://dojiw2m9tvv09.cloudfront.net/10102/product/X_catan9477.jpg?43&time=1757334820",
                                                catJuegos);

                                crearProducto(productoRepository, "JM002", "Carcassonne",
                                                "Un juego de colocación de fichas donde los jugadores construyen el paisaje medieval.",
                                                24990, 15, 5,
                                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ0QZhisa2tKRa2YTmjEjXPBMwT3DkYtKgcuQ&s",
                                                catJuegos);

                                // Accesorios
                                Categoria catAccesorios = categoriaRepository.findByNombre("accesorios").orElseThrow();
                                crearProducto(productoRepository, "AC001", "Controlador Xbox Series X",
                                                "Experimenta el diseño modernizado del control inalámbrico de Xbox.",
                                                59990, 25, 5,
                                                "https://http2.mlstatic.com/D_NQ_NP_851883-MLA54692335944_032023-O.webp",
                                                catAccesorios);

                                crearProducto(productoRepository, "AC002", "Auriculares HyperX Cloud II",
                                                "Famosos por su comodidad, los HyperX Cloud II ofrecen un sonido envolvente.",
                                                79990, 10, 3,
                                                "https://http2.mlstatic.com/D_NQ_NP_719345-MLU77945147420_082024-O.webp",
                                                catAccesorios);

                                crearProducto(productoRepository, "SG001", "Silla Gamer Secretlab Titan",
                                                "Diseñada para el máximo confort, con soporte ergonómico.",
                                                349990, 8, 2,
                                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_eJAGG3mPro93cQDCHvq5yis6rEBVfxar3SGGGO3Huic3NRc6l4pv5gYTEpZXS2N5JaI&usqp=CAU",
                                                catAccesorios);

                                crearProducto(productoRepository, "MS001", "Mouse Gamer Logitech G502 HERO",
                                                "Con sensor de alta precisión y botones personalizables.",
                                                49990, 30, 10,
                                                "https://http2.mlstatic.com/D_NQ_NP_657872-MLU70840166924_082023-O.webp",
                                                catAccesorios);

                                crearProducto(productoRepository, "MP001", "Mousepad Razer Goliathus",
                                                "Área de juego amplia con iluminación RGB personalizable.",
                                                29990, 18, 5,
                                                "https://assets2.razerzone.com/images/pnx.assets/b761ba62aece1bcec7a7d9c998177cb9/razer-goliathus-chroma-3xl-ogimage_1200x630.webp",
                                                catAccesorios);

                                crearProducto(productoRepository, "PP001", "Polera Gamer Personalizada",
                                                "Camiseta cómoda y estilizada, personalizable.",
                                                14990, 50, 10,
                                                "https://i.imgur.com/yX3aL8p.jpeg", catAccesorios);

                                // Consolas
                                Categoria catConsolas = categoriaRepository.findByNombre("consolas").orElseThrow();
                                crearProducto(productoRepository, "CO001", "PlayStation 5",
                                                "La consola de última generación de Sony, con gráficos impresionantes.",
                                                549990, 5, 2,
                                                "https://http2.mlstatic.com/D_Q_NP_883946-MLA79964406701_102024-O.webp",
                                                catConsolas);

                                crearProducto(productoRepository, "CG001", "PC Gamer ASUS ROG Strix",
                                                "Un potente equipo diseñado para los gamers más exigentes.",
                                                1299990, 3, 1,
                                                "https://www.asus.com/media/Odin/Websites/global/Series/52.png",
                                                catConsolas);
                        }

                        // 4. INICIALIZAR BLOG
                        if (blogPostRepository.count() == 0) {
                                crearBlog(blogPostRepository,
                                                "PlayStation anuncia los 8 juegos gratis de septiembre de 2025",
                                                LocalDate.of(2025, 9, 10),
                                                "/img/Play plus.jpg",
                                                "El Catálogo de PS Plus Extra y Premium se actualiza el 16 de septiembre...");

                                crearBlog(blogPostRepository,
                                                "¡Madrid y Barcelona dominan! EA SPORTS FC 26 anuncia los mejores jugadores",
                                                LocalDate.of(2025, 9, 10),
                                                "/img/FC26.jpg",
                                                "La Semana de las Valoraciones de EA SPORTS FC 26 pone el foco en estrellas...");

                                crearBlog(blogPostRepository,
                                                "NVIDIA RTX Remix recibe un sistema avanzado de partículas",
                                                LocalDate.of(2025, 9, 9),
                                                "/img/RTXs.jpg",
                                                "NVIDIA ha anunciado una nueva actualización de RTX Remix...");

                                crearBlog(blogPostRepository,
                                                "Jugadores temen que la próxima consola de Xbox sea demasiado cara",
                                                LocalDate.of(2025, 9, 20),
                                                "https://i0.wp.com/levelup.buscafs.com/2025/09/Precio-Xbox.jpg?fit=1225,610&quality=75&strip=all",
                                                "Microsoft subió el precio de Xbox Series X|S una vez más...");

                                crearBlog(blogPostRepository,
                                                "Hay un nuevo shooter táctico gratis petándolo en Steam",
                                                LocalDate.of(2025, 9, 22),
                                                "https://i.blogs.es/0683a3/maxresdefault-1-_upscayl_2x_remacri-4x-1-/1200_800.jpeg",
                                                "Arena Breakout: Infinite. El título de Morefun Studios...");

                                crearBlog(blogPostRepository,
                                                "PlayStation 5: Potencia de Nueva Generación",
                                                LocalDate.of(2024, 10, 12),
                                                "/img/Play 5.jpg",
                                                "La PS5 no es solo una consola, es un centro de entretenimiento inmersivo.");

                                crearBlog(blogPostRepository,
                                                "Mando Inalámbrico Xbox: Precisión y Comodidad",
                                                LocalDate.of(2024, 10, 8),
                                                "/img/Mando.jpg",
                                                "Microsoft ha perfeccionado un diseño ya legendario.");

                                crearBlog(blogPostRepository,
                                                "HyperX Cloud II: Escucha Cada Detalle",
                                                LocalDate.of(2024, 10, 3),
                                                "/img/Audifonos.jpg",
                                                "Un clásico por una razón. Los HyperX Cloud II son famosos.");

                                crearBlog(blogPostRepository,
                                                "Logitech G502 HERO: El Rey de la Precisión",
                                                LocalDate.of(2024, 9, 25),
                                                "/img/Mause.jpg",
                                                "El G502 HERO es un ícono en el mundo del gaming.");

                                crearBlog(blogPostRepository,
                                                "Secretlab TITAN: El Trono Definitivo del Gamer",
                                                LocalDate.of(2024, 9, 18),
                                                "/img/Silla.jpg",
                                                "La Secretlab TITAN Evo es la inversión definitiva.");
                        }
                };
        }

        @SuppressWarnings("null")
        private void crearCategoria(CategoriaRepository repo, String nombre) {
                if (repo.findByNombre(nombre).isEmpty()) {
                        repo.save(Categoria.builder().nombre(nombre).build());
                }
        }

        @SuppressWarnings("null")
        private void crearProducto(ProductoRepository repo, String codigo, String nombre, String desc,
                        Integer precio, Integer stock, Integer critico, String img, Categoria cat) {
                if (repo.findById(codigo).isEmpty()) {
                        repo.save(Producto.builder()
                                        .codigo(codigo)
                                        .nombre(nombre)
                                        .descripcion(desc)
                                        .precio(precio)
                                        .stock(stock)
                                        .stockCritico(critico)
                                        .imagenUrl(img)
                                        .categoria(cat)
                                        .build());
                }
        }

        @SuppressWarnings("null")
        private void crearBlog(BlogPostRepository repo, String title, LocalDate date, String img, String content) {
                repo.save(BlogPost.builder()
                                .title(title)
                                .date(date)
                                .image(img)
                                .content(content)
                                .build());
        }
}