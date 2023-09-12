package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;
@Table(name = "pacientes")
@Entity(name = "paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String documento;
    private boolean activo;
    @Embedded
    private Direccion direccion;
    private String telefono;
    private String email;

    public Paciente(DatosRegistroPaciente datosRegistroPaciente){
        this.activo = true;
        this.nombre = datosRegistroPaciente.nombre();
        this.documento = datosRegistroPaciente.documento();
        this.direccion = datosRegistroPaciente.direccion();
        this.telefono = datosRegistroPaciente.telefono();
        this.email = datosRegistroPaciente.email();
    }

}
