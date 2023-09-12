package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.direccion.Direccion;

public record DatosRegistroPaciente(@NotBlank String nombre,
                                    @NotBlank String telefono,
                                    @NotBlank @Pattern(regexp = "\\d{4,6}") String documento,
                                    @NotNull @Valid Direccion direccion,
                                    @NotBlank @Email String email) {



}
