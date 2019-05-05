package com.iteleshov.revolut.rest.createUser;

import com.iteleshov.revolut.rest.common.AbstractResponse;
import com.iteleshov.revolut.rest.common.ResponseHeader;
import com.iteleshov.revolut.rest.common.ResponseStatus;
import lombok.AllArgsConstructor;

public class CreateUserResponse extends AbstractResponse<CreateUserResponseBody> {
    public CreateUserResponse(ResponseHeader header) {
        super(header);
    }
}
