package br.com.ifpe.oxefood.api.entregador;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.oxefood.modelo.entregador.Entregador;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Lombok: cria getters, setters, toString, equals e hashCode
@Data

// Lombok: gera um builder para esta classe,só vai funcionar se em Entregador.java tbm tiver o @Builder
@Builder

@NoArgsConstructor

@AllArgsConstructor

public class EntregadorRequest {

    @NotBlank(message = "O nome é de preenchimento obrigatório")
    private String nome;

    @NotBlank(message = "O CPF é de preenchimento obrigatório")
    @CPF
    private String cpf;

    @NotBlank(message = "O RG é de preenchimento obrigatório")
    private String rg;

    // Define o formato de data esperado no JSON: "dd/MM/yyyy"
    @NotNull(message = "A data de nascimento é de preenchimento obrigatório")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Length(min = 8, max = 20, message = "O campo Fone tem que ter entre {min} e {max} caracteres")
    private String foneCelular;

    private String foneFixo;

    @NotNull(message = "A quantidade de entregas realizadas é de preenchimento obrigatório")
    @PositiveOrZero(message = "A quantidade de entregas realizadas não pode ser negativa")
    private Integer qtdEntregasRealizadas;
    
    @NotNull(message = "O valor do frete é de preenchimento obrigatório")
    @PositiveOrZero(message = "O valor do frete não pode ser negativo")
    private Double valorFrete;

    @NotBlank(message = "O endereço da rua é de preenchimento obrigatório")
    private String enderecoRua;

    @NotBlank(message = "O complemento é de preenchimento obrigatório")
    private String enderecoComplemento;

    @NotBlank(message = "O número da residência é de preenchimento obrigatório")
    private String enderecoNumero;

    @NotBlank(message = "As informações do bairro é de preenchimento obrigatório")
    private String enderecoBairro;

    @NotBlank(message = "As informações da cidade é de preenchimento obrigatório")
    private String enderecoCidade;

    @NotBlank(message = "O número do CEP é de preenchimento obrigatório")
    private String enderecoCep;

    @NotBlank(message = "A unidade federativa é de preenchimento obrigatório")
    private String enderecoUf;

    private Boolean ativo;
    
    // Método que converte este DTO em uma entidade Entregador
    public Entregador build(){
        return Entregador.builder() // --> Inicializa o builder da entidade Entregador, ou seja,inicializa o construtor fluente. 
        //O construtor fluente você encadeie chamadas de métodos, ou seja, não precisa ficar repetindo o nome do objeto a cada operação.
        //Cada método retorna o próprio objeto builder, então você não precisa escrever o nome do objeto várias vezes.
            .nome(nome) // passa o campo nome e retorna o builder para permitir o encadeamento
            .cpf(cpf)
            .rg(rg)
            .dataNascimento(dataNascimento)
            .foneCelular(foneCelular)
            .foneFixo(foneFixo)
            .qtdEntregasRealizadas(qtdEntregasRealizadas)
            .valorFrete(valorFrete)
            .enderecoRua(enderecoRua)
            .enderecoComplemento(enderecoComplemento)
            .enderecoNumero(enderecoNumero)
            .enderecoBairro(enderecoBairro)
            .enderecoCidade(enderecoCidade)
            .enderecoCep(enderecoCep)
            .enderecoUf(enderecoUf)
            .ativo(ativo)
            .build();// --> n esquece de colocar pois ele cria o objeto Entregador, ele  finaliza a construção e retorna a instância final de Entregador preenchida com todos os valores que você passou. Diferente dos outros parametros o .build() retorna o objeto final e não o builder
    }
}
