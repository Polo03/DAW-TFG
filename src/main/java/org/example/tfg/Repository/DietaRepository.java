package org.example.tfg.Repository;

import org.example.tfg.Dto.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DietaRepository extends JpaRepository<Dieta, Integer> {
    @Query("SELECT d FROM Dieta d WHERE d.objetivo LIKE ('SELECT o.objetivo1 FROM Objetivo o WHERE o.usuario = :idUsuario')")
    List<Dieta> findDietaByUsuarioId(Integer idUsuario);
}
