package br.com.residencia.ecommerce.validador;

public class ValidationException extends Exception {

    public ValidationException(String mensagem) {
        super(mensagem);
    }
}
