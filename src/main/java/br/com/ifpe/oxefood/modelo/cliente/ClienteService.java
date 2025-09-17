package br.com.ifpe.oxefood.modelo.cliente;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
