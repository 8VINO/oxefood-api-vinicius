package br.com.ifpe.oxefood.api.produto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.produto.CategoriaProdutoService;
import br.com.ifpe.oxefood.modelo.produto.Produto;
import br.com.ifpe.oxefood.modelo.produto.ProdutoService;

@CrossOrigin
@RestController
@RequestMapping("/api/produto")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody ProdutoRequest request){

        Produto produtoNovo = request.build();
        produtoNovo.setCategoria(categoriaProdutoService.obterPorId(request.getIdCategoria()));
       Produto produto = produtoService.save(produtoNovo);

        return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos(){
        List<Produto> lista =  produtoService.listarTodos();
        return new ResponseEntity<List<Produto>>(lista,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> obterPorId(@PathVariable Long id){
         Produto produto = produtoService.obterPorId(id);
        return new ResponseEntity<Produto>(produto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update (@PathVariable Long id, @RequestBody ProdutoRequest request) {

        Produto produto = request.build();

        produto.setCategoria(categoriaProdutoService.obterPorId(request.getIdCategoria()));

        produtoService.update(id,produto);
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete (@PathVariable Long id){
            produtoService.delete(id);
            return ResponseEntity.ok().build();
        }
    
}
