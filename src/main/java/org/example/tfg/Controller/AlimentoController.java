package org.example.tfg.Controller;

import jakarta.validation.Valid;
import org.example.tfg.Dto.Alimento;
import org.example.tfg.Dto.Usuario;
import org.example.tfg.Service.AlimentoService;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/alimentos")
@CacheConfig(cacheNames = {"alimentos"})
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @GetMapping()
    public List<Alimento> getAllAlimentos() throws ExecutionException, InterruptedException {
        return alimentoService.getAllAlimentos();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "alimentos", key = "#id")
    public Alimento getAlimentoById(@PathVariable String id) throws ExecutionException, InterruptedException {
        return alimentoService.getAlimentoById(id);
    }

    @PostMapping
    public ResponseEntity<?> addAlimento(@Valid @RequestBody Alimento alimento, BindingResult result) throws ExecutionException, InterruptedException {
        Map<String, String> errores = new HashMap<>();
        if (result.hasErrors() || !errores.isEmpty()) {
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        Alimento creado = alimentoService.addAlimento(alimento);
        return ResponseEntity.ok(creado);
    }

    @PutMapping
    public ResponseEntity<?> updateAlimento(@Valid @RequestBody Alimento alimento, BindingResult result) throws ExecutionException, InterruptedException {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        Alimento actualizado = alimentoService.updateAlimento(alimento);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAlimento(@PathVariable String id) {
        boolean eliminado = alimentoService.deleteAlimento(id);

        if (eliminado) {
            return ResponseEntity.ok("Alimento eliminado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alimento no encontrado");
        }
    }
}
