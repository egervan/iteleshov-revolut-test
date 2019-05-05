package com.iteleshov.revolut;

import com.iteleshov.revolut.dao.UserDao;
import com.iteleshov.revolut.dao.impl.UserDaoImpl;
import com.iteleshov.revolut.model.User;
import com.iteleshov.revolut.rest.service.UserRestService;
import com.iteleshov.revolut.service.UserService;
import com.iteleshov.revolut.service.impl.UserServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jdbi.v3.core.Jdbi;

import java.math.BigDecimal;

public class TestTaskApplication extends Application<Configuration> {
    private static final String CREATE_USER_TABLE = "CREATE TABLE user (username VARCHAR PRIMARY KEY, balance NUMERIC)";
    private static final String INSERT_USER = "INSERT INTO user(username, balance) VALUES (:username, :balance)";

    public static void main(String[] args) throws Exception {
        new TestTaskApplication().run("server");
    }

    @Override
    public void run(Configuration configuration, Environment environment) {
        final Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        jdbi.useHandle(handle -> {
                    handle.execute(CREATE_USER_TABLE);
                    handle.createUpdate(INSERT_USER)
                            .bindBean(new User("David", new BigDecimal("500000")))
                            .execute();
                    handle.createUpdate(INSERT_USER)
                            .bindBean(new User("Nic", new BigDecimal("100000")))
                            .execute();
                }
        );

        environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(jdbi).to(Jdbi.class);
                bind(UserDaoImpl.class).to(UserDao.class);
                bind(UserServiceImpl.class).to(UserService.class);
            }
        });

        environment.jersey().register(UserRestService.class);
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
    }
}
