package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DatosAgendarConsulta(Long id, Long idMedico, @NotNull Long idPaciente, @NotNull @Future LocalDate data) {
}
