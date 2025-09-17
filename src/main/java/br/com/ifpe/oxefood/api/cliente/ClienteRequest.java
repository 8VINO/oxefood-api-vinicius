package br.com.ifpe.oxefood.api.cliente;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.oxefood.modelo.cliente.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter e setter automaticamente
@Builder//
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    private String nome;
    @JsonFormat(pattern = "dd/MM/yyyy")//altera o formato de data, indentificando por json
    private LocalDate dataNascimento;

   private String cpf;

   private String foneCelular;

   private String foneFixo;

   public Cliente build() {
        //cria um objeto do tipo cliente, preenchendo com os dados do cleinte request
       return Cliente.builder()//instancia o objeto, só funciona se na entidade base (Cliente.java) ter o builder
           .nome(nome)
           .dataNascimento(dataNascimento)
           .cpf(cpf)
           .foneCelular(foneCelular)
           .foneFixo(foneFixo)
           .build();
   }

}
