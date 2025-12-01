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
public class BoletaServiceImpl implements BoletaService {

    private final BoletaRepository boletaRepository;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    private AtomicLong orderCounter = new AtomicLong(0);

    public BoletaServiceImpl(
            BoletaRepository boletaRepository,
            ProductoService productoService,
            UsuarioService usuarioService) {
        this.boletaRepository = boletaRepository;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    @PostConstruct
    public void initOrderCounter() {
        Long maxOrder = boletaRepository.findMaxNumeroOrden();
        orderCounter.set(maxOrder != null ? maxOrder + 1 : 1000L);
    }

    @Override
    @Transactional
    public Boleta crearBoleta(BoletaRequest request) {
        Usuario usuario = null;
        if (request.getUsuarioEmail() != null && !request.getUsuarioEmail().isEmpty()) {
            usuario = usuarioService.obtenerPorEmail(request.getUsuarioEmail()).orElse(null);
        }

        Boleta boleta = Boleta.builder()
                .numeroOrden(orderCounter.getAndIncrement())
                .fechaCompra(LocalDateTime.now())
                .total(request.getTotal())
                .estado(request.getEstado() != null ? request.getEstado() : "Pendiente")
                .tipoEntrega(request.getTipoEntrega())
                .nombreCliente(request.getNombreCliente())
                .apellidoCliente(request.getApellidoCliente())
                .telefonoCliente(request.getTelefonoCliente())
                .usuario(usuario)
                .direccionEnvio(request.getDireccionEnvio())
                .detalles(new ArrayList<>())
                .build();

        int totalCalculado = 0;

        for (DetalleBoletaDto itemDto : request.getItems()) {
            Producto producto = productoService.actualizarStock(
                    itemDto.getCodigoProducto(),
                    itemDto.getCantidad());

            int precioReal = producto.getPrecio();

            DetalleBoleta detalle = DetalleBoleta.builder()
                    .boleta(boleta)
                    .producto(producto)
                    .cantidad(itemDto.getCantidad())
                    .precioUnitario(precioReal)
                    .build();

            boleta.getDetalles().add(detalle);
            totalCalculado += itemDto.getCantidad() * precioReal;
        }

        if (totalCalculado != request.getTotal()) {
            System.out.println("ADVERTENCIA: Total frontend (" + request.getTotal() +
                    ") difiere de backend (" + totalCalculado + "). Se guardará el calculado.");
            boleta.setTotal(totalCalculado);
        }

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
        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada."));
        boleta.setEstado(nuevoEstado);
        return boletaRepository.save(boleta);
    }

    @Override
    @Transactional(readOnly = true)
    public Long obtenerTotalVentas() {
        return boletaRepository.findAll().stream()
                .mapToLong(b -> b.getTotal() != null ? b.getTotal() : 0)
                .sum();
    }
}