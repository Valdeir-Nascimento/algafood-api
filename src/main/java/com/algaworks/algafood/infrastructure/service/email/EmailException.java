package com.algaworks.algafood.infrastructure.service.email;

import java.io.Serializable;

public class EmailException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

}
