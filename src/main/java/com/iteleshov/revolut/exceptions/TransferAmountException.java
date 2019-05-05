package com.iteleshov.revolut.exceptions;

public class TransferAmountException extends RuntimeException {
    public TransferAmountException(String message) {
        super(message);
    }
}