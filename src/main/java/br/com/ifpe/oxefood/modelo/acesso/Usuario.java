package br.com.ifpe.oxefood.modelo.acesso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;// Interface do Spring Security para permissões

import org.springframework.security.core.userdetails.UserDetails;// Interface do Spring Security para detalhes do usuário

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifpe.oxefood.util.entity.EntidadeNegocio;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Usuario")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//O UserDetails é uma inteface que faz com que a classe represente um usuário de segurança, contendo informações necessária para o Spring Security autenticar e autorizar os usuarios
public class Usuario extends EntidadeNegocio implements UserDetails {

   @Column(nullable = false, unique = true)
   private String username;// Nome de usuário único para login
     
   @JsonIgnore // Impede que a senha seja serializada em respostas JSON
   @Column(nullable = false)
   private String password;

   @JsonIgnore// Impede que as roles sejam serializadas diretamente em respostas JSON

   //O @ElementCollection vai servir para criar uma tabela intermediária entre role(Perfil) e usuários(Usuario), como se fosse um relacionamento Many-to-Many
   @ElementCollection(fetch = FetchType.EAGER)// Carrega as roles junto com o usuário
   @Builder.Default// Lombok: Define um valor padrão se não for especificado no builder
   private List<Perfil> roles = new ArrayList<>();// Lista de perfis/permissões do usuário

   //Retorna uma coleção de roles do usuário. O Spring Security usa isso para verificar a autorização do usuário.
   //A classe Perfil implementa GrantedAuthority,permitindo que a lista de Perfil seja retornada diretamente.

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
       return roles;
   }

   @Override
   public String getUsername() {
       return username;
   }

   public String getPassword() {
       return password;
   }
   //Indica se a conta do usuário expirou.
   @Override
   public boolean isAccountNonExpired() {
       return true;
   }
   // Indica se a conta do usuário está bloqueada.
   @Override
   public boolean isAccountNonLocked() {
       return true;
   }

   //Indica se as credenciais do usuário expiraram.
   @Override
   public boolean isCredentialsNonExpired() {
       return true;
   }

   //Indica se o usuário está habilitado.
   @Override
   public boolean isEnabled() {
       return true;
   }
}
