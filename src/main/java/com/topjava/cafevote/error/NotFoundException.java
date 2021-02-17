package com.topjava.cafevote.error;

public class NotFoundException extends IllegalRequestDataException {
    public NotFoundException(String msg) {
        super(msg);
    }
}
