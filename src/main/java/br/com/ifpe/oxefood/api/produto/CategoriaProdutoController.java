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

import br.com.ifpe.oxefood.modelo.produto.CategoriaProduto;
import br.com.ifpe.oxefood.modelo.produto.CategoriaProdutoService;
@RestController
@RequestMapping("/api/categoria")
@CrossOrigin
public class CategoriaProdutoController {
    @Autowired
    CategoriaProdutoService categoriaProdutoService;

    @PostMapping
    public ResponseEntity<CategoriaProduto> save (@RequestBody CategoriaProdutoRequest request){
        CategoriaProduto categoriaProduto = categoriaProdutoService.save(request.build());
        return  ResponseEntity.status(HttpStatus.CREATED).body(categoriaProduto);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaProduto>> listarTodos(){
        List<CategoriaProduto> listaCategoriaProduto = categoriaProdutoService.listarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(listaCategoriaProduto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProduto> obterPorId(@PathVariable Long id){
        CategoriaProduto categoriaProduto = categoriaProdutoService.obterPorId(id);

        return ResponseEntity.ok(categoriaProduto).build();
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProduto> update (@PathVariable Long id, @RequestBody CategoriaProdutoRequest request){
        CategoriaProduto categoriaProduto = categoriaProdutoService.update(id,request.build());

        return ResponseEntity.status(HttpStatus.OK).body(categoriaProduto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        categoriaProdutoService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
