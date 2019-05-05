package com.iteleshov.revolut.rest.transferMoney;

import com.iteleshov.revolut.rest.common.AbstractResponse;
import com.iteleshov.revolut.rest.common.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransferMoneyResponse extends AbstractResponse {
    private BigDecimal originatorBalance;
    public TransferMoneyResponse(ResponseStatus status, String messageId) {
        super(status, messageId);
    }
}