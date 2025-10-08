package br.com.ifpe.oxefood.modelo.cidade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
@Service
public class CidadeService {
    @Autowired
    CidadeRepository repository;

    @Transactional
    public Cidade save (Cidade cidade){
        cidade.setHabilitado(Boolean.TRUE);

        return repository.save(cidade);
    }

    public List<Cidade> listarTodos(){
        return repository.findAll();
    }

    public Cidade obterPorId(Long id){
        return repository.findById(id).get();
    }

    @Transactional
    public Cidade update(Long id, Cidade cidadeAlterada){
        Cidade cidade = repository.findById(id).get();
        cidade.setNome(cidadeAlterada.getNome());
        cidade.setEstado(cidadeAlterada.getEstado());
        cidade.setQtdPopulacao(cidadeAlterada.getQtdPopulacao());
        cidade.setEhCapital(cidadeAlterada.getEhCapital());
        cidade.setDataFundacao(cidadeAlterada.getDataFundacao());
        

        return repository.save(cidade);

    }

    public void delete (Long id){
        Cidade cidade = repository.findById(id).get();
        cidade.setHabilitado(Boolean.FALSE);
        repository.save(cidade);
    }
}
