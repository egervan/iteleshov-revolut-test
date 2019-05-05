package com.iteleshov.revolut.dao;

import com.iteleshov.revolut.model.User;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserDao {
    Optional<User> getByUsername(String username);
    User create(User user);
    void updateAmount(String username, BigDecimal amount);
}
