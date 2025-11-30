package levelup_gamer_backend.dto;

import lombok.Data;

@Data
public class DetalleBoletaDto {
    private String codigoProducto;
    private Integer cantidad;
    private Integer precioUnitario; 
}