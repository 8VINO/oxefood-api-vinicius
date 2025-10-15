package br.com.ifpe.oxefood.modelo.acesso;

import org.springframework.security.core.GrantedAuthority;

import br.com.ifpe.oxefood.util.entity.EntidadeNegocio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Perfil extends EntidadeNegocio implements GrantedAuthority {
    
   public static final String ROLE_CLIENTE = "CLIENTE";
   public static final String ROLE_FUNCIONARIO_ADMIN = "ROLE_FUNCIONARIO_ADMIN"; // READ, DELETE, WRITE, UPDATE.
   public static final String ROLE_FUNCIONARIO_USER = "ROLE_FUNCIONARIO_USER"; // READ, WRITE, UPDATE.
  
   @Column
   private String nome;
  
   @Override
   public String getAuthority() {
       return this.nome;
   }
   public Perfil(String nome) {
    this.nome = nome;
}
}
