package br.com.ifpe.oxefood.modelo.cliente;
//permite manipular entidade cliente
//<Cliente (tipo da classe), Long(ttipo do atributo que usou como chave prmaria)>
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClienteRepository extends JpaRepository <Cliente, Long>  {
    
}
//controller - service repositorio -> banco
//autowired- injeta automaticamente