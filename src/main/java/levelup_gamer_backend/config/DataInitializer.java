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
                        if (categoriaRepository.count() == 0) {
                                crearCategoria(categoriaRepository, "juegos");
                                crearCategoria(categoriaRepository, "accesorios");
                                crearCategoria(categoriaRepository, "consolas");
                        }

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
                                                .nombre("Fuentealba Candia")
                                                .apellido("Amargo Universo")
                                                .rut("9.886.921-2")
                                                .email("vendedor@vendedor.cl")
                                                .password(passwordEncoder.encode("vendedor"))
                                                .rol("Vendedor")
                                                .build();
                                usuarioRepository.save(vendedor);
                        }

                        if (productoRepository.count() == 0) {
                                Categoria catJuegos = categoriaRepository.findByNombre("juegos").orElseThrow();

                                crearProducto(productoRepository, "JM001", "Catan",
                                                "Un clásico juego de estrategia donde compites por colonizar la isla de Catan. Reúne recursos, comercia y construye para ser el primero en alcanzar 10 puntos de victoria.",
                                                29990, 20, 5,
                                                "https://dojiw2m9tvv09.cloudfront.net/10102/product/X_catan9477.jpg?43&time=1757334820",
                                                catJuegos,
                                                "[\"Juego de estrategia y gestión de recursos.\", \"Alta rejugabilidad gracias a su tablero modular.\", \"Fomenta la negociación y el comercio entre jugadores.\", \"Objetivo: Ser el primero en alcanzar 10 Puntos de Victoria.\"]",
                                                "{\"Detalles del Juego\": {\"Tipo de Juego\": \"Estrategia, Negociación\", \"N° de Jugadores\": \"3 a 4 jugadores (ampliable con expansiones)\", \"Edad Recomendada\": \"10+ años\", \"Duración\": \"60 - 90 minutos aprox.\"}, \"Contenido de la Caja\": {\"Hexágonos de Terreno\": \"19\", \"Piezas de Marco (Mar)\": \"6\", \"Cartas de Materia Prima\": \"95\", \"Cartas de Desarrollo\": \"25\", \"Fichas Numeradas\": \"18\", \"Figuras\": \"16 ciudades, 20 poblados, 60 caminos\", \"Dados\": \"2\", \"Figura de Ladrón\": \"1\"}}");

                                crearProducto(productoRepository, "JM002", "Carcassonne",
                                                "Un juego de colocación de fichas donde los jugadores construyen el paisaje medieval de la ciudad francesa de Carcasona, casilla a casilla.",
                                                24990, 15, 5,
                                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ0QZhisa2tKRa2YTmjEjXPBMwT3DkYtKgcuQ&s",
                                                catJuegos,
                                                "[\"Juego táctico de colocación de losetas.\", \"Reglas sencillas, ideal para familias y nuevos jugadores.\", \"Construye un mapa diferente en cada partida.\", \"Incluye la mini-expansión 'El Río' (en ediciones recientes).\"]",
                                                "{\"Detalles del Juego\": {\"Tipo de Juego\": \"Colocación de Losetas\", \"N° de Jugadores\": \"2 a 5 jugadores\", \"Edad Recomendada\": \"7+ años\", \"Duración\": \"35 - 45 minutos aprox.\"}, \"Contenido de la Caja (Edición Estándar)\": {\"Losetas de Terreno\": \"72\", \"Seguidores (Meeples)\": \"40 (8 en 5 colores)\", \"Tablero de Puntuación\": \"1\", \"Reglamento\": \"1\"}}");

                                Categoria catAccesorios = categoriaRepository.findByNombre("accesorios").orElseThrow();

                                crearProducto(productoRepository, "AC001", "Controlador Xbox Series X",
                                                "Experimenta el diseño modernizado del control inalámbrico de Xbox, con superficies esculpidas y geometría refinada para una mayor comodidad.",
                                                59990, 25, 5,
                                                "https://http2.mlstatic.com/D_NQ_NP_851883-MLA54692335944_032023-O.webp",
                                                catAccesorios,
                                                "[\"Diseño ergonómico modernizado con agarre texturizado.\", \"Botón 'Compartir' dedicado para capturar y compartir contenido.\", \"Pad direccional (D-pad) híbrido para mayor precisión.\", \"Tecnología Xbox Wireless y Bluetooth® para jugar en consolas, PC y móviles.\"]",
                                                "{\"Conectividad\": {\"Xbox\": \"Tecnología Xbox Wireless\", \"PC y Móvil\": \"Bluetooth® Low Energy\", \"Cable\": \"Puerto USB-C\"}, \"Compatibilidad\": {\"Consolas\": \"Xbox Series X, Xbox Series S, Xbox One\", \"Sistemas Operativos\": \"Windows 10/11, Android, iOS\"}, \"Audio\": {\"Jack de audio\": \"Conector estéreo de 3,5 mm\"}, \"Batería\": {\"Tipo\": \"Pilas AA (incluidas)\", \"Duración\": \"Hasta 40 horas (variable)\"}}");

                                crearProducto(productoRepository, "AC002", "Auriculares HyperX Cloud II",
                                                "Famosos por su comodidad, los HyperX Cloud II ofrecen un sonido envolvente virtual 7.1. Cuentan con almohadillas de espuma viscoelástica.",
                                                79990, 10, 3,
                                                "https://http2.mlstatic.com/D_NQ_NP_719345-MLU77945147420_082024-O.webp",
                                                catAccesorios,
                                                "[\"Sonido envolvente virtual 7.1.\", \"Caja de control de audio avanzada USB.\", \"Comodidad legendaria de espuma viscoelástica HyperX.\", \"Estructura de aluminio resistente.\", \"Micrófono desmontable con cancelación de ruido.\"]",
                                                "{\"Auriculares\": {\"Transductor\": \"Dinámico de 53 mm con imanes de neodimio\", \"Principio de funcionamiento\": \"Cerrado\", \"Respuesta de frecuencia\": \"15 Hz-25 000 Hz\", \"Impedancia nominal\": \"60 Ω por sistema\", \"Acoplamiento auditivo\": \"Circumaural\"}, \"Micrófono\": {\"Tipo\": \"Condensador (electret trasero)\", \"Patrón polar\": \"Cardioide\", \"Respuesta de frecuencia\": \"50-18 000 Hz\", \"Conexión\": \"Conector mini estéreo (3,5 mm)\"}, \"Conexión\": {\"Tipo\": \"Conector estéreo jack mini (3,5 mm), USB (con caja de control)\"}}");

                                crearProducto(productoRepository, "SG001", "Silla Gamer Secretlab Titan",
                                                "Diseñada para el máximo confort, con soporte ergonómico y personalización ajustable. La serie TITAN Evo combina múltiples tecnologías patentadas.",
                                                349990, 8, 2,
                                                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_eJAGG3mPro93cQDCHvq5yis6rEBVfxar3SGGGO3Huic3NRc6l4pv5gYTEpZXS2N5JaI&usqp=CAU",
                                                catAccesorios,
                                                "[\"Sistema de soporte lumbar L-ADAPT™ de 4 posiciones.\", \"Almohada magnética para la cabeza de espuma viscoelástica con gel refrigerante.\", \"Reposabrazos 4D metálicos con sistema de reemplazo CloudSwap™.\", \"Asiento ergonómico esculpido 'Pebble'.\", \"Reclinación de respaldo completo de 165°.\"]",
                                                "{\"Materiales\": {\"Tapicería\": \"Piel sintética Secretlab NEO™ (o tela SoftWeave® Plus)\", \"Espuma\": \"Espuma de curado en frío Secretlab\", \"Estructura\": \"Acero\", \"Base de ruedas\": \"Aleación de aluminio (ADC12)\"}, \"Ajustes (Tamaño Regular)\": {\"Altura recomendada\": \"170 - 189 cm\", \"Peso recomendado\": \"< 100 kg\", \"Carga máxima\": \"130 kg\", \"Reclinación\": \"85 - 165°\", \"Pistón hidráulico\": \"Clase 4\"}, \"Garantía\": {\"Estándar\": \"Garantía de 5 años (sujeto a extensión)\"}}");

                                crearProducto(productoRepository, "MS001", "Mouse Gamer Logitech G502 HERO",
                                                "Con sensor de alta precisión y botones personalizables para un control preciso. Diseñado para un rendimiento avanzado.",
                                                49990, 30, 10,
                                                "https://http2.mlstatic.com/D_NQ_NP_657872-MLU70840166924_082023-O.webp",
                                                catAccesorios,
                                                "[\"Sensor HERO 25K con seguimiento de precisión de hasta 25.600 dpi.\", \"11 botones programables y rueda con dos modos (desplazamiento superrápido).\", \"Peso ajustable: incluye cinco pesas reposicionables de 3,6 g.\", \"Tecnología LIGHTSYNC para iluminación RGB totalmente personalizable.\", \"Memoria integrada para guardar hasta 5 perfiles de configuración.\"]",
                                                "{\"Dimensiones Físicas\": {\"Altura\": \"132 mm\", \"Ancho\": \"75 mm\", \"Profundidad\": \"40 mm\", \"Peso\": \"121 g (sólo mouse)\", \"Pesos adicionales\": \"hasta 18 g (5 x 3.6 g)\", \"Longitud del cable\": \"2,1 m\"}, \"Seguimiento\": {\"Sensor\": \"HERO™ 25K\", \"Resolución\": \"100 – 25.600 dpi\", \"Aceleración máx.\": \"> 40 G\", \"Velocidad máx.\": \"> 400 IPS\"}, \"Respuesta\": {\"Formato de datos USB\": \"16 bits/eje\", \"Velocidad de respuesta USB\": \"1000 Hz (1 ms)\", \"Microprocesador\": \"ARM de 32 bits\"}, \"Durabilidad\": {\"Botones principales\": \"50 millones de clics\", \"Pies de PTFE\": \"> 250 km\"}, \"Requisitos\": {\"Sistema operativo\": \"Windows 7+, macOS 10.11+, Chrome OS\"}}");

                                crearProducto(productoRepository, "MP001", "Mousepad Razer Goliathus",
                                                "Área de juego amplia con iluminación RGB personalizable y superficie suave. Optimizado para todos los sensores y sensibilidades.",
                                                29990, 18, 5,
                                                "https://assets2.razerzone.com/images/pnx.assets/b761ba62aece1bcec7a7d9c998177cb9/razer-goliathus-chroma-3xl-ogimage_1200x630.webp",
                                                catAccesorios,
                                                "[\"Superficie de tela microtexturizada.\", \"Iluminación Razer Chroma™ con 16.8 millones de opciones de color.\", \"Equilibrado para velocidad y control.\", \"Base de goma antideslizante.\", \"Captura de cable (en versión Chroma).\"]",
                                                "{\"Dimensiones (Estándar Chroma)\": {\"Largo\": \"355 mm\", \"Ancho\": \"255 mm\", \"Altura\": \"3 mm\", \"Peso (aprox)\": \"230 g\"}, \"Dimensiones (Extended Chroma)\": {\"Largo\": \"920 mm\", \"Ancho\": \"294 mm\", \"Altura\": \"3 mm\"}, \"Características\": {\"Superficie\": \"Tela microtexturizada\", \"Base\": \"Goma antideslizante\", \"Iluminación\": \"Razer Chroma™ RGB\"}}");

                                crearProducto(productoRepository, "PP001", "Polera Gamer Personalizada",
                                                "Camiseta cómoda y estilizada, con la posibilidad de personalizarla con tu gamer tag o el logo de tu equipo favorito.",
                                                14990, 50, 10,
                                                "https://i.imgur.com/yX3aL8p.jpeg",
                                                catAccesorios,
                                                "[\"100% Algodón de alta densidad.\", \"Corte moderno y cómodo.\", \"Estampado de alta durabilidad (DTG o vinilo).\", \"Personalizable (consultar opciones).\"]",
                                                "{\"Material\": {\"Tela\": \"100% Algodón peinado\", \"Peso de tela\": \"180 g/m²\"}, \"Cuidados\": {\"Lavado\": \"Lavar con agua fría, al reverso\", \"Secado\": \"No usar secadora\", \"Planchado\": \"No planchar sobre el estampado\"}}");

                                Categoria catConsolas = categoriaRepository.findByNombre("consolas").orElseThrow();

                                crearProducto(productoRepository, "CO001", "PlayStation 5",
                                                "La consola de última generación de Sony, con gráficos impresionantes y carga ultrarrápida gracias a su SSD.",
                                                549990, 5, 2,
                                                "https://http2.mlstatic.com/D_Q_NP_883946-MLA79964406701_102024-O.webp",
                                                catConsolas,
                                                "[\"SSD de ultra alta velocidad para cargas casi instantáneas.\", \"Trazado de rayos (Ray Tracing) para un realismo increíble.\", \"Salida de video 4K a 120 Hz (requiere TV compatible).\", \"Tecnología de audio 3D 'Tempest' AudioTech.\", \"Incluye control inalámbrico DualSense.\"]",
                                                "{\"Procesamiento\": {\"CPU\": \"AMD Ryzen™ Zen 2 (8 núcleos/16 hilos, hasta 3.5GHz)\", \"GPU\": \"AMD Radeon RDNA 2 (10.3 TFLOPS, hasta 2.23 GHz)\", \"Memoria\": \"16 GB GDDR6\", \"Ancho de banda\": \"448 GB/s\"}, \"Almacenamiento\": {\"Tipo\": \"SSD NVMe personalizado\", \"Capacidad\": \"825 GB (Estándar) o 1 TB (Slim)\", \"Ancho de banda E/S\": \"5.5 GB/s (Sin formato)\"}, \"Video y Audio\": {\"Salida de video\": \"Soporte para 4K 120Hz, 8K, VRR (HDMI 2.1)\", \"Audio\": \"Tempest 3D AudioTech\"}, \"Unidad Óptica\": {\"Tipo\": \"Ultra HD Blu-ray 4K (en versión con disco)\"}}");

                                crearProducto(productoRepository, "CG001", "PC Gamer ASUS ROG Strix",
                                                "Un potente equipo diseñado para los gamers más exigentes. Equipado con procesadores de alto rendimiento y gráficos NVIDIA GeForce RTX.",
                                                1299990, 3, 1,
                                                "https://www.asus.com/media/Odin/Websites/global/Series/52.png",
                                                catConsolas,
                                                "[\"Gráficos NVIDIA® GeForce RTX™ serie 5000.\", \"Procesador AMD Ryzen™ 7 septima generación.\", \"Sistema de refrigeración inteligente ROG.\", \"Iluminación Aura Sync personalizable.\"]",
                                                "{\"Ejemplo (Modelo G15)\": {\"Procesador\": \"AMD Ryzen™ 7 7700X (8 núcleos/16 hilos)\", \"Gráficos\": \"NVIDIA® GeForce RTX™ 5070 12GB GDDR6\", \"Memoria\": \"16GB DDR4 SO-DIMM (Max 32GB)\", \"Almacenamiento\": \"512GB M.2 NVMe™ PCIe® 3.0 SSD\", \"Puertos\": \"1x USB 3.2 Gen 2 Tipo-C, 3x USB 3.2 Gen 1 Tipo-A, 1x HDMI 2.1\", \"Redes\": \"Wi-Fi 6 (802.11ax), Bluetooth® 5.1\", \"Sistema Operativo\": \"Windows 11 Home\"}}");
                        }

                        if (blogPostRepository.count() == 0) {
                                crearBlog(blogPostRepository,
                                                "PlayStation anuncia los 8 juegos gratis de septiembre de 2025",
                                                LocalDate.of(2025, 9, 10), "/img/Play plus.jpg",
                                                "El Catálogo de PS Plus Extra y Premium se actualiza el 16 de septiembre...");
                                crearBlog(blogPostRepository,
                                                "¡Madrid y Barcelona dominan! EA SPORTS FC 26 anuncia los mejores jugadores",
                                                LocalDate.of(2025, 9, 10), "/img/FC26.jpg",
                                                "La Semana de las Valoraciones de EA SPORTS FC 26 pone el foco en estrellas...");
                                crearBlog(blogPostRepository,
                                                "NVIDIA RTX Remix recibe un sistema avanzado de partículas",
                                                LocalDate.of(2025, 9, 9), "/img/RTXs.jpg",
                                                "NVIDIA ha anunciado una nueva actualización de RTX Remix...");
                                crearBlog(blogPostRepository,
                                                "Jugadores temen que la próxima consola de Xbox sea demasiado cara",
                                                LocalDate.of(2025, 9, 20),
                                                "https://i0.wp.com/levelup.buscafs.com/2025/09/Precio-Xbox.jpg?fit=1225,610&quality=75&strip=all",
                                                "Microsoft subió el precio de Xbox Series X|S una vez más...");
                                crearBlog(blogPostRepository, "Hay un nuevo shooter táctico gratis petándolo en Steam",
                                                LocalDate.of(2025, 9, 22),
                                                "https://i.blogs.es/0683a3/maxresdefault-1-_upscayl_2x_remacri-4x-1-/1200_800.jpeg",
                                                "Arena Breakout: Infinite. El título de Morefun Studios...");
                                crearBlog(blogPostRepository, "PlayStation 5: Potencia de Nueva Generación",
                                                LocalDate.of(2024, 10, 12), "/img/Play 5.jpg",
                                                "La PS5 no es solo una consola, es un centro de entretenimiento inmersivo.");
                                crearBlog(blogPostRepository, "Mando Inalámbrico Xbox: Precisión y Comodidad",
                                                LocalDate.of(2024, 10, 8), "/img/Mando.jpg",
                                                "Microsoft ha perfeccionado un diseño ya legendario.");
                                crearBlog(blogPostRepository, "HyperX Cloud II: Escucha Cada Detalle",
                                                LocalDate.of(2024, 10, 3), "/img/Audifonos.jpg",
                                                "Un clásico por una razón. Los HyperX Cloud II son famosos.");
                                crearBlog(blogPostRepository, "Logitech G502 HERO: El Rey de la Precisión",
                                                LocalDate.of(2024, 9, 25), "/img/Mause.jpg",
                                                "El G502 HERO es un ícono en el mundo del gaming.");
                                crearBlog(blogPostRepository, "Secretlab TITAN: El Trono Definitivo del Gamer",
                                                LocalDate.of(2024, 9, 18), "/img/Silla.jpg",
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
                        Integer precio, Integer stock, Integer critico, String img, Categoria cat,
                        String featuresJson, String specsJson) {
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
                                        .features(featuresJson)
                                        .specifications(specsJson)
                                        .build());
                }
        }

        @SuppressWarnings("null")
        private void crearBlog(BlogPostRepository repo, String title, LocalDate date, String img, String content) {
                repo.save(BlogPost.builder().title(title).date(date).image(img).content(content).build());
        }
}