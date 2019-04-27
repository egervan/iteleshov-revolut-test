package com.iteleshov.revolut.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author iteleshov
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyRequest {
    private String messageId;
    private String originator;
    private String receiver;
    private BigDecimal amount;
}