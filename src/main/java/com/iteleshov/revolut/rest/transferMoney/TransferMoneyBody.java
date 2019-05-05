package com.iteleshov.revolut.rest.transferMoney;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyBody {
    private String originator;
    private String receiver;
    private BigDecimal amount;
}