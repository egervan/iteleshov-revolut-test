package com.iteleshov.revolut.rest.transferMoney;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author iteleshov
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class TransferMoneyResponseBody {
    private BigDecimal originatorBalance;
}
