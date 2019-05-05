package com.iteleshov.revolut.rest.common;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class Header {
    private String messageId;

    public Header(@NotNull(message = "MessageId must be present") String messageId) {
        this.messageId = messageId;
    }
}