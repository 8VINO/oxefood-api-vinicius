package br.com.ifpe.oxefood.api.cidade;

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


import br.com.ifpe.oxefood.modelo.cidade.Cidade;
import br.com.ifpe.oxefood.modelo.cidade.CidadeService;
import br.com.ifpe.oxefood.modelo.estado.EstadoService;


@CrossOrigin
@RestController
@RequestMapping("/api/cidade")
public class CidadeController {
    
    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private EstadoService estadoService;

    @PostMapping
    public ResponseEntity<Cidade> save(@RequestBody CidadeRequest request){

        Cidade cidadeNova = request.build();
        cidadeNova.setEstado(estadoService.obterPorId(request.getIdEstado()));
       Cidade cidade = cidadeService.save(cidadeNova);

        return new ResponseEntity<Cidade>(cidade, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cidade>> listarTodos(){
        List<Cidade> lista =  cidadeService.listarTodos();
        return new ResponseEntity<List<Cidade>>(lista,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> obterPorId(@PathVariable Long id){
         Cidade cidade = cidadeService.obterPorId(id);
        return new ResponseEntity<Cidade>(cidade, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> update (@PathVariable Long id, @RequestBody CidadeRequest request) {

        Cidade cidade = request.build();

        cidade.setEstado(estadoService.obterPorId(request.getIdEstado()));

        Cidade cidadeAtualizada=cidadeService.update(id,cidade);
        
        return ResponseEntity.status(HttpStatus.OK).body(cidadeAtualizada);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete (@PathVariable Long id){
            cidadeService.delete(id);
            return ResponseEntity.ok().build();
        }
    
}
