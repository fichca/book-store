package com.company.storage.exceptions;

public class BookWithIdNotExist extends RuntimeException {
    public BookWithIdNotExist() {
    }

    public BookWithIdNotExist(String message) {
        super(message);
    }

    public BookWithIdNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public BookWithIdNotExist(Throwable cause) {
        super(cause);
    }

    public BookWithIdNotExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
