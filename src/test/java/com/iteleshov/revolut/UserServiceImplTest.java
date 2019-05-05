package com.iteleshov.revolut;

import com.iteleshov.revolut.dao.UserDao;
import com.iteleshov.revolut.model.User;
import com.iteleshov.revolut.service.impl.UserServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static junit.framework.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserServiceImpl userService;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void getByUserName() {
        final String username = "David";
        final User user = new User(username, new BigDecimal("100"));

        when(userDao.getByUsername(username))
                .thenReturn(Optional.of(user));

        final Optional<User> actual = userService.getByUsername(username);

        verify(userDao).getByUsername(username);
        verifyNoMoreInteractions(userDao);

        assertSame(user, actual.get());
    }

    @Test
    public void create() {
        final String username = "David";
        final BigDecimal balance = new BigDecimal("100");

        when(userDao.create(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        userService.create(username, balance);

        final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userDao).create(userCaptor.capture());
        verifyNoMoreInteractions(userDao);

        assertEquals(username, userCaptor.getValue().getUsername());
        assertEquals(balance, userCaptor.getValue().getBalance());
    }

    @Test
    public void transferMoney() throws ExecutionException, InterruptedException {
        final String originatorUsername = "David";
        final BigDecimal originatorBalance = new BigDecimal("250");
        final String receiverUsername = "Nic";
        final BigDecimal receiverBalance = new BigDecimal("100");

        when(userDao.getByUsername(originatorUsername))
                .thenReturn(Optional.of(new User(originatorUsername, originatorBalance)));
        when(userDao.getByUsername(receiverUsername))
                .thenReturn(Optional.of(new User(receiverUsername, receiverBalance)));

        final BigDecimal originatorAmount = userService.transferMoney(originatorUsername, receiverUsername, new BigDecimal("50"));

        verify(userDao).getByUsername(originatorUsername);
        verify(userDao).getByUsername(receiverUsername);
        verify(userDao).updateAmount(originatorUsername, new BigDecimal("200"));
        verify(userDao).updateAmount(receiverUsername, new BigDecimal("150"));
        verifyNoMoreInteractions(userDao);

        assertEquals(new BigDecimal("200"), originatorAmount);
    }


    @Test
    public void transferMoney_insufficient_funds() throws ExecutionException, InterruptedException {
        final String originatorUsername = "David";
        final BigDecimal originatorBalance = new BigDecimal("49");
        final String receiverUsername = "Nic";
        final BigDecimal receiverBalance = new BigDecimal("100");

        when(userDao.getByUsername(originatorUsername))
                .thenReturn(Optional.of(new User(originatorUsername, originatorBalance)));
        when(userDao.getByUsername(receiverUsername))
                .thenReturn(Optional.of(new User(receiverUsername, receiverBalance)));

        expected.expect(ExecutionException.class);
        expected.expectMessage("Insufficient funds in originator banking account");

        userService.transferMoney(originatorUsername, receiverUsername, new BigDecimal("50"));

        verify(userDao).getByUsername(originatorUsername);
        verify(userDao).getByUsername(receiverUsername);
        verifyNoMoreInteractions(userDao);
    }


    @Test
    public void transferMoney_originator_not_found() throws ExecutionException, InterruptedException {
        final String originatorUsername = "David";
        final String receiverUsername = "Nic";

        when(userDao.getByUsername(receiverUsername))
                .thenReturn(Optional.of(new User(receiverUsername, new BigDecimal("100"))));

        expected.expect(ExecutionException.class);
        expected.expectMessage("Originator user with username 'David' not found");
        userService.transferMoney(originatorUsername, receiverUsername, new BigDecimal("50"));
    }

    @Test
    public void transferMoney_receiver_not_found() throws ExecutionException, InterruptedException {
        final String originatorUsername = "David";
        final String receiverUsername = "Nic";

        when(userDao.getByUsername(originatorUsername))
                .thenReturn(Optional.of(new User(originatorUsername, new BigDecimal("250"))));

        expected.expect(ExecutionException.class);
        expected.expectMessage("Receiver user with username 'Nic' not found");
        userService.transferMoney(originatorUsername, receiverUsername, new BigDecimal("50"));
    }
}
