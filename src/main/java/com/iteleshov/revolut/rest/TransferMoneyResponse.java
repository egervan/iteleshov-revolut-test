package com.iteleshov.revolut.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author iteleshov
 * @since 1.0
 */
@Setter
@Getter
@NoArgsConstructor
public class TransferMoneyResponse {
    private ResponseStatus status;
    private String messageId;
    private String errorMessage;

    public TransferMoneyResponse(ResponseStatus status, String messageId) {
        this.status = status;
        this.messageId = messageId;
    }
}