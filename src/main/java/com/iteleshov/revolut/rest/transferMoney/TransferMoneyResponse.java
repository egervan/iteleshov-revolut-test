package com.iteleshov.revolut.rest.transferMoney;

import com.iteleshov.revolut.rest.common.AbstractResponse;
import com.iteleshov.revolut.rest.common.ResponseHeader;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferMoneyResponse extends AbstractResponse<TransferMoneyResponseBody> {
    public TransferMoneyResponse(ResponseHeader header) {
        super(header);
    }
}