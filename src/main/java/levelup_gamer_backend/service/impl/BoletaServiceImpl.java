package levelup_gamer_backend.service.impl;

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
    
    private static final AtomicLong ORDER_COUNTER = new AtomicLong(1000L);

    public BoletaServiceImpl(
            BoletaRepository boletaRepository,
            ProductoService productoService,
            UsuarioService usuarioService) {
        this.boletaRepository = boletaRepository;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    // 🚨 CRÍTICO: La transacción asegura la integridad entre stock y boleta
    @Override
    @Transactional 
    public Boleta crearBoleta(BoletaRequest request) {
        Usuario usuario = null;
        if (request.getUsuarioEmail() != null && !request.getUsuarioEmail().isEmpty()) {
            usuario = usuarioService.obtenerPorEmail(request.getUsuarioEmail()).orElse(null); 
        }
        
        Boleta boleta = Boleta.builder()
                .numeroOrden(ORDER_COUNTER.getAndIncrement())
                .fechaCompra(LocalDateTime.now())
                .total(request.getTotal()) 
                .estado(request.getEstado() != null ? request.getEstado() : "Pendiente") 
                .usuario(usuario)
                .detalles(new ArrayList<>())
                .build();

        int totalCalculado = 0;
        
        // 1. Procesar Detalles y Descontar Stock
        for (DetalleBoletaDto itemDto : request.getItems()) {
            // Llama a ProductoService.actualizarStock, que lanza RuntimeException si el stock falla.
            Producto productoActualizado = productoService.actualizarStock(
                itemDto.getCodigoProducto(), 
                itemDto.getCantidad()
            );

            DetalleBoleta detalle = DetalleBoleta.builder()
                .boleta(boleta)
                .producto(productoActualizado)
                .cantidad(itemDto.getCantidad())
                .precioUnitario(itemDto.getPrecioUnitario())
                .build();
            
            boleta.getDetalles().add(detalle);
            totalCalculado += itemDto.getCantidad() * itemDto.getPrecioUnitario();
        }
        
        // 2. Lógica de Integridad: Verificar el total
        if (totalCalculado != request.getTotal()) {
            throw new RuntimeException("Error de seguridad: El total calculado no coincide con el total enviado. Transacción revertida.");
        }

        // 3. Guardar Boleta
        return boletaRepository.save(boleta);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Boleta> obtenerTodas() {
        return boletaRepository.findAll();
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
        
        List<String> estadosValidos = List.of("Pendiente", "Procesando", "En preparación", "En tránsito", "Completado", "Cancelado");
        if (!estadosValidos.contains(nuevoEstado)) {
            throw new IllegalArgumentException("Estado de orden no válido.");
        }
        boleta.setEstado(nuevoEstado);
        return boletaRepository.save(boleta);
    }

    // Reporte de Ventas
    @Override
    @Transactional(readOnly = true)
    public Long obtenerTotalVentas() {
        return boletaRepository.findAll().stream()
                .mapToLong(Boleta::getTotal)
                .sum();
    }
}