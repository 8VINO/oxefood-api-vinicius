package br.com.ifpe.oxefood.util.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@MappedSuperclass//só serve para ser superclassse
public abstract class EntidadeNegocio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)//especie d evariavel que armazena no banco q é incrementada, serve para incrementar, indicar um valor que vai incrementar

    private Long id;

    @JsonIgnore
    @Column

    private Boolean habilitado;
    
}
