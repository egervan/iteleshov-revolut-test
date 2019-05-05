package com.iteleshov.revolut.service.impl;

import com.iteleshov.revolut.exceptions.TransferAmountException;
import com.iteleshov.revolut.dao.UserDao;
import com.iteleshov.revolut.model.User;
import com.iteleshov.revolut.service.UserService;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import javax.inject.Inject;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.jdbi.v3.core.transaction.TransactionIsolationLevel.SERIALIZABLE;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Inject
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transaction(readOnly = true)
    public Optional<User> getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    @Transaction(SERIALIZABLE)
    public User create(String username, BigDecimal amount) {
        return userDao.create(new User(username, amount));
    }

    @Transaction(SERIALIZABLE)
    public BigDecimal transferMoney(@NotNull(message = "Originator must be present") String originator,
                                    @NotNull(message = "Receiver must be present") String receiver,
                                    @NotNull(message = "Amount can't be 'null'")
                                    @DecimalMin(value = "0", message = "Amount must be positive") BigDecimal amount) throws ExecutionException, InterruptedException {
        return supplyAsync(() -> userDao.getByUsername(originator).orElseThrow(() -> new TransferAmountException(format("Originator user with username '%s' not found", originator))))
                .thenCombine(supplyAsync(() -> userDao.getByUsername(receiver)
                                .orElseThrow(() -> new TransferAmountException(format("Receiver user with username '%s' not found", receiver)))),
                        (originatorUser, receiverUser) -> {
                            if (originatorUser.getBalance().compareTo(amount) < 0) {
                                throw new TransferAmountException("Insufficient funds in originator banking account");
                            }

                            final BigDecimal originatorBalance = originatorUser.getBalance().subtract(amount);
                            userDao.updateAmount(originator, originatorBalance);
                            userDao.updateAmount(receiver, receiverUser.getBalance().add(amount));

                            return originatorBalance;
                        }).get();
    }
}
