package br.com.ifpe.oxefood.api.produto;


import br.com.ifpe.oxefood.modelo.produto.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequest {
    
    private Long idCategoria;

    @NotBlank(message="O codigo é de preenchimento obrigatório")
    private String codigo;

    @NotBlank(message="O título é de preenchimento obrigatório")
    private String titulo;

    @NotBlank(message="A descrição é de preenchimento obrigatório")
    private String descricao;

    @NotNull(message="O valor unitário é de preenchimento obrigatório")
    @PositiveOrZero(message = "O valor unitário não pode ser negativo")
    private Double valorUnitario;

    @NotNull(message="O tempo de entrega máximo é de preenchimento obrigatório")
    @PositiveOrZero(message = "O tempo de entrega não pode ser negativo")
    private Integer tempoEntregaMaximo;
    
    @NotNull(message="A tempo de entrega mínimo é de preenchimento obrigatório")
    @PositiveOrZero(message = "O tempo de entrega não pode ser negativo")
    private Integer tempoEntregaMinimo;

    public Produto build(){
        return Produto.builder()
                    .codigo(codigo)
                    .titulo(titulo)
                    .descricao(descricao)
                    .valorUnitario(valorUnitario)
                    .tempoEntregaMinimo(tempoEntregaMinimo)
                    .tempoEntregaMaximo(tempoEntregaMaximo)
                    .build();
    }
}
