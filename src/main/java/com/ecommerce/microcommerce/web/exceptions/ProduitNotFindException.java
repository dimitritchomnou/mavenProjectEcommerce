package com.ecommerce.microcommerce.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProduitNotFindException extends RuntimeException {
    public ProduitNotFindException(String s) {
        super(s);
    }
}
