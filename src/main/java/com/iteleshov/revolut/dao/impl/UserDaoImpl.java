package com.iteleshov.revolut.dao.impl;

import com.iteleshov.revolut.dao.UserDao;
import com.iteleshov.revolut.model.User;
import lombok.AllArgsConstructor;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.math.BigDecimal;

/**
 * @author iteleshov
 * @since 1.0
 */
@AllArgsConstructor
public class UserDaoImpl implements UserDao {
    private final Jdbi jdbi;
    private final Handle handle;

    @Override
    public User getByUsername(String username) {
        return handle.createQuery("SELECT * FROM USER WHERE username = :username")
                .bind("username", username)
                .mapToBean(User.class)
                .one();
    }

    @Override
    public User create(User user) {
        handle.createUpdate("INSERT INTO user (username, amount) VALUES (:username, :amount)")
                .bindBean(user)
                .execute();

        return user;
    }

    @Override
    public void updateAmount(String username, BigDecimal amount) {
        handle.createUpdate("UPDATE user SET amount = :amount WHERE username = :username")
                .bind("username", username)
                .bind("amount", amount)
                .execute();
    }
}
