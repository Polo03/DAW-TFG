package org.example.tfg.Controller;

import jakarta.validation.Valid;
import org.example.tfg.Dto.Calendario;
import org.example.tfg.Service.CalendarioService;
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
@RequestMapping("/api/calendario")
//@CacheConfig(cacheNames = {"calendario"})
public class CalendarioController {

    @Autowired
    private CalendarioService calendarioService;

    @GetMapping
    public List<Calendario> getAllCalendario() throws ExecutionException, InterruptedException {
        return calendarioService.getAllCalendario();
    }

    @GetMapping("/{id}")
    //@Cacheable(value = "calendario", key = "#id")
    public List<Calendario> getFechasById(@PathVariable String id) throws ExecutionException, InterruptedException {
        return calendarioService.getFechasById(id);
    }

    @PostMapping
    public ResponseEntity<?> addCalendario(@Valid @RequestBody Calendario calendario, BindingResult result) throws ExecutionException, InterruptedException {
        Map<String, String> errores = new HashMap<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        Calendario creado = calendarioService.addCalendario(calendario);
        return ResponseEntity.ok(creado);
    }

    @PutMapping
    public ResponseEntity<?> updateCalendario(@Valid @RequestBody Calendario calendario, BindingResult result) throws ExecutionException, InterruptedException {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        Calendario actualizado = calendarioService.updateCalendario(calendario);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Calendario no encontrado o id inválido");
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCalendario(@PathVariable String id) {
        boolean eliminado = calendarioService.deleteCalendario(id);
        if (eliminado) {
            return ResponseEntity.ok("Fecha eliminada con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fecha no encontrada");
        }
    }

    @PostMapping("/addOrUpdateFechaActual/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> addOrUpdateFechaActual(@PathVariable String id) {
        try {
            Calendario actualizado = calendarioService.addOrUpdateFechaActual(id);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error añadiendo o actualizando fecha actual");
        }
    }

}
