package br.com.ifpe.oxefood.api.cliente;

import java.time.LocalDate;
import java.util.Arrays;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.oxefood.modelo.acesso.Perfil;
import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import br.com.ifpe.oxefood.modelo.cliente.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getter e setter automaticamente
@Builder //
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    // @NotNull(message = "O Nome é de preenchimento obrigatório")
    // @NotEmpty(message = "O Nome é de preenchimento obrigatório")
    @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
    @NotBlank(message = "O e-mail é de preenchimento obrigatório")
    @Email
    private String email;

    @NotBlank(message = "A senha é de preenchimento obrigatório")
    private String password;

    @NotBlank(message = "O nome é de preenchimento obrigatório")
    private String nome;

    @NotNull(message = "A data de nascimento é de preenchimento obrigatório")
    @JsonFormat(pattern = "dd/MM/yyyy") // altera o formato de data, indentificando por json
    private LocalDate dataNascimento;

    @NotBlank(message = "O CPF é de preenchimento obrigatório")
    @CPF
    private String cpf;

    @Length(min = 8, max = 20, message = "O campo Fone tem que ter entre {min} e {max} caracteres")
    private String foneCelular;

    private String foneFixo;
        public Usuario buildUsuario() {
       return Usuario.builder()
           .username(email)
           .password(password)
           //Arrays.asList(new Perfil(Perfil.ROLE_CLIENTE)) vai criar um novo objeto de perfil e esse novo objeto vai ser retornado, e logo em seguida será encaixado dentro de uma lista
           .roles(Arrays.asList(new Perfil(Perfil.ROLE_CLIENTE)))
           .build();
   }


    public Cliente build() {
        // cria um objeto do tipo cliente, preenchendo com os dados do cleinte request
        return Cliente.builder()// instancia o objeto, só funciona se na entidade base (Cliente.java) ter o
                                // builder
                .usuario(buildUsuario())
                .nome(nome)
                .dataNascimento(dataNascimento)
                .cpf(cpf)
                .foneCelular(foneCelular)
                .foneFixo(foneFixo)
                .build();
    }

}
