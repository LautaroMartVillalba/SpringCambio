package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAutenticarUsuario;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DatosJWToken;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
        @Autowired
        TokenService tokenService;

        @Autowired
        AuthenticationManager authenticationManager;
        @PostMapping
        public ResponseEntity autenticarUsuario (@RequestBody @Valid DatosAutenticarUsuario datosAutenticarUsuario){
                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(datosAutenticarUsuario.usuario(),
                datosAutenticarUsuario.clave());

                var usuarioAutenticado = authenticationManager.authenticate(authenticationToken);
                var JWToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
                        return ResponseEntity.ok(new DatosJWToken(JWToken));
        }

}
