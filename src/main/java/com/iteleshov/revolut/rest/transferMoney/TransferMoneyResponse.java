package com.iteleshov.revolut.rest.transferMoney;

import com.iteleshov.revolut.rest.common.AbstractResponse;
import com.iteleshov.revolut.rest.common.ResponseStatus;

/**
 * @author iteleshov
 * @since 1.0
 */
public class TransferMoneyResponse extends AbstractResponse {
    public TransferMoneyResponse(ResponseStatus status, String messageId) {
        super(status, messageId);
    }
}