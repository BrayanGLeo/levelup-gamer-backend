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
                                                "https://media.spdigital.cl/thumbnails/products/8u6wwtrx_be76f979_thumbnail_4096.jpg",
                                                catConsolas,
                                                "[\"Gráficos NVIDIA® GeForce RTX™ serie 5000.\", \"Procesador AMD Ryzen™ 7 septima generación.\", \"Sistema de refrigeración inteligente ROG.\", \"Iluminación Aura Sync personalizable.\"]",
                                                "{\"Ejemplo (Modelo G15)\": {\"Procesador\": \"AMD Ryzen™ 7 7700X (8 núcleos/16 hilos)\", \"Gráficos\": \"NVIDIA® GeForce RTX™ 5070 12GB GDDR6\", \"Memoria\": \"16GB DDR4 SO-DIMM (Max 32GB)\", \"Almacenamiento\": \"512GB M.2 NVMe™ PCIe® 3.0 SSD\", \"Puertos\": \"1x USB 3.2 Gen 2 Tipo-C, 3x USB 3.2 Gen 1 Tipo-A, 1x HDMI 2.1\", \"Redes\": \"Wi-Fi 6 (802.11ax), Bluetooth® 5.1\", \"Sistema Operativo\": \"Windows 11 Home\"}}");
                        }

                        if (blogPostRepository.count() == 0) {
                                crearBlog(blogPostRepository,
                                                "PlayStation anuncia los 8 juegos gratis de septiembre de 2025",
                                                LocalDate.of(2025, 9, 10), "/img/Play plus.jpg",
                                                "El Catálogo de PS Plus Extra y Premium se actualiza el 16 de septiembre con títulos como Psychonauts 2 y Stardew Valley.",
                                                """
                                                                Sony Interactive Entertainment ha desvelado la lista oficial de juegos que se unirán al catálogo de PlayStation Plus Extra y Premium a partir del próximo martes 16 de septiembre. Tras un mes de agosto cargado de sorpresas, la compañía nipona apuesta por una selección variada que mezcla narrativa profunda, acción cooperativa y simulación relajante, buscando satisfacer a todos los perfiles de jugadores en su plataforma.

                                                                El Plato Fuerte: Psychonauts 2
                                                                Encabezando la lista se encuentra 'Psychonauts 2', la aclamada secuela de Double Fine que combina plataformas ingeniosas con una historia conmovedora sobre la salud mental. Los jugadores acompañarán a Razputin "Raz" Aquato en un viaje psicodélico a través de mentes ajenas, enfrentándose a miedos y descubriendo secretos oscuros de la organización de espías psíquicos. El juego ha sido optimizado para PS5, ofreciendo tiempos de carga instantáneos y soporte para feedback háptico del DualSense, permitiendo sentir cada salto y poder psíquico.

                                                                Relajación Rural: Stardew Valley
                                                                Para los amantes de la gestión y la relajación, llega 'Stardew Valley'. Este fenómeno indie permite a los jugadores escapar a una granja heredada, cultivar, criar animales y entablar relaciones con los habitantes del pueblo. Es el título perfecto para desconectar después de un día largo. La versión que llega a Plus incluye la reciente actualización 1.7, que añade nuevas áreas explorables y eventos estacionales multijugador.

                                                                Innovación Visual: Viewfinder
                                                                Además, se suma 'Viewfinder', un innovador juego de puzles en primera persona donde puedes rediseñar la realidad tomando fotografías instantáneas. Es una experiencia visualmente impactante que desafía la percepción del jugador y ha ganado múltiples premios por su diseño artístico.

                                                                Lista completa de novedades para septiembre de 2025:
                                                                - Psychonauts 2 (PS4/PS5): Una aventura de plataformas que explora la mente humana.
                                                                - Stardew Valley (PS4): El simulador de granja definitivo.
                                                                - Viewfinder (PS5): Puzles que desafían la realidad.
                                                                - The Plucky Squire (PS5): Una aventura que salta entre 2D y 3D.
                                                                - Sniper Elite 5 (PS4/PS5): Acción táctica en la Segunda Guerra Mundial.
                                                                - SnowRunner (PS4/PS5): Simulación de conducción extrema en terrenos difíciles.
                                                                - Tales of Arise (PS4/PS5): Uno de los mejores JRPG de la década.
                                                                - Chants of Sennaar (PS4/PS5): Un juego de descifrar idiomas antiguos.

                                                                Estos títulos estarán disponibles para su descarga sin coste adicional para todos los suscriptores de los niveles Extra y Premium. Recuerda que también tienes hasta el 30 de septiembre para reclamar los juegos del nivel Essential de este mes, que incluyen 'Sifu', 'F1 24' y 'Hello Neighbor 2'. Sony sigue demostrando que su servicio de suscripción es uno de los pilares fundamentales de su estrategia para esta generación.
                                                                """);

                                crearBlog(blogPostRepository,
                                                "¡Madrid y Barcelona dominan! EA SPORTS FC 26 anuncia los mejores jugadores",
                                                LocalDate.of(2025, 9, 10), "/img/FC26.jpg",
                                                "La Semana de las Valoraciones de EA SPORTS FC 26 pone el foco en estrellas como Mbappé, Haaland y Bonmatí.",
                                                """
                                                                La espera ha terminado y la comunidad de FIFA (ahora EA SPORTS FC) está en llamas. EA SPORTS ha revelado las valoraciones oficiales de los 24 mejores jugadores y jugadoras de EA SPORTS FC 26, y como era de esperar, la rivalidad entre los gigantes de España domina la tabla, reflejando el estado actual del fútbol mundial.

                                                                El Trono Masculino
                                                                Kylian Mbappé (Real Madrid), Erling Haaland (Manchester City) y Rodri (Manchester City) comparten el trono con una valoración general de 91.
                                                                - Mbappé destaca por su ritmo endiablado (97), manteniéndose como el jugador más rápido del juego y letal en el contraataque.
                                                                - Haaland se consolida como el rematador definitivo con un tiro de 93 y un físico de 88, haciéndolo imparable en el área.
                                                                - Rodri, el cerebro del City, recibe un merecido reconocimiento a su visión de juego con un pase de 90 y defensa de 87.

                                                                Reinas del Fútbol
                                                                En el fútbol femenino, Aitana Bonmatí (FC Barcelona) lidera en solitario con un impresionante 91 de media, siendo la carta más técnica del juego gracias a su regate de 92. Le siguen muy de cerca sus compañeras Alexia Putellas y Caroline Graham Hansen, ambas con 90. El dominio del Barça Femení es absoluto en el top 10, reflejando su hegemonía en Europa tras ganar su cuarta Champions consecutiva.

                                                                Estrellas del Real Madrid y Sorpresas
                                                                El Real Madrid también posiciona fuerte a sus estrellas: Vinícius Jr. y Jude Bellingham alcanzan la media de 90, convirtiéndose en cartas 'meta' obligatorias para cualquier equipo de Ultimate Team debido a sus 5 estrellas de filigranas. Federico Valverde, con su versatilidad física y ritmo, sube a 88, siendo uno de los mediocampistas más completos ("Gullit Gang").

                                                                Sorpresas y cambios destacados:
                                                                - Lamine Yamal: Recibe la mejora más histórica de la franquicia, saltando a 86 de media tras su temporada consagratoria y ganar el Golden Boy.
                                                                - El Fin de una Era: Lionel Messi y Cristiano Ronaldo ven ajustadas sus medias a 88 y 86 respectivamente. Aunque siguen teniendo estadísticas técnicas de élite (tiro y pase), su ritmo ha bajado considerablemente, marcando el paso del tiempo.
                                                                - Premier League vs LaLiga: La liga inglesa sigue siendo la competición con más representantes en el Top 50, aunque LaLiga ha recortado distancias gracias a los fichajes galácticos del último verano.

                                                                Novedades Tácticas
                                                                EA SPORTS FC 26 no solo trae nuevos números; promete ser la entrega más táctica hasta la fecha con la introducción de 'FC IQ', un nuevo sistema que utiliza datos de partidos reales para revisar la inteligencia artificial de los jugadores sin balón. Esto significa que jugadores con alta inteligencia táctica como Modric o De Bruyne se posicionarán de manera mucho más efectiva. El juego estará disponible mundialmente el 27 de septiembre.
                                                                """);

                                crearBlog(blogPostRepository,
                                                "NVIDIA RTX Remix recibe un sistema avanzado de partículas",
                                                LocalDate.of(2025, 9, 9), "/img/RTXs.jpg",
                                                "NVIDIA ha anunciado una nueva actualización de RTX Remix que permite partículas con trazado de rayos completo.",
                                                """
                                                                NVIDIA ha lanzado una actualización masiva y revolucionaria para su plataforma de modding RTX Remix, introduciendo una característica que la comunidad llevaba años solicitando: un sistema de partículas avanzado con trazado de rayos completo (Path Tracing).

                                                                El Problema de los Clásicos
                                                                Hasta ahora, RTX Remix permitía remasterizar juegos clásicos de DirectX 8 y 9 añadiendo iluminación global, sombras realistas y texturas mejoradas por IA. Sin embargo, los efectos de partículas originales (humo, fuego, chispas, hechizos mágicos) a menudo desentonaban terriblemente. Eran sprites planos 2D que no reaccionaban a la nueva iluminación, rompiendo la inmersión de un remaster por lo demás perfecto.

                                                                La Solución Volumétrica
                                                                Con esta actualización, los modders pueden reemplazar esos efectos planos por emisores de partículas volumétricas que emiten luz real y reaccionan físicamente al entorno. Ya no son simples texturas pegadas en pantalla, sino elementos 3D simulados.

                                                                Características clave de la actualización:
                                                                - Iluminación dinámica: Las chispas de una explosión o una bola de fuego ahora iluminarán dinámicamente una habitación oscura, rebotando la luz en las paredes en tiempo real.
                                                                - Sombras propias y suaves: El humo denso ahora puede proyectar sombras sobre sí mismo y sobre el escenario, añadiendo una profundidad increíble a las escenas de batalla o incendios.
                                                                - Reflejos precisos: Las partículas se reflejan con precisión en superficies como agua, cristal o metal pulido. Imagina ver los fuegos artificiales reflejados perfectamente en un charco en el suelo.
                                                                - Herramientas simplificadas: NVIDIA ha incluido una biblioteca de "prefabs" para fuego, humo, chispas eléctricas y lluvia que los modders pueden arrastrar y soltar sin necesidad de programar shaders complejos.

                                                                Juegos Beneficiados
                                                                Títulos legendarios como 'Half-Life 2 RTX' y 'Portal: Prelude RTX' serán los primeros en integrar estas mejoras oficialmente. Sin embargo, la comunidad ya está trabajando en implementaciones para clásicos de culto como 'Need for Speed: Underground 2' (donde las luces de neón y el humo de los derrapes lucirán increíbles) y 'Max Payne', prometiendo revitalizar estos títulos con una fidelidad visual que rivaliza con lanzamientos de 2025. La actualización ya está disponible para descargar gratuitamente a través de NVIDIA Omniverse para todos los poseedores de tarjetas RTX serie 4000 y 5000.
                                                                """);

                                crearBlog(blogPostRepository,
                                                "Jugadores temen que la próxima consola de Xbox sea demasiado cara",
                                                LocalDate.of(2025, 9, 20),
                                                "https://i0.wp.com/levelup.buscafs.com/2025/09/Precio-Xbox.jpg?fit=1225,610&quality=75&strip=all",
                                                "Rumores sugieren que la próxima Xbox podría romper la barrera de precio de los $600 dólares debido a los costes de componentes.",
                                                """
                                                                Los rumores sobre la próxima generación de consolas de Microsoft se intensifican, y no todas las noticias son alentadoras para el bolsillo de los jugadores. Analistas de la industria y filtraciones recientes de la cadena de suministro sugieren que la sucesora de la Xbox Series X podría lanzarse con un precio significativamente superior al estándar histórico de $499.

                                                                Factores Económicos y Técnicos
                                                                Varios factores contribuyen a este temor creciente en la industria:
                                                                1. Costo de componentes estancado: El precio de los chips de alto rendimiento (wafers de 3nm), la memoria GDDR7 y los SSDs ultrarrápidos no ha bajado al ritmo que solía hacerlo en décadas pasadas. La Ley de Moore se está ralentizando.
                                                                2. Inflación global y logística: Los costes de manufactura, energía y distribución global han aumentado considerablemente desde 2020 y no han regresado a niveles prepandemia.
                                                                3. Estrategia Premium: Se rumorea que Microsoft busca lanzar una máquina "sin compromisos", capaz de ejecutar trazado de rayos completo y 60 FPS estables nativos, lo que requiere un hardware que, en PC, costaría más de $1500.

                                                                Declaraciones Preocupantes
                                                                Phil Spencer, CEO de Microsoft Gaming, comentó recientemente en una entrevista con The Verge que "el modelo tradicional de subsidiar fuertemente el hardware para recuperar dinero en la venta de software se está volviendo insostenible en el clima económico actual". Esto ha encendido las alarmas sobre un posible precio de lanzamiento que podría rondar los $600 o incluso $700 dólares para el modelo de gama alta.

                                                                Reacción de la Comunidad
                                                                La comunidad ha reaccionado con preocupación en redes sociales y foros como Reddit, argumentando que un precio tan elevado podría alejar al público masivo y elitizar el gaming de consola, especialmente si no hay un salto gráfico tan evidente como en generaciones pasadas.

                                                                Sin embargo, algunos entusiastas defienden que si la consola ofrece un rendimiento equiparable a un PC de gama alta actual y mantiene la compatibilidad con todo el catálogo anterior, el precio estaría justificado. Se espera que Microsoft revele los primeros detalles oficiales del hardware a mediados de 2026, con un posible lanzamiento en 2027. Mientras tanto, la incertidumbre reina en el ecosistema Xbox.
                                                                """);

                                crearBlog(blogPostRepository,
                                                "Hay un nuevo shooter táctico gratis petándolo en Steam",
                                                LocalDate.of(2025, 9, 22),
                                                "https://i.blogs.es/0683a3/maxresdefault-1-_upscayl_2x_remacri-4x-1-/1200_800.jpeg",
                                                "Arena Breakout: Infinite se convierte en la nueva sensación de los shooters de extracción en PC.",
                                                """
                                                                Steam tiene un nuevo rey en el nicho hardcore de los shooters de extracción. 'Arena Breakout: Infinite', desarrollado por Morefun Studios, ha irrumpido en la plataforma de Valve con una fuerza arrolladora, acumulando miles de jugadores simultáneos y reseñas "Muy Positivas" en sus primeras semanas de acceso anticipado global.

                                                                ¿Qué hace especial a este juego?
                                                                A diferencia de los Battle Royale tradicionales como Warzone o Apex, aquí el objetivo no es ser el último en pie. Tu misión es entrar en la "Zona Oscura", saquear recursos valiosos, eliminar a otros operadores o IA, y escapar con vida a través de puntos de extracción. La clave es que si mueres, pierdes absolutamente todo el equipo que llevabas contigo. Esta premisa de "alto riesgo, alta recompensa" genera una tensión inigualable que mantiene a los jugadores al borde del asiento.

                                                                Puntos fuertes según la comunidad:
                                                                - Gráficos Fotorrealistas: Utilizando una versión modificada de Unreal Engine 5, el juego ofrece una fidelidad visual impresionante. La iluminación dinámica en interiores y las texturas de la vegetación rivalizan con títulos AAA de pago de 70 dólares.
                                                                - Personalización de Armas "Gunsmith": El sistema para modificar armas es obsesivamente detallado. Puedes cambiar cada pieza: cañón, culata, guardamanos, mira, cargador, tipo de munición, e incluso el gas block. Esto permite crear armas únicas adaptadas a tu estilo de juego exacto.
                                                                - Modelo Free-to-Play justo: Aunque existen microtransacciones para cosméticos y espacio de inventario, la mayoría de los jugadores coinciden en que se puede progresar y competir sin gastar dinero real, gracias a la economía del juego basada en el saqueo y el mercado entre jugadores.

                                                                El mapa principal, 'Kamona', ofrece una mezcla táctica de combates a larga distancia en bosques densos y tiroteos frenéticos en interiores de complejos militares. Con actualizaciones constantes prometidas y una hoja de ruta ambiciosa que incluye nuevos mapas y modos nocturnos, Arena Breakout: Infinite parece estar listo para desafiar a titanes establecidos del género como Escape from Tarkov y Hunt: Showdown.
                                                                """);

                                crearBlog(blogPostRepository, "PlayStation 5: Potencia de Nueva Generación",
                                                LocalDate.of(2025, 10, 12), "/img/Play 5.jpg",
                                                "Analizamos la PS5 años después de su lanzamiento: ¿sigue siendo la reina del salón?",
                                                """
                                                                La PlayStation 5 no es solo una consola, es un centro de entretenimiento inmersivo que ha redefinido lo que esperamos de los videojuegos domésticos. Años después de su lanzamiento, y con una biblioteca de juegos madura, sigue demostrando por qué es la líder del mercado actual.

                                                                Rendimiento que se Siente
                                                                Lo primero que cambió las reglas del juego fue su SSD personalizado de ultra alta velocidad. Los tiempos de carga, que antes eran momentos para mirar el móvil, ahora son prácticamente inexistentes en juegos optimizados como 'Spider-Man 2' o 'Ratchet & Clank: Rift Apart'. Viajar rápido en un mundo abierto es, por primera vez, verdaderamente rápido, cambiando el ritmo de juego por completo.

                                                                DualSense: La Verdadera Next-Gen
                                                                Más allá de los gráficos 4K, el mando DualSense sigue siendo la característica más innovadora. La retroalimentación háptica permite sentir texturas: la arena bajo los pies, el golpe del metal contra metal o la lluvia cayendo. Los gatillos adaptativos añaden resistencia física; sentir la tensión de la cuerda de un arco o el bloqueo de un arma encasquillada ofrece una inmersión táctil que ninguna otra plataforma tiene actualmente.

                                                                Audio 3D Tempest
                                                                Con auriculares compatibles, el motor de audio Tempest 3D es una revelación. El sonido posicional es increíblemente preciso, permitiéndote ubicar enemigos por sus pasos en shooters competitivos o sumergirte en la atmósfera opresiva de un survival horror como 'Resident Evil Village'.

                                                                Puntos a Considerar
                                                                No todo es perfecto. El tamaño de la consola sigue siendo considerable, lo que puede dificultar su colocación en algunos muebles de TV minimalistas. Además, el almacenamiento base de 825GB se llena rápido con el tamaño creciente de los juegos modernos (Call of Duty puede ocupar 200GB solo), aunque la expansión mediante SSD M.2 es sencilla y los precios de las memorias han bajado.

                                                                Veredicto
                                                                Si buscas la mejor experiencia en juegos exclusivos narrativos de alta calidad y tecnología de vanguardia en el salón de tu casa, la PS5 es la elección indiscutible. Su catálogo ha madurado y ofrece joyas imprescindibles para todo tipo de jugador.
                                                                """);

                                crearBlog(blogPostRepository, "Mando Inalámbrico Xbox: Precisión y Comodidad",
                                                LocalDate.of(2025, 10, 8), "/img/Mando.jpg",
                                                "Microsoft perfecciona lo que ya era excelente. Análisis del mando de Xbox Series X|S.",
                                                """
                                                                Microsoft ha optado por la filosofía de "si no está roto, no lo arregles" pero sí "perfecciónalo" con su nuevo Mando Inalámbrico Xbox para Series X|S. El resultado es el controlador más refinado, compatible y ergonómico que han fabricado hasta la fecha, ideal para largas sesiones de juego.

                                                                Diseño y Ergonomía Refinada
                                                                A simple vista parece igual al de Xbox One, pero en las manos la diferencia es notable. El chasis es ligeramente más pequeño y redondeado, adaptándose mejor a manos de todos los tamaños, incluidas las más pequeñas. Los agarres texturizados (grip) en los gatillos, botones superiores (bumpers) y la carcasa trasera proporcionan una sujeción firme incluso si te sudan las manos en momentos de tensión.

                                                                Novedades que Importan
                                                                - Botón Compartir: Finalmente, Xbox se pone al día. Capturar clips de video y capturas de pantalla es ahora instantáneo y sencillo con un botón dedicado en el centro, eliminando la necesidad de menús engorrosos en medio de la acción.
                                                                - D-Pad Híbrido: Inspirado en el mando Elite, el nuevo pad direccional cóncavo es una maravilla. Es perfecto tanto para movimientos cardinales precisos (en juegos de plataformas) como para diagonales rápidas en juegos de lucha, ofreciendo un "clic" satisfactorio y táctil.
                                                                - Latencia Reducida: La tecnología 'Dynamic Latency Input' (DLI) sincroniza los inputs del mando con los frames de la consola, reduciendo imperceptiblemente el retardo para una respuesta más ágil.

                                                                Conectividad Universal
                                                                La compatibilidad nativa mediante Bluetooth con Windows 10/11, Android e iOS lo convierte en el mando más versátil del mercado. Puedes pasar de jugar en tu consola a tu PC o tablet con solo pulsar un botón de sincronización. Además, sigue funcionando con pilas AA, lo que algunos odian pero otros aman por la posibilidad de nunca tener que enchufarlo si usas recargables. Es el estándar de oro por su fiabilidad.
                                                                """);

                                crearBlog(blogPostRepository, "HyperX Cloud II: Escucha Cada Detalle",
                                                LocalDate.of(2025, 10, 3), "/img/Audifonos.jpg",
                                                "¿Por qué los HyperX Cloud II siguen siendo los reyes de la calidad-precio años después?",
                                                """
                                                                En el saturado y a veces confuso mercado de auriculares gaming, los HyperX Cloud II mantienen su estatus de leyenda viviente por una razón muy simple: ofrecen exactamente lo que los gamers necesitan sin luces RGB innecesarias ni software complicado.

                                                                Comodidad Suprema
                                                                Lo primero que notas al sacarlos de la caja es la calidad de construcción. Pero al ponerlos, notas la comodidad. Las almohadillas de espuma viscoelástica (memory foam) recubiertas de piel sintética premium permiten jugar durante horas sin sentir presión en las orejas o la cabeza, incluso si usas gafas. La estructura de aluminio es duradera pero sorprendentemente ligera.

                                                                Calidad de Sonido Competitiva
                                                                Sus transductores de 53mm entregan un sonido claro y contundente. El perfil de audio está afinado para el gaming: los bajos son potentes para dar impacto a las explosiones sin distorsionar, mientras que los medios y agudos son nítidos, permitiendo escuchar pasos enemigos y recargas con precisión quirúrgica. La tarjeta de sonido USB incluida permite activar el sonido envolvente 7.1 virtual con un botón, que, aunque no es nivel estudio, amplia la escena sonora en juegos de mundo abierto inmersivos.

                                                                Micrófono y Versatilidad
                                                                El micrófono desmontable con cancelación de ruido es claro y hace un buen trabajo aislando tu voz del tecleo mecánico o el ruido ambiente del hogar. Está certificado por TeamSpeak y Discord, asegurando que tus compañeros te entiendan siempre. Además, al ser jack 3.5mm (sin la tarjeta USB), funcionan con cualquier consola o móvil.

                                                                Conclusión
                                                                Por su precio actual, es difícil, si no imposible, encontrar unos auriculares que ofrezcan un mejor equilibrio entre construcción robusta, confort de primera clase y audio fiable. Un clásico que sigue vigente y que recomendamos a ojos cerrados.
                                                                """);

                                crearBlog(blogPostRepository, "Logitech G502 HERO: El Rey de la Precisión",
                                                LocalDate.of(2025, 9, 25), "/img/Mause.jpg",
                                                "El ratón más vendido del mundo sigue siendo una bestia para el gaming competitivo y la productividad.",
                                                """
                                                                El Logitech G502 HERO no es solo un ratón, es una institución en el PC Gaming. Venerado por la comunidad y odiado por sus competidores, este periférico combina un diseño icónico y angular con tecnología de punta que ha resistido la prueba del tiempo.

                                                                El Corazón de la Bestia: Sensor HERO 25K
                                                                Equipado con el sensor HERO 25K propietario de Logitech, ofrece un seguimiento 1:1 sin suavizado, aceleración ni filtros en todo el rango de DPI (desde 100 hasta 25.600). En la práctica, esto significa que el cursor va exactamente donde tu mano lo mueve, píxel a píxel. Es una herramienta fundamental para shooters competitivos como Counter-Strike 2 o Valorant donde la precisión lo es todo.

                                                                Personalización Total
                                                                Cuenta con 11 botones programables. Desde macros complejas para MMOs hasta comandos simples como granadas o curaciones, puedes adaptarlo a cualquier género. Además, incluye un sistema de pesas ajustables (cinco pesas de 3.6g) que se colocan en la base, permitiéndote modificar el equilibrio y el peso total del ratón a tu gusto exacto. ¿Lo quieres ligero para movimientos rápidos o pesado para mayor control? Tú decides.

                                                                La Rueda Infinita
                                                                Una característica amada por muchos usuarios es su rueda de desplazamiento metálica con dos modos: el modo paso a paso para cambios de arma precisos, y el modo de desplazamiento libre (desbloqueado) para navegar por documentos largos, inventarios infinitos o webs a toda velocidad.

                                                                Diseño
                                                                Es un ratón grande y ergonómico diseñado para diestros, con un apoyo para el pulgar que evita el arrastre. Ideal para agarres de palma o garra. Si buscas rendimiento puro, durabilidad y personalización extrema, el G502 HERO sigue siendo el rey indiscutible de los ratones con cable.
                                                                """);

                                crearBlog(blogPostRepository, "Secretlab TITAN: El Trono Definitivo del Gamer",
                                                LocalDate.of(2025, 9, 18), "/img/Silla.jpg",
                                                "Analizamos la Secretlab TITAN Evo: cuando una silla gaming se convierte en una inversión de salud.",
                                                """
                                                                Olvídate de las sillas de oficina genéricas que te destrozan la espalda o las sillas 'racing' baratas que se pelan a los seis meses. La Secretlab TITAN Evo representa el pináculo del mobiliario gamer, combinando una estética elegante con una ergonomía de nivel científico.

                                                                Soporte Lumbar Revolucionario
                                                                A diferencia de los cojines sueltos tradicionales que se caen o molestan, la TITAN Evo integra un sistema de soporte lumbar L-ADAPT de 4 vías dentro del respaldo. Puedes ajustar tanto la altura como la curvatura del soporte mediante perillas laterales, asegurando que se adapte perfectamente a la forma única de tu columna vertebral, previniendo dolores tras largas jornadas.

                                                                Materiales de Primera Calidad
                                                                Disponible en piel sintética híbrida NEO (que promete ser 12 veces más duradera que la piel normal) o tela SoftWeave Plus (más suave y transpirable, ideal para climas calurosos). Ambos materiales se sienten premium al tacto. El asiento de espuma curada en frío ofrece el equilibrio justo entre firmeza para mantener la postura y suavidad para el confort, sin hundirse con el tiempo.

                                                                Innovación Magnética
                                                                El cojín para la cabeza es magnético, eliminando las antiestéticas correas elásticas. Simplemente colócalo donde quieras y se quedará ahí mágicamente. Los reposabrazos 4D también tienen tapas magnéticas intercambiables (sistema CloudSwap), permitiéndote cambiar la superficie de apoyo si se desgasta o si prefieres otro material como gel refrigerante.

                                                                Veredicto
                                                                Es una inversión considerable, sí (ronda los $500-$600), pero si pasas más de 6 horas al día sentado frente al PC, ya sea trabajando o jugando, tu espalda te lo agradecerá a largo plazo. Es, sin duda, el trono definitivo para cualquier setup.
                                                                """);
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
        private void crearBlog(BlogPostRepository repo, String title, LocalDate date, String img, String summary,
                        String content) {
                repo.save(BlogPost.builder()
                                .title(title)
                                .date(date)
                                .image(img)
                                .summary(summary)
                                .content(content)
                                .build());
        }
}