package com.iteleshov.revolut.rest.common;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.iteleshov.revolut.rest.createUser.CreateUserRequest;
import com.iteleshov.revolut.rest.transferMoney.TransferMoneyRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NONE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TransferMoneyRequest.class),
        @JsonSubTypes.Type(value = CreateUserRequest.class)})
public abstract class AbstractRequest<T> {
    private RequestHeader header;
    private T body;

    public AbstractRequest(@NotNull(message = "RequestHeader must be present") RequestHeader header,
                           @NotNull(message = "Body must be present") T body) {
        this.header = header;
        this.body = body;
    }
}