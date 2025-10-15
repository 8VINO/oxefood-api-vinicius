package br.com.ifpe.oxefood.util.exception;

public class ClienteException extends RuntimeException{
     public static final String DDD_FIXO = "Não é permitido número de telefones que não possua o DDD 81.";

    public ClienteException(String msg) {

	super(String.format(msg));
    }

}
