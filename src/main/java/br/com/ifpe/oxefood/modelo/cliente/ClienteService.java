package br.com.ifpe.oxefood.modelo.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.modelo.acesso.Perfil;
import br.com.ifpe.oxefood.modelo.acesso.PerfilRepository;
import br.com.ifpe.oxefood.modelo.acesso.UsuarioService;
import br.com.ifpe.oxefood.util.exception.ClienteException;
import br.com.ifpe.oxefood.util.exception.EntidadeNaoEncontradaException;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

   @Autowired
   private ClienteRepository repository;

   @Autowired
   private EnderecoClienteRepository enderecoClienteRepository;

      @Autowired
   private UsuarioService usuarioService;

   @Autowired
   private PerfilRepository perfilUsuarioRepository;

   

    //funcoes de consulta n precisa de transacional
   @Transactional//criar um bloco transacional nesse metodo, ou executa tudo ou executa nada, agrupap varias operações no banco
   public Cliente save(Cliente cliente) {
       String foneCelular = cliente.getFoneCelular(); 
       if(!foneCelular.startsWith("(81)") && !foneCelular.startsWith("81")){
            throw new ClienteException(ClienteException.DDD_FIXO);
       }
       usuarioService.save(cliente.getUsuario());

      for (Perfil perfil : cliente.getUsuario().getRoles()) {
           perfil.setHabilitado(Boolean.TRUE);
           perfilUsuarioRepository.save(perfil);
      }

       cliente.setHabilitado(Boolean.TRUE);
       return repository.save(cliente);
       
       
   }
   
    public List<Cliente> listarTodos() {
  
        return repository.findAll();//Indepente se tiver vazio ou n, a lista vai ser retornada
    }

    public Cliente obterPorID(Long id) {

         Optional<Cliente> consulta = repository.findById(id);
  
       if (consulta.isPresent()) {
           return consulta.get();
       } else {
           throw new EntidadeNaoEncontradaException("Cliente", id);
       }

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
    @Transactional
    public void update(Long id, Cliente clienteAlterado){
        Cliente cliente = repository.findById(id).get();
        cliente.setNome(clienteAlterado.getNome());
        cliente.setDataNascimento(clienteAlterado.getDataNascimento());
        cliente.setCpf(clienteAlterado.getCpf());
        cliente.setFoneCelular(clienteAlterado.getFoneCelular());
        cliente.setFoneFixo(clienteAlterado.getFoneFixo());

        repository.save(cliente);
    }
    @Transactional
    public void delete(Long id){
        Cliente cliente = repository.findById(id).get();
        cliente.setHabilitado(Boolean.FALSE);

        repository.save(cliente);
    }

    //ENDEREÇO
    @Transactional
   public EnderecoCliente adicionarEnderecoCliente(Long clienteId, EnderecoCliente endereco) {

       Cliente cliente = this.obterPorID(clienteId);
      
       //Primeiro salva o EnderecoCliente:

       endereco.setCliente(cliente);
       endereco.setHabilitado(Boolean.TRUE);
       enderecoClienteRepository.save(endereco);
      
       //Depois acrescenta o endereço criado ao cliente e atualiza o cliente:

       List<EnderecoCliente> listaEnderecoCliente = cliente.getEnderecos();
      
       if (listaEnderecoCliente == null) {
           listaEnderecoCliente = new ArrayList<EnderecoCliente>();
       }
      
       listaEnderecoCliente.add(endereco);
       cliente.setEnderecos(listaEnderecoCliente);
       repository.save(cliente);
      
       return endereco;
   }

    @Transactional
   public EnderecoCliente atualizarEnderecoCliente(Long id, EnderecoCliente enderecoAlterado) {

       EnderecoCliente endereco = enderecoClienteRepository.findById(id).get();
       endereco.setRua(enderecoAlterado.getRua());
       endereco.setNumero(enderecoAlterado.getNumero());
       endereco.setBairro(enderecoAlterado.getBairro());
       endereco.setCep(enderecoAlterado.getCep());
       endereco.setCidade(enderecoAlterado.getCidade());
       endereco.setEstado(enderecoAlterado.getEstado());
       endereco.setComplemento(enderecoAlterado.getComplemento());

       return enderecoClienteRepository.save(endereco);
   }

   @Transactional
   public void removerEnderecoCliente(Long idEndereco) {

       EnderecoCliente endereco = enderecoClienteRepository.findById(idEndereco).get();
       endereco.setHabilitado(Boolean.FALSE);
       enderecoClienteRepository.save(endereco);

       Cliente cliente = this.obterPorID(endereco.getCliente().getId());
       cliente.getEnderecos().remove(endereco);
       repository.save(cliente);
   }


}
