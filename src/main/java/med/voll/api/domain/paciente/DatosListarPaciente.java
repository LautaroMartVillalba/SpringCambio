package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;

public record DatosListarPaciente(@NotNull Long id, String nombre, String documento, String telefono) {

    public DatosListarPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNombre(), paciente.getDocumento(), paciente.getTelefono());
    }

}
