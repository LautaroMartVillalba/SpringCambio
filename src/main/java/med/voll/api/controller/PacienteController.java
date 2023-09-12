package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity<DatosResultadoPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente, UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
        DatosResultadoPaciente datosResultadoPaciente = new DatosResultadoPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(),paciente.getDocumento(),paciente.getTelefono(),
                new DatosDireccion(paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(), paciente.getDireccion().getNumero(),
                        paciente.getDireccion().getComplemento(), paciente.getDireccion().getCiudad()));
        URI url = uriComponentsBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosResultadoPaciente);
    }

    @GetMapping
    public Page<DatosListarPaciente> listarPaciente(@PageableDefault(size = 2) Pageable pageable){
        return pacienteRepository.findByActivoTrue(pageable).map(DatosListarPaciente::new);
    }

}