package com.iteleshov.revolut.service;

import com.iteleshov.revolut.model.User;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface UserService {
    Optional<User> getByUsername(String username);
    User create(String username, BigDecimal amount);
    BigDecimal transferMoney(String originator, String receiver, BigDecimal amount) throws ExecutionException, InterruptedException;
}
