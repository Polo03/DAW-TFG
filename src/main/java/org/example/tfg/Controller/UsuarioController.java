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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/usuarios")
@CacheConfig(cacheNames = {"usuarios"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping()
    public List<Usuario> getAllUsers() throws ExecutionException, InterruptedException {
        return usuarioService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "usuarios", key = "#id")
    public Usuario getUserById(@PathVariable String id) throws ExecutionException, InterruptedException {
        return usuarioService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<?> addUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) throws ExecutionException, InterruptedException {
        Map<String, String> errores = new HashMap<>();
        if(usuarioService.existeNickname(usuario.getNickname()))
            errores.put("nickname","El nickname ya existe, prueba con otro.");
        if(usuarioService.existeDNI(usuario.getDni()))
            errores.put("dni","El dni ya existe, prueba con otro.");
        if(usuarioService.existeTelefono(usuario.getTlf()))
            errores.put("tlf","El teléfono ya existe, prueba con otro.");
        if(usuarioService.existeEmail(usuario.getEmail()))
            errores.put("email","El email ya existe, prueba con otro.");
        if (result.hasErrors() || !errores.isEmpty()) {
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        Usuario creado = usuarioService.addUser(usuario);
        return ResponseEntity.ok(creado);
    }

    @PutMapping
    public ResponseEntity<?> updateUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) throws ExecutionException, InterruptedException {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        Usuario actualizado = usuarioService.updateUser(usuario);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable String id) {
        boolean eliminado = usuarioService.deleteUser(id);

        if (eliminado) {
            return ResponseEntity.ok("Usuario eliminado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> validarLogin(@RequestBody LoginRequest loginRequest) {
        try {
            if (usuarioService.validarLogin(loginRequest)) {
                return ResponseEntity.ok("Usuario logueado");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no logueado");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Para ver el error en consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al intentar loguear");
        }
    }

}
