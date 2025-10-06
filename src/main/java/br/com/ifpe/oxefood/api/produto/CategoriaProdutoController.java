package br.com.ifpe.oxefood.api.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.ifpe.oxefood.modelo.produto.CategoriaProduto;
import br.com.ifpe.oxefood.modelo.produto.CategoriaProdutoService;

public class CategoriaProdutoController {
    @Autowired
    CategoriaProdutoService categoriaProdutoService;
    public ResponseEntity<CategoriaProduto> save (@RequestBody CategoriaProdutoRequest request){
        CategoriaProduto categoriaProduto = categoriaProdutoService.save(request.build());
        return new ResponseEntity<CategoriaProduto>(categoriaProduto,HttpStatus.CREATED);
    }
}
