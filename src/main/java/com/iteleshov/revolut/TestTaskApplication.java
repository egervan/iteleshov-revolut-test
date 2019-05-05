package com.iteleshov.revolut;

import com.iteleshov.revolut.dao.UserDao;
import com.iteleshov.revolut.dao.impl.UserDaoImpl;
import com.iteleshov.revolut.model.User;
import com.iteleshov.revolut.rest.service.UserRestService;
import com.iteleshov.revolut.service.UserService;
import com.iteleshov.revolut.service.impl.UserServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.math.BigDecimal;

public class TestTaskApplication extends Application<TestTaskConfiguration> {
    public static void main(String[] args) throws Exception {
        new TestTaskApplication().run("server", "src/main/resources/properties.yml");
    }

    @Override
    public void run(TestTaskConfiguration configuration, Environment environment) throws Exception {
        final Jdbi jdbi = Jdbi.create("jdbc:h2:mem:");
        final Handle handle = jdbi.open();

        handle.execute("CREATE TABLE user (username VARCHAR PRIMARY KEY, balance NUMERIC)");
        handle.createUpdate("INSERT INTO user(username, balance) VALUES (:username, :balance)")
                    .bindBean(new User("David", new BigDecimal("500000")))
                    .execute();

        handle.createUpdate("INSERT INTO user(username, balance) VALUES (:username, :balance)")
                .bindBean(new User("Nic", new BigDecimal("100000")))
                .execute();

        environment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(handle).to(Handle.class);
                bind(UserDaoImpl.class).to(UserDao.class);
                bind(UserServiceImpl.class).to(UserService.class);
            }
        });
        environment.jersey().register(UserRestService.class);
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
    }
}
