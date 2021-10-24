package com.algaworks.algafood.infrastructure.service.storage;

import java.io.Serializable;

public class StorageException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
