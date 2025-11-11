package br.com.ifpe.oxefood.modelo.acesso;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Acrescentando mais um metodo para o repository de usuário para buscar pelo username
    //Spring Data JPA irá tratar como se fosse um de seus métodos "prontos"
    Optional<Usuario> findByUsername(String username);

}
