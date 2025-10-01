package br.com.ifpe.oxefood.modelo.categoriaProduto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.transaction.Transactional;

@Service
public class CategoriaProdutoService {
    @Autowired
    CategoriaProdutoRepository repository;

    @Transactional
    @PostMapping
    public CategoriaProduto save (CategoriaProduto categoriaProduto){
        categoriaProduto.setHabilitado(Boolean.TRUE);
        return repository.save(categoriaProduto);
    }
    
    
}
