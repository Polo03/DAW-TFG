package org.example.tfg.Controller;

import jakarta.validation.Valid;
import org.example.tfg.Dto.Alimento;
import org.example.tfg.Dto.Dieta;
import org.example.tfg.Dto.Foro;
import org.example.tfg.Service.AlimentoService;
import org.example.tfg.Service.DietaService;
import org.example.tfg.Service.ForoService;
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
@RequestMapping("/api/foro")
public class ForoController {
    @Autowired
    private ForoService foroService;

    @GetMapping()
    public List<Foro> getAllForo() throws ExecutionException, InterruptedException {
        return foroService.getAllForo();
    }

    @GetMapping("/{id}")
    public Foro getForoById(@PathVariable String id) throws ExecutionException, InterruptedException {
        return foroService.getForoById(id);
    }

    @PostMapping
    public ResponseEntity<?> addForo(@Valid @RequestBody Foro foro, BindingResult result) throws ExecutionException, InterruptedException {
        Map<String, String> errores = new HashMap<>();
        if (result.hasErrors() || !errores.isEmpty()) {
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        foro.setRespuesta("");
        Foro creado = foroService.addForo(foro);
        return ResponseEntity.ok(creado);
    }

    @PutMapping
    public ResponseEntity<?> updateForo(@Valid @RequestBody Foro foro, BindingResult result) throws ExecutionException, InterruptedException {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        Foro actualizado = foroService.updateForo(foro);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarForo(@PathVariable String id) {
        boolean eliminado = foroService.deleteForo(id);

        if (eliminado) {
            return ResponseEntity.ok("Pregunta eliminada con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pregunta no encontrada");
        }
    }
}
