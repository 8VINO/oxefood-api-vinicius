package br.com.ifpe.oxefood.api.produto;

import br.com.ifpe.oxefood.modelo.produto.CategoriaProduto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter e setter automaticamente
@Builder//
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProdutoRequest {
    @NotBlank(message = "A descrição não pode ser vazia")
    private String descricao;

    public CategoriaProduto build(){
        return CategoriaProduto.builder()
                        .descricao(descricao)
                        .build();
    }
}
