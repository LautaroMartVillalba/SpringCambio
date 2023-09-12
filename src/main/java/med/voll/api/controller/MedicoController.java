package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosResultadoMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosResultadoMedico datosResultadoMedico = new DatosResultadoMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(),medico.getDocumento(),medico.getTelefono(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),
                        medico.getDireccion().getComplemento(), medico.getDireccion().getNumero()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosResultadoMedico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListarMedico>> listarMedico(@PageableDefault(size = 2) Pageable paginacion){
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListarMedico::new));
    }
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosResultadoMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(),medico.getDocumento(),medico.getTelefono(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),
                        medico.getDireccion().getComplemento(), medico.getDireccion().getNumero())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable @Valid Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity <DatosResultadoMedico>listarDatosMedico(@PathVariable @Valid Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosResultadoMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(),medico.getDocumento(),medico.getTelefono(),
                new DatosDireccion(medico.getDireccion().getCalle(),
                        medico.getDireccion().getDistrito(), medico.getDireccion().getCiudad(),
                        medico.getDireccion().getComplemento(), medico.getDireccion().getNumero()));
        return ResponseEntity.ok(datosMedico);
    }
}
