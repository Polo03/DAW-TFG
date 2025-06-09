package org.example.tfg.Controller;

import jakarta.validation.Valid;
import org.example.tfg.Dto.Alimento;
import org.example.tfg.Dto.Rutina;
import org.example.tfg.Service.AlimentoService;
import org.example.tfg.Service.RutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/rutinas")
public class RutinaController {

    @Autowired
    private RutinaService rutinaService;

    @GetMapping()
    public List<Rutina> getAllRutinas() throws ExecutionException, InterruptedException {
        return rutinaService.getAllRutinas();
    }

    @GetMapping("/{nombre}")
    public Rutina getRutinaById(@PathVariable String nombre) throws ExecutionException, InterruptedException {
        return rutinaService.getRutinaById(nombre);
    }

    @PostMapping
    public ResponseEntity<?> addRutina(@Valid @RequestBody Rutina rutina, BindingResult result) throws ExecutionException, InterruptedException {
        Map<String, String> errores = new HashMap<>();
        if (result.hasErrors() || !errores.isEmpty()) {
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        Rutina creado = rutinaService.addRutina(rutina);
        return ResponseEntity.ok(creado);
    }

    @PutMapping
    public ResponseEntity<?> updateRutina(@Valid @RequestBody Rutina rutina, BindingResult result) throws ExecutionException, InterruptedException {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            // No seguimos: devolvemos directamente los errores
            return ResponseEntity.badRequest().body(errores);
        }

        // Solo si NO hay errores, se guarda el usuario
        Rutina actualizado =rutinaService.updateRutina(rutina);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<String> eliminarRutina(@PathVariable String nombre) {
        boolean eliminado = rutinaService.deleteRutina(nombre);

        if (eliminado) {
            return ResponseEntity.ok("Rutina eliminado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rutina no encontrado");
        }
    }

}
