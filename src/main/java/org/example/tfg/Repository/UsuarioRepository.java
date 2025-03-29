package org.example.tfg.Repository;

import org.example.tfg.Dto.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface
UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNicknameAndPassword(String nickname, String password);
}
