package expediente.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Expediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tipo_presentacion", precision = 1, scale = 0)
    private Integer tipoPresentacion;
    private String notas;
    @Column(name = "ciudadano_id")
    private Integer ciudadanoId;
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @PrePersist
    protected void onCreate() {
        createAt = new Date();
    }

}
