package org.example.tfg.Controller;

import jakarta.validation.Valid;
import org.example.tfg.Dto.Dieta;
import org.example.tfg.Dto.LoginRequest;
import org.example.tfg.Dto.Usuario;
import org.example.tfg.Service.DietaService;
import org.example.tfg.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/dietas")
@CacheConfig(cacheNames = {"dietas"})
public class DietaController {
    @Autowired
    private DietaService dietaService;

    // Obtener todas las dietas
    @GetMapping
    public ResponseEntity<List<Dieta>> obtenerTodasDietas() {
        List<Dieta> dietas = dietaService.obtenerTodasDietas();
        return new ResponseEntity<>(dietas, HttpStatus.OK);
    }

    //Obtener dieta por ID
    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity<Optional<Dieta>> obtenerDietaPorId(@PathVariable Integer id) {
        try{
            Thread.sleep(3000);
            Optional<Dieta> dieta = dietaService.obtenerDietaByID(id);
            return new ResponseEntity<>(dieta, HttpStatus.OK);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    // Crear dieta
    @PostMapping
    public ResponseEntity<String> guardarDieta(@RequestBody @Valid Dieta dieta) {
        Dieta dietaGuardar = dietaService.guardarDieta(dieta);
        if (dietaGuardar != null) {
            return ResponseEntity.ok("Dieta guardado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dieta no guardado");
        }
    }

    //Actualizar dieta
    @PutMapping
    public ResponseEntity<String> actualizarDieta(@RequestBody @Valid Dieta nuevaDieta) {
        boolean actualizado = dietaService.actualizarDieta(nuevaDieta);
        if (actualizado) {
            return ResponseEntity.ok("Dieta actualizado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dieta no encontrada");
        }
    }

    //Eliminar un dieta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDieta(@PathVariable int id) {
        boolean eliminado = dietaService.eliminarDieta(id);

        if (eliminado) {
            return ResponseEntity.ok("Dieta eliminado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dieta no encontrada");
        }
    }

    @GetMapping("/getDietaByIdUser/{idUsuario}")
    public ResponseEntity<List<Dieta>> dameDietaPorIdUsuario(@PathVariable int idUsuario) {
        // Obtener el día de la semana en español
        String diaSemana = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        // Capitalizar la primera letra
        diaSemana = diaSemana.substring(0, 1).toUpperCase() + diaSemana.substring(1);
        List<Dieta> dietas = dietaService.getAllDietasByIdUsuario(idUsuario, diaSemana);
        return ResponseEntity.ok(dietas);
    }
}
