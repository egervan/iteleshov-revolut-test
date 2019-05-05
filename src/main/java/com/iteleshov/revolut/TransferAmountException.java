package com.iteleshov.revolut;

public class TransferAmountException extends RuntimeException {
    public TransferAmountException(String message) {
        super(message);
    }
}