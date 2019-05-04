package com.iteleshov.revolut.dao;

import com.iteleshov.revolut.model.User;

import java.math.BigDecimal;

/**
 * @author iteleshov
 * @since 1.0
 */
public interface UserDao {
    User getByUsername(String username);
    User create(User user);
    void updateAmount(String username, BigDecimal amount);
}
