package com.iteleshov.revolut.dao.impl;

import com.iteleshov.revolut.dao.UserDao;
import com.iteleshov.revolut.model.User;
import lombok.AllArgsConstructor;
import org.jdbi.v3.core.Handle;

import java.math.BigDecimal;

@AllArgsConstructor
public class UserDaoImpl implements UserDao {
    private static final String SELECT_BY_USERNAME = "SELECT * FROM USER WHERE username = :username";
    private static final String CREATE_USER = "INSERT INTO USER (username, balance) VALUES (:username, :balance)";
    private static final String UPDATE_USER_AMOUNT = "UPDATE USER SET balance = :balance WHERE username = :username";

    private final Handle handle;

    @Override
    public User getByUsername(String username) {
        return handle.createQuery(SELECT_BY_USERNAME)
                .bind("username", username)
                .mapToBean(User.class)
                .one();
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
