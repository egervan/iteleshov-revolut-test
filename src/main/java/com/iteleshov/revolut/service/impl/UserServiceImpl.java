package com.iteleshov.revolut.service.impl;

import com.iteleshov.revolut.TransferAmountException;
import com.iteleshov.revolut.dao.UserDao;
import com.iteleshov.revolut.model.User;
import com.iteleshov.revolut.service.UserService;
import lombok.AllArgsConstructor;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.jdbi.v3.core.transaction.TransactionIsolationLevel.SERIALIZABLE;

@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    @Transaction(readOnly = true)
    public User getByUsername(String username) {
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
        return supplyAsync(() -> userDao.getByUsername(originator))
                .thenCombine(supplyAsync(() -> userDao.getByUsername(receiver)),
                        (originatorUser, receiverUser) -> {
                            if (isNull(originatorUser)) {
                                throw new TransferAmountException(format("Originator user with username '%s' not found", originator));
                            }
                            if (originatorUser.getBalance().compareTo(amount) < 0) {
                                throw new TransferAmountException("Insufficient funds in originator banking account");
                            }

                            if (isNull(receiverUser)) {
                                throw new TransferAmountException(format("Receiver user with username '%s' not found", receiver));
                            }

                            final BigDecimal originatorBalance = originatorUser.getBalance().subtract(amount);
                            userDao.updateAmount(originator, originatorBalance);
                            userDao.updateAmount(receiver, receiverUser.getBalance().add(amount));

                            return originatorBalance;
                        }).get();
    }
}
