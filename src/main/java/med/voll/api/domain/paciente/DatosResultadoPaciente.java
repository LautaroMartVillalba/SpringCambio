package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.DatosDireccion;

public record DatosResultadoPaciente (Long id,
                                      String nombre,
                                      String email,
                                      String documento,
                                      String telefono,
                                      DatosDireccion datosDireccion){
}
