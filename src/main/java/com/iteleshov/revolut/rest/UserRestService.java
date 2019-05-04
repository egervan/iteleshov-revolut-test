package com.iteleshov.revolut.rest;

import com.iteleshov.revolut.model.User;
import com.iteleshov.revolut.service.UserService;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static com.iteleshov.revolut.rest.ResponseStatus.ERROR;
import static com.iteleshov.revolut.rest.ResponseStatus.SUCCESS;

/**
 * @author iteleshov
 * @since 1.0
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class UserRestService {
    private final UserService userService;

    @GET
    public User getByUsername(@QueryParam("username") String username) {
        return userService.getByUsername(username);
    }

    @POST
    @Path("/transferMoney")
    @Consumes(MediaType.APPLICATION_JSON)
    public TransferMoneyResponse transferMoney(TransferMoneyRequest request) {
        final TransferMoneyResponse response = new TransferMoneyResponse(SUCCESS, request.getMessageId());

        try {
            userService.transferMoney(request.getOriginator(), request.getReceiver(), request.getAmount());
        } catch (Exception e) {
            response.setStatus(ERROR);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public CreateUserResponse createUser(CreateUserRequest request) {
        final CreateUserResponse response = new CreateUserResponse(SUCCESS, request.getMessageId());

        try {
            userService.create(request.getUsername(), request.getAmount());
        } catch (Exception e) {
            response.setStatus(ERROR);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }
}
