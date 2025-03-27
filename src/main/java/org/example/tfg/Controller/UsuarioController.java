package org.example.tfg.Controller;

import jakarta.validation.Valid;
import org.example.tfg.Dto.LoginRequest;
import org.example.tfg.Dto.Usuario;
import org.example.tfg.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/usuarios")
@CacheConfig(cacheNames = {"usuarios"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    //Obtener usuario por ID
    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Optional<Usuario>> obtenerUsuarioPorId(@PathVariable Integer id) {
        try{
            Thread.sleep(3000);
            Optional<Usuario> usuario = usuarioService.obtenerUsuarioByID(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    // Crear usuario
    @PostMapping
    public ResponseEntity<String> guardarUsuario(@RequestBody @Valid Usuario usuario) {
        Usuario clienteGuardar = usuarioService.guardarUsuario(usuario);
        if (clienteGuardar != null) {
            return ResponseEntity.ok("Usuario guardado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no guardado");
        }
    }

    //Actualizar usuario
    @PutMapping
    public ResponseEntity<String> actualizarCliente(@RequestBody @Valid Usuario nuevoUsuario) {
        boolean actualizado = usuarioService.actualizarUsuario(nuevoUsuario);
        if (actualizado) {
            return ResponseEntity.ok("Usuario actualizado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    //Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int id) {
        boolean eliminado = usuarioService.eliminarUsuario(id);

        if (eliminado) {
            return ResponseEntity.ok("Usuario eliminado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> validarLogin(@RequestBody @Valid LoginRequest loginRequest) {
        // Crear usuario con la información recibida
        Usuario usuario = new Usuario();
        usuario.setNickname(loginRequest.getNickname());
        usuario.setPassword(loginRequest.getPassword());

        // Validar las credenciales
        if (usuarioService.validarLogin(usuario)) {
            // Crear respuesta en formato JSON
            ResponseMessage responseMessage = new ResponseMessage("Usuario logueado");
            return ResponseEntity.ok(responseMessage);
        } else {
            // En caso de fallo, respuesta con error en formato JSON
            ResponseMessage responseMessage = new ResponseMessage("Usuario no logueado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }

    // Clase interna para el mensaje de respuesta
    public static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}

