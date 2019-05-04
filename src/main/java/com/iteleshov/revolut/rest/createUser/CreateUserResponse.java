package com.iteleshov.revolut.rest.createUser;

import com.iteleshov.revolut.rest.common.AbstractResponse;
import com.iteleshov.revolut.rest.common.ResponseStatus;

/**
 * @author iteleshov
 * @since 1.0
 */
public class CreateUserResponse extends AbstractResponse {
    public CreateUserResponse(ResponseStatus status, String messageId) {
        super(status, messageId);
    }
}
