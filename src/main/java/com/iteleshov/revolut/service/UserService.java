package com.iteleshov.revolut.service;

import com.iteleshov.revolut.model.User;

import java.math.BigDecimal;

/**
 * @author iteleshov
 * @since 1.0
 */
public interface UserService {
    User getByUsername(String username);
    void transferMoney(String originator, String receiver, BigDecimal amount);
}
