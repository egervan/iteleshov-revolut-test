package com.iteleshov.revolut.dao.impl;

import com.iteleshov.revolut.dao.UserDao;
import com.iteleshov.revolut.model.User;
import org.jdbi.v3.core.Handle;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final String SELECT_BY_USERNAME = "SELECT * FROM USER WHERE username = :username";
    private static final String CREATE_USER = "INSERT INTO USER (username, balance) VALUES (:username, :balance)";
    private static final String UPDATE_USER_AMOUNT = "UPDATE USER SET balance = :balance WHERE username = :username";

    private final Handle handle;

    @Inject
    public UserDaoImpl(Handle handle) {
        this.handle = handle;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return handle.createQuery(SELECT_BY_USERNAME)
                .bind("username", username)
                .mapToBean(User.class)
                .findOne();
    }

    @Override
    public User create(User user) {
        handle.createUpdate(CREATE_USER)
                .bindBean(user)
                .execute();

        return user;
    }

    @Override
    public void updateAmount(String username, BigDecimal amount) {
        handle.createUpdate(UPDATE_USER_AMOUNT)
                .bind("username", username)
                .bind("balance", amount)
                .execute();
    }
}
