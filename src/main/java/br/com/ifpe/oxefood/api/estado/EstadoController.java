package br.com.ifpe.oxefood.api.estado;

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

import br.com.ifpe.oxefood.modelo.estado.Estado;
import br.com.ifpe.oxefood.modelo.estado.EstadoService;

@RestController
@RequestMapping("/api/estado")
@CrossOrigin
public class EstadoController {
    @Autowired
    EstadoService estadoService;

    @PostMapping
    public ResponseEntity<Estado> save (@RequestBody EstadoRequest request){
        Estado estado = estadoService.save(request.build());
        return  ResponseEntity.status(HttpStatus.CREATED).body(estado);
    }

    @GetMapping
    public ResponseEntity<List<Estado>> listarTodos(){
        List<Estado> listaEstado = estadoService.listarTodos();

        return ResponseEntity.status(HttpStatus.OK).body(listaEstado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> obterPorId(@PathVariable Long id){
        Estado estado = estadoService.obterPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(estado);
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> update (@PathVariable Long id, @RequestBody EstadoRequest request){
        Estado estado = estadoService.update(id,request.build());

        return ResponseEntity.status(HttpStatus.OK).body(estado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        estadoService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
