package br.com.ifpe.oxefood.util.exception;

public class EntregadorException extends RuntimeException {
     public static final String DDD_FIXO = "Não é permitido número de telefones que não possua o DDD 81.";

    public EntregadorException(String msg) {

	super(String.format(msg));
    }
}
