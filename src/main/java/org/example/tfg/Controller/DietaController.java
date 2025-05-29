package org.example.tfg.Controller;

import jakarta.validation.Valid;
import org.example.tfg.Dto.Alimento;
import org.example.tfg.Dto.Dieta;
import org.example.tfg.Service.AlimentoService;
import org.example.tfg.Service.DietaService;
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
@RequestMapping("/api/dietas")
public class DietaController {
    @Autowired
    private DietaService dietaService;

    @GetMapping()
    public List<Dieta> getAllDietas() throws ExecutionException, InterruptedException {
        return dietaService.getAllDietas();
    }

    @GetMapping("/{id}")
    public Dieta getDietaById(@PathVariable String id) throws ExecutionException, InterruptedException {
        return dietaService.getDietaById(id);
    }

    @PostMapping
    public ResponseEntity<?> addDieta(@Valid @RequestBody Dieta dieta, BindingResult result) throws ExecutionException, InterruptedException {
        Map<String, String> errores = new HashMap<>();
        if (result.hasErrors() || !errores.isEmpty()) {
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        Dieta creado = dietaService.addDieta(dieta);
        return ResponseEntity.ok(creado);
    }

    @PutMapping
    public ResponseEntity<?> updateDieta(@Valid @RequestBody Dieta dieta, BindingResult result) throws ExecutionException, InterruptedException {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        Dieta actualizado = dietaService.updateDieta(dieta);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDieta(@PathVariable String id) {
        boolean eliminado = dietaService.deleteDieta(id);

        if (eliminado) {
            return ResponseEntity.ok("Dieta eliminada con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dieta no encontrada");
        }
    }
}
