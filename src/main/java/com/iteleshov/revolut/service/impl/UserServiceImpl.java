package com.iteleshov.revolut.service.impl;

import com.iteleshov.revolut.TransferAmountException;
import com.iteleshov.revolut.dao.UserDao;
import com.iteleshov.revolut.model.User;
import com.iteleshov.revolut.service.UserService;
import lombok.AllArgsConstructor;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.math.BigDecimal;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static org.jdbi.v3.core.transaction.TransactionIsolationLevel.REPEATABLE_READ;

/**
 * @author iteleshov
 * @since 1.0
 */
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    public static void main(String[] args) {
        System.out.println(new BigDecimal("1").compareTo(ZERO));
    }

    @Transaction(REPEATABLE_READ)
    public void transferMoney(String originator, String receiver, BigDecimal amount) {
        requireNonNull(originator, "Originator must be present");
        requireNonNull(receiver, "Receiver must be present");
        requireNonNull(amount, "Amount can't be 'null'");
        if (amount.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        final User originatorUser = userDao.getByUsername(originator);
        if (isNull(originatorUser)) {
            throw new TransferAmountException(format("Originator user with username '%s' not found", originator));
        }
        if (originatorUser.getAmount().compareTo(amount) < 0) {
            throw new TransferAmountException("Insufficient funds in originator banking account");
        }

        final User receiverUser = userDao.getByUsername(receiver);
        if (isNull(receiverUser)) {
            throw new TransferAmountException(format("Receiver user with username '%s' not found", receiver));
        }

        userDao.updateAmount(originator, originatorUser.getAmount().subtract(amount));
        userDao.updateAmount(receiver, receiverUser.getAmount().add(amount));
    }
}
