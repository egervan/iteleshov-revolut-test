package com.iteleshov.revolut;

/**
 * @author iteleshov
 * @since 1.0
 */
public class TransferAmountException extends RuntimeException {
    public TransferAmountException() {
    }

    public TransferAmountException(String message) {
        super(message);
    }

    public TransferAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}