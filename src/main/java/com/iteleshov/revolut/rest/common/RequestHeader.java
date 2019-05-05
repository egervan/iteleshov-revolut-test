package com.iteleshov.revolut.rest.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class RequestHeader {
    private String messageId;

    @JsonCreator
    public RequestHeader(@NotNull(message = "MessageId must be present") @JsonProperty("messageId") String messageId) {
        this.messageId = messageId;
    }
}