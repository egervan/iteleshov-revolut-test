package com.iteleshov.revolut.rest.service;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iteleshov.revolut.model.User;
import com.iteleshov.revolut.rest.common.ResponseHeader;
import com.iteleshov.revolut.rest.createUser.CreateUserRequest;
import com.iteleshov.revolut.rest.createUser.CreateUserResponse;
import com.iteleshov.revolut.rest.transferMoney.TransferMoneyRequest;
import com.iteleshov.revolut.rest.transferMoney.TransferMoneyResponse;
import com.iteleshov.revolut.rest.transferMoney.TransferMoneyResponseBody;
import com.iteleshov.revolut.service.UserService;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;

import java.math.BigDecimal;

import static com.iteleshov.revolut.rest.common.ResponseStatus.ERROR;
import static com.iteleshov.revolut.rest.common.ResponseStatus.SUCCESS;
import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/user")
@Produces(APPLICATION_JSON)
@AllArgsConstructor
public class UserRestService {
    private final UserService userService;

    @GET
    public User getByUsername(@QueryParam("username") String username) {
        return userService.getByUsername(username)
                .orElseThrow(() -> new NotFoundException(format("User not found by name: %s", username)));
    }

    @POST
    @Path("/transferMoney")
    @Consumes(APPLICATION_JSON)
    public TransferMoneyResponse transferMoney(TransferMoneyRequest request) {
        final TransferMoneyResponse response = new TransferMoneyResponse(new ResponseHeader(request.getHeader().getMessageId(), SUCCESS));

        try {
            final BigDecimal originatorBalance = userService.transferMoney(request.getBody().getOriginator(), request.getBody().getReceiver(), request.getBody().getAmount());
            final TransferMoneyResponseBody body = new TransferMoneyResponseBody(originatorBalance);
            response.setBody(body);
        } catch (Exception e) {
            response.getHeader().setStatus(ERROR);
            response.getHeader().setErrorMessage(e.getMessage());
        }

        return response;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public CreateUserResponse createUser(CreateUserRequest request) {
        final CreateUserResponse response = new CreateUserResponse(new ResponseHeader(request.getHeader().getMessageId(), SUCCESS));

        try {
            userService.create(request.getBody().getUsername(), request.getBody().getBalance());
        } catch (Exception e) {
            response.getHeader().setStatus(ERROR);
            response.getHeader().setErrorMessage(e.getMessage());
        }

        return response;
    }
}
