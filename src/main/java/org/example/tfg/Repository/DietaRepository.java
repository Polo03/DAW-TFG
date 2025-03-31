package org.example.tfg.Repository;

import org.example.tfg.Dto.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DietaRepository extends JpaRepository<Dieta, Integer> {
    @Query(value = "SELECT * FROM dietas d WHERE d.objetivo LIKE (SELECT o.objetivo1 FROM objetivos o WHERE o.usuario_id = :idUsuario) AND d.dia_semana LIKE :dia",
            nativeQuery = true)
    List<Dieta> findDietaByUsuarioId(@Param("idUsuario") Integer idUsuario, @Param("dia") String dia);
}
