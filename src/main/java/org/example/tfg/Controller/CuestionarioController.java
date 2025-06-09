package org.example.tfg.Controller;

import jakarta.validation.Valid;
import org.example.tfg.Dto.Alimento;
import org.example.tfg.Dto.Cuestionario;
import org.example.tfg.Dto.Usuario;
import org.example.tfg.Service.AlimentoService;
import org.example.tfg.Service.CuestionarioService;
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
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/cuestionario")
public class CuestionarioController {

    @Autowired
    private CuestionarioService cuestionarioService;

    @GetMapping()
    public List<Cuestionario> getAllCuestionarios() throws ExecutionException, InterruptedException {
        return cuestionarioService.getAllCuestionarios();
    }

    @GetMapping("/{id}")
    public Cuestionario getCuestionarioById(@PathVariable String id) throws ExecutionException, InterruptedException {
        return cuestionarioService.getCuestionarioById(id);
    }

    @PostMapping
    public ResponseEntity<?> addCuestionario(@Valid @RequestBody Cuestionario cuestionario, BindingResult result) throws ExecutionException, InterruptedException {
        Map<String, String> errores = new HashMap<>();
        if (result.hasErrors() || !errores.isEmpty()) {
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        Cuestionario creado = cuestionarioService.addCuestionario(cuestionario);
        return ResponseEntity.ok(creado);
    }

    @PutMapping
    public ResponseEntity<?> updateCuestionario(@Valid @RequestBody Cuestionario cuestionario, BindingResult result) throws ExecutionException, InterruptedException {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        Cuestionario actualizado = cuestionarioService.updateCuestionario(cuestionario);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCuestionario(@PathVariable String id) {
        boolean eliminado = cuestionarioService.deleteCuestionario(id);

        if (eliminado) {
            return ResponseEntity.ok("Cuestionario eliminado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuestionario no encontrado");
        }
    }

}
