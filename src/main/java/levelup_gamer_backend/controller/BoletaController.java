package levelup_gamer_backend.controller;

import levelup_gamer_backend.dto.BoletaRequest;
import levelup_gamer_backend.entity.Boleta;
import levelup_gamer_backend.service.BoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @PostMapping("/checkout/finalizar")
    public ResponseEntity<?> realizarCompra(@RequestBody BoletaRequest request) {
        try {
            Boleta nuevaBoleta = boletaService.crearBoleta(request);
            String response = String.format("{\"id\": %d, \"numeroOrden\": %d}",
                    nuevaBoleta.getId(), nuevaBoleta.getNumeroOrden());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar la compra: " + e.getMessage());
        }
    }

    @GetMapping("/ordenes")
    public ResponseEntity<List<Boleta>> obtenerTodas() {
        return ResponseEntity.ok(boletaService.obtenerTodas());
    }

    @GetMapping("/ordenes/{id}")
    public ResponseEntity<Boleta> obtenerPorId(@PathVariable Long id) {
        return boletaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/ordenes/{id}/estado")
    public ResponseEntity<Boleta> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("estado");
        if (nuevoEstado == null || nuevoEstado.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Boleta boletaActualizada = boletaService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(boletaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/admin/reportes/total-ventas")
    public ResponseEntity<Long> obtenerTotalVentas() {
        return ResponseEntity.ok(boletaService.obtenerTotalVentas());
    }

    @GetMapping("/ordenes/mis-pedidos")
    public ResponseEntity<List<Boleta>> obtenerMisPedidos(
            org.springframework.security.core.Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String email = authentication.getName();
        return ResponseEntity.ok(boletaService.obtenerPorUsuario(email));
    }
}