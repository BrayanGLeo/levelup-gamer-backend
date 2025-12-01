package levelup_gamer_backend.service;

import levelup_gamer_backend.dto.BoletaRequest;
import levelup_gamer_backend.entity.Boleta;
import java.util.List;
import java.util.Optional;

public interface BoletaService {
    Boleta crearBoleta(BoletaRequest request);

    List<Boleta> obtenerTodas();

    List<Boleta> obtenerPorUsuario(String email);

    Optional<Boleta> obtenerPorId(Long id);

    Boleta actualizarEstado(Long id, String nuevoEstado);

    Long obtenerTotalVentas();
}