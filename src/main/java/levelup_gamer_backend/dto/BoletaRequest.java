package levelup_gamer_backend.dto;

import java.util.List;
import lombok.Data;

@Data
public class BoletaRequest {
    private String usuarioEmail;
    private Integer total;
    private String tipoEntrega;
    private String estado;

    private String nombreCliente;
    private String apellidoCliente;
    private String telefonoCliente;

    private List<DetalleBoletaDto> items;
}