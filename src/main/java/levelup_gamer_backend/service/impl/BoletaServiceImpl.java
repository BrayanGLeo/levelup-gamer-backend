package levelup_gamer_backend.service.impl;

import jakarta.annotation.PostConstruct;
import levelup_gamer_backend.dto.BoletaRequest;
import levelup_gamer_backend.dto.DetalleBoletaDto;
import levelup_gamer_backend.entity.Boleta;
import levelup_gamer_backend.entity.DetalleBoleta;
import levelup_gamer_backend.entity.Producto;
import levelup_gamer_backend.entity.Usuario;
import levelup_gamer_backend.repository.BoletaRepository;
import levelup_gamer_backend.service.BoletaService;
import levelup_gamer_backend.service.ProductoService;
import levelup_gamer_backend.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@SuppressWarnings("null")
public class BoletaServiceImpl implements BoletaService {

    private final BoletaRepository boletaRepository;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    private AtomicLong orderCounter = new AtomicLong(0);

    public BoletaServiceImpl(BoletaRepository repo, ProductoService prodService, UsuarioService userService) {
        this.boletaRepository = repo;
        this.productoService = prodService;
        this.usuarioService = userService;
    }

    @PostConstruct
    public void initOrderCounter() {
        Long maxOrder = boletaRepository.findMaxNumeroOrden();
        orderCounter.set(maxOrder != null ? maxOrder + 1 : 1000L);
    }

    @Override
    @Transactional
    public Boleta crearBoleta(BoletaRequest request) {
        System.out.println(">>> Iniciando creación de boleta para: " + request.getUsuarioEmail());

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío. No se puede procesar.");
        }

        Usuario usuario = null;
        if (request.getUsuarioEmail() != null && !request.getUsuarioEmail().isEmpty()) {
            usuario = usuarioService.obtenerPorEmail(request.getUsuarioEmail().trim().toLowerCase())
                    .orElse(null);
        }

        Boleta boleta = Boleta.builder()
                .numeroOrden(orderCounter.getAndIncrement())
                .fechaCompra(LocalDateTime.now())
                .total(request.getTotal())
                .estado("Pagado")
                .tipoEntrega(request.getTipoEntrega())
                .metodoPago(request.getMetodoPago())
                .nombreCliente(request.getNombreCliente())
                .apellidoCliente(request.getApellidoCliente())
                .telefonoCliente(request.getTelefonoCliente())
                .direccionEnvio(request.getDireccionEnvio())
                .usuario(usuario)
                .detalles(new ArrayList<>())
                .build();

        int totalCalculado = 0;

        for (DetalleBoletaDto itemDto : request.getItems()) {
            Producto producto = productoService.actualizarStock(
                    itemDto.getCodigoProducto(),
                    itemDto.getCantidad());

            DetalleBoleta detalle = DetalleBoleta.builder()
                    .boleta(boleta)
                    .producto(producto)
                    .cantidad(itemDto.getCantidad())
                    .precioUnitario(producto.getPrecio())
                    .build();

            boleta.getDetalles().add(detalle);
            totalCalculado += itemDto.getCantidad() * producto.getPrecio();
        }
        if (totalCalculado != request.getTotal()) {
            boleta.setTotal(totalCalculado);
        }

        System.out.println(">>> Guardando boleta #" + boleta.getNumeroOrden());
        return boletaRepository.save(boleta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Boleta> obtenerTodas() {
        return boletaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Boleta> obtenerPorUsuario(String email) {
        return boletaRepository.findByUsuarioEmailOrderByFechaCompraDesc(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Boleta> obtenerPorId(Long id) {
        return boletaRepository.findById(id);
    }

    @Override
    @Transactional
    public Boleta actualizarEstado(Long id, String nuevoEstado) {
        Boleta boleta = boletaRepository.findById(id).orElseThrow(() -> new RuntimeException("No encontrada"));
        boleta.setEstado(nuevoEstado);
        return boletaRepository.save(boleta);
    }

    @Override
    @Transactional(readOnly = true)
    public Long obtenerTotalVentas() {
        return boletaRepository.findAll().stream().mapToLong(b -> b.getTotal() != null ? b.getTotal() : 0).sum();
    }
}