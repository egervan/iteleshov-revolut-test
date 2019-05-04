package com.iteleshov.revolut.rest.service;

import com.iteleshov.revolut.model.User;
import com.iteleshov.revolut.rest.createUser.CreateUserRequest;
import com.iteleshov.revolut.rest.createUser.CreateUserResponse;
import com.iteleshov.revolut.rest.transferMoney.TransferMoneyRequest;
import com.iteleshov.revolut.rest.transferMoney.TransferMoneyResponse;
import com.iteleshov.revolut.service.UserService;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;

import static com.iteleshov.revolut.rest.common.ResponseStatus.ERROR;
import static com.iteleshov.revolut.rest.common.ResponseStatus.SUCCESS;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author iteleshov
 * @since 1.0
 */
@Path("/user")
@Produces(APPLICATION_JSON)
@AllArgsConstructor
public class UserRestService {
    private final UserService userService;

    @GET
    public User getByUsername(@QueryParam("username") String username) {
        return userService.getByUsername(username);
    }

    @POST
    @Path("/transferMoney")
    @Consumes(APPLICATION_JSON)
    public TransferMoneyResponse transferMoney(TransferMoneyRequest request) {
        final TransferMoneyResponse response = new TransferMoneyResponse(SUCCESS, request.getHeader().getMessageId());

        try {
            userService.transferMoney(request.getBody().getOriginator(), request.getBody().getReceiver(), request.getBody().getAmount());
        } catch (Exception e) {
            response.setStatus(ERROR);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public CreateUserResponse createUser(CreateUserRequest request) {
        final CreateUserResponse response = new CreateUserResponse(SUCCESS, request.getHeader().getMessageId());

        try {
            userService.create(request.getBody().getUsername(), request.getBody().getAmount());
        } catch (Exception e) {
            response.setStatus(ERROR);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }
}
