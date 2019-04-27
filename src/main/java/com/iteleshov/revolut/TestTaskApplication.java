package com.iteleshov.revolut;

import com.iteleshov.revolut.dao.UserDao;
import com.iteleshov.revolut.dao.impl.UserDaoImpl;
import com.iteleshov.revolut.model.User;
import com.iteleshov.revolut.rest.UserRestService;
import com.iteleshov.revolut.service.UserService;
import com.iteleshov.revolut.service.impl.UserServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.math.BigDecimal;

/**
 * @author iteleshov
 * @since 1.0
 */
public class TestTaskApplication extends Application<TestTaskConfiguration> {
    public static void main(String[] args) throws Exception {
        new TestTaskApplication().run("server", "src/main/resources/properties.yml");
    }

    @Override
    public void run(TestTaskConfiguration configuration, Environment environment) throws Exception {
        final Jdbi jdbi = Jdbi.create("jdbc:h2:mem:");
        final Handle handle = jdbi.open();
        final UserDao userDao = new UserDaoImpl(jdbi, handle);
        final UserService userService = new UserServiceImpl(userDao);

        handle.execute("CREATE TABLE user (username VARCHAR PRIMARY KEY, amount NUMERIC)");
        handle.createUpdate("INSERT INTO user(username, amount) VALUES (:username, :amount)")
                    .bindBean(new User("David", new BigDecimal("500000")))
                    .execute();

        handle.createUpdate("INSERT INTO user(username, amount) VALUES (:username, :amount)")
                .bindBean(new User("Nic", new BigDecimal("100000")))
                .execute();

        final UserRestService userRestService = new UserRestService(userService);
        environment.jersey().register(userRestService);
    }
}
