package com.iteleshov.revolut.rest.createUser;

import com.iteleshov.revolut.rest.common.AbstractResponse;
import com.iteleshov.revolut.rest.common.ResponseStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUserResponse extends AbstractResponse {
    public CreateUserResponse(ResponseStatus status, String messageId) {
        super(status, messageId);
    }
}
