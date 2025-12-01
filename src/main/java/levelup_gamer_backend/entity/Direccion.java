package levelup_gamer_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DIRECCION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String alias;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String comuna;

    @Column(nullable = false)
    private String calle;

    @Column(nullable = false)
    private String numero;

    private String depto;

    @Column(name = "recibe_nombre", nullable = false)
    private String recibeNombre;

    @Column(name = "recibe_apellido", nullable = false)
    private String recibeApellido;

    @Column(name = "recibe_telefono", nullable = false)
    private String recibeTelefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;
}