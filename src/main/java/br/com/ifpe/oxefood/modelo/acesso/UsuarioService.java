package br.com.ifpe.oxefood.modelo.acesso;

import org.springframework.beans.factory.annotation.Autowired;// Anotação do Spring para injeção de dependências.
import org.springframework.security.authentication.AuthenticationManager;// Gerencia o processo de autenticação.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;// Objeto que representa um token de autenticação com nome de usuário e senha.
import org.springframework.security.core.userdetails.UserDetails;// Interface que representa os detalhes de um usuário para fins de segurança.
import org.springframework.security.core.userdetails.UserDetailsService;// Interface para carregar dados de usuário por nome de usuário.
import org.springframework.security.core.userdetails.UsernameNotFoundException;// Exceção lançada quando um nome de usuário não é encontrado.
import org.springframework.security.crypto.password.PasswordEncoder;// Interface para codificar (criptografar) senhas.
import org.springframework.stereotype.Service;// Anotação do Spring que indica que esta classe é um serviço (camada de lógica de negócios).

import br.com.ifpe.oxefood.modelo.seguranca.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
//A interface UserDetailsService serve como um contrato para o Spring Security. Ela garante que qualquer classe que a implemente forneça a funcionalidade essencial de carregar um UserDetails (que contém informações como username, senha criptografada e papéis/permissões) a partir de um nome de usuário. O Spring Security então usa esse UserDetails para comparar senhas, verificar se o usuário está habilitado, etc., durante o processo de autenticação.

//A interface UserDetailsService possui apenas um método abstrato, qualquer classe que implemente UserDetailsService obrigatoriamente deve fornecer uma implementação para o método loadUserByUsername(String username).
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private JwtService jwtService;

    // Injeção de dependência para a interface PasswordEncoder, usada para criptografar e verificar senhas. É injetado via construtor porque é uma dependência final (não pode ser alterada após a construção).
    private final PasswordEncoder passwordEncoder;

    // Injeção de dependência para o AuthenticationManager, que gerencia o processo de autenticação. Injetado tbm via construtor.
    private final AuthenticationManager authenticationManager;

    //Essa função recupera o objeto Usuario completo do banco de dados a partir do token JWT presente no cabeçalho Authorization da requisição HTTP.
    public Usuario obterUsuarioLogado(HttpServletRequest request) {

        // Inicializa a variável para armazenar o usuário logado.
        Usuario usuarioLogado = null;

        // Obtém o cabeçalho "Authorization" da requisição.
        String authHeader = request.getHeader("Authorization");

        // Verifica se o cabeçalho "Authorization" existe.
        if (authHeader != null) {

            // Extrai o token JWT, removendo o prefixo "Bearer " obtendo o valor apartir do indice 7.
            String jwt = authHeader.substring(7);

            // Extrai o username (geralmente o email) do token JWT.
            String userEmail = jwtService.extractUsername(jwt);

            // Busca o usuário no banco de dados usando o username (email) extraído do token .
            usuarioLogado = findByUsername(userEmail);
            return usuarioLogado;
        }

        return usuarioLogado;
    }

    public UsuarioService(UsuarioRepository userRepository, AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Essa função vai servir para autenticar o usuário que está tentando fazer o login
    /**Fluxo do funcionamento:
    *UsernamePasswordAuthenticationToken é um pedido de autenticação, é um objeto que implementa a interface Authentication (ou seja, mesmo sendo objeto, é conceitualmente um token de autenticação )
    
    *De primeira ele vai armazenar os campos principal(no momento do login é o username), credetials (no momento do login é asenha em texto 
    plano) e a propriedade authenticated = false.

    *Passsando para o authenticationManager.autenticate: o AuthenticationManager por si só n vai realizar a autenticação. Ele possui uma lista de varios AuthenticationProviders configurados e o provedor para esse tipo de token é o DaoAuthenticationProvider (que é o provedor padrão para autenticação baseada em nome de usuário/senha e UserDetailsService)

    *Ele vai receber o UsernamePasswordAuthenticationToken não autenticado e vai buscar detalhes para comparar com as informações que estão no banco para conseguir autenticar. Para fazer essa comparação ele vai chamar o método loadByUsername(q tá na próprio UsuarioService) para obter objeto Usuário que possui aquele username e vai criptografar a senha de tentativa de login usando o PasswordEncoder e comparar com a senha contida no banco daquele usuário.
    * Se as senhas não corresponderem, o DaoAuthenticationProvider lançará uma BadCredentialsException, e o processo de autenticação falhará. 

    *Se a senha for validada com sucesso, o DaoAuthenticationProvider considera o usuário autenticado. Ele criaria um novo UsernamePasswordAuthenticationToken com campo principal sendo o objeto Usuário, credentials sendo string vazia (por seurança), uma collection de GrantedAuthority que são as roles do usuário obtidas do UserDetails e a propriedade authenticated como TRUE

    *DaoAuthenticationProvider retorna para o AuthenticationManager e pronto.

    *Geralmente o token autenticado é armazenado no SecurityContextHolder. Isso seria o que realmente "loga" o usuário na sessão.
    
    */
    public Usuario authenticate(String username, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        return repository.findByUsername(username).orElseThrow();
    }

    @Transactional
    public Usuario findByUsername(String username) {

        return repository.findByUsername(username).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByUsername(username).get();
    }

    @Transactional
    public Usuario save(Usuario user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setHabilitado(Boolean.TRUE);
        return repository.save(user);
    }
}
