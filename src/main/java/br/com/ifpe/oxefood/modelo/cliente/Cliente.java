package br.com.ifpe.oxefood.modelo.cliente;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Cliente")
@SQLRestriction("habilitado = true")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends EntidadeAuditavel {
   
   //A coluna que será usada para unir as duas tabelas no banco de dados.
   // Indica que a coluna no banco de dados que representa a chave estrangeira para Usuario não pode ser nula
   @OneToOne
   @JoinColumn(nullable = false)
   private Usuario usuario;

   //orphanRemoval = true: quer dizer se um endereço não tem mais um cliente pai, ele é um órfão e deve ser excluído. Caso o cliente remova um endereço de suas collection, ao salvar o cliente, o endereço sera removido na tabela EndereçoCliente tbm(se n ficaria null na coluna de cliente, e o endereço n teria nenhum cliente para ser associado, ficando orfão)
   @OneToMany(mappedBy = "cliente", orphanRemoval = true, fetch = FetchType.EAGER)
   //o SUBSELECT vai otimizar o carregamento de collections evitando o problema de  N+1
   @Fetch(FetchMode.SUBSELECT)
   private List<EnderecoCliente> enderecos;
   @Column(nullable = false, length = 100)
   private String nome;
   @Column
   private LocalDate dataNascimento;
   @Column(unique=true)
   private String cpf;
   @Column
   private String foneCelular;
   @Column
   private String foneFixo;

}
