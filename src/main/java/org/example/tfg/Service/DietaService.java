package org.example.tfg.Service;

import org.example.tfg.Dto.Dieta;
import org.example.tfg.Dto.Usuario;
import org.example.tfg.Repository.DietaRepository;
import org.example.tfg.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DietaService {
    @Autowired
    private DietaRepository dietaRepository;

    // Obtener todaslas dietas
    public List<Dieta> obtenerTodasDietas() {
        return dietaRepository.findAll();
    }

    // Obtener dieta by ID
    public Optional<Dieta> obtenerDietaByID(Integer id) {
        return dietaRepository.findById(id);
    }

    //Guardar dieta
    public Dieta guardarDieta(Dieta dieta) {
        Dieta dietaGuardar = dietaRepository.save(dieta);
        return dietaGuardar;
    }

    //Actualizar dieta
    public boolean actualizarDieta(Dieta nuevaDieta) {
        Optional<Dieta> dietaExistente = dietaRepository.findById(nuevaDieta.getId());
        if (dietaExistente.isPresent()) {
            dietaRepository.save(nuevaDieta);
            return true;
        }
        return false;
    }

    //Eliminar dieta
    public boolean eliminarDieta(Integer id) {
        if (dietaRepository.existsById(id)) {
            dietaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Dieta> getAllDietasByIdUsuario(Integer idUsuario, String dia){
        return dietaRepository.findDietaByUsuarioId(idUsuario, dia);
    }

}
