package com.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProductSaveException extends RuntimeException {

    public ProductSaveException(String message) {
        super(message);
    }

}
