package br.com.ifpe.oxefood.modelo.entregador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class EntregadorService {

    // Injeta automaticamente uma instância de EndtregadorRepository para usar os metodos prontos
    @Autowired
    private EntregadorRepository repository;

   // Este método será executado dentro de uma transação
   // Se der erro, tudo é desfeito (rollback)
    @Transactional
    public Entregador save(Entregador entregador){

        // Antes de salvar, garante que o entregador está habilitado
        entregador.setHabilitado(Boolean.TRUE);

        // Usa o repository para salvar no banco de dados
        return repository.save(entregador); //--> ele salva (ou atualiza) o objeto no banco de dados. Retorna o mesmo objeto, mas possivelmente atualizado.
    }

    public List<Entregador> listarTodos(){
        return repository.findAll();
    }

    public Entregador obterPorId(Long id){
        return repository.findById(id).get();
    }

    @Transactional
    public Entregador update (Long id, Entregador entregadorAlterado){
        Entregador entregador= repository.findById(id).get();
        entregador.setNome(entregadorAlterado.getNome());
        entregador.setCpf(entregadorAlterado.getCpf());
        entregador.setRg(entregadorAlterado.getRg());
        entregador.setDataNascimento(entregadorAlterado.getDataNascimento());
        entregador.setFoneCelular(entregadorAlterado.getFoneCelular());
        entregador.setFoneFixo(entregadorAlterado.getFoneFixo());
        entregador.setQtdEntregasRealizadas(entregadorAlterado.getQtdEntregasRealizadas());
        entregador.setValorFrete(entregadorAlterado.getValorFrete());
        entregador.setEnderecoRua(entregadorAlterado.getEnderecoRua());
        entregador.setEnderecoComplemento(entregadorAlterado.getEnderecoComplemento());
        entregador.setEnderecoNumero(entregadorAlterado.getEnderecoNumero());
        entregador.setEnderecoBairro(entregadorAlterado.getEnderecoBairro());
        entregador.setEnderecoCidade(entregadorAlterado.getEnderecoCidade());
        entregador.setEnderecoCep(entregadorAlterado.getEnderecoCep());
        entregador.setEnderecoUf(entregadorAlterado.getEnderecoUf());
        entregador.setAtivo(entregadorAlterado.getAtivo());

        return repository.save(entregador);
    }

    @Transactional
    public void delete (Long id){
        Entregador entregador = repository.findById(id).get();
        entregador.setHabilitado(Boolean.FALSE);

        repository.save(entregador);
    }
    
}
