package br.com.ifpe.oxefood.modelo.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

   @Autowired
   private ClienteRepository repository;
    //funcoes de consulta n precisa de transacional
   @Transactional//criar um bloco transacional nesse metodo, ou executa tudo ou executa nada, agrupap varias operações no banco
   public Cliente save(Cliente cliente) {

       cliente.setHabilitado(Boolean.TRUE);
       return repository.save(cliente);

       
   }
   
    public List<Cliente> listarTodos() {
  
        return repository.findAll();//Indepente se tiver vazio ou n, a lista vai ser retornada
    }

    public Cliente obterPorID(Long id) {

        return repository.findById(id).get(); 
            // Por que ".get()"?
    // O método está definido para retornar um objeto do tipo Cliente.
    // Porém, o findById retorna um Optional<Cliente>, que pode estar vazio
    // (quando não encontra o registro) ou conter um Cliente (quando encontra).
    // Como a função deve retornar um Cliente e não um Optional,
    // precisamos "tirar" o valor de dentro do Optional.
    //
    // O .get() faz isso, mas se o Optional estiver vazio, ele lança
    // NoSuchElementException. Isso funciona, mas não é recomendado,
    // porque a exceção não é muito descritiva.
    //
    // O mais recomendado é usar orElseThrow(), onde você pode lançar
    // uma exceção personalizada e dar uma mensagem mais clara.

   //MESMO CASO USANDO orElseThrow:
    // return repository.findById(id)
    //     .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));


    // findById(id) → retorna um Optional<Cliente>.

    // .orElseThrow(...) → abre o Optional:

    // Se tiver um valor → retorna o Cliente de dentro.

    // Se estiver vazio → lança a exceção que você definiu.

    // Ou seja: o orElseThrow faz o mesmo papel do .get(), mas de forma mais segura e expressiva.

    //resumo:
    // .get() → tira o valor de dentro, mas estoura exceção feia (NoSuchElementException) se vazio.
    // .orElse(...) → tira o valor ou retorna um default.
    // .orElseThrow(...) → tira o valor ou lança uma exceção customizada.
    }

    public void update(Long id, Cliente clienteAlterado){
        Cliente cliente = repository.findById(id).get();
        cliente.setNome(clienteAlterado.getNome());
        cliente.setDataNascimento(clienteAlterado.getDataNascimento());
        cliente.setCpf(clienteAlterado.getCpf());
        cliente.setFoneCelular(clienteAlterado.getFoneCelular());
        cliente.setFoneFixo(clienteAlterado.getFoneFixo());

        repository.save(cliente);
    }

}
