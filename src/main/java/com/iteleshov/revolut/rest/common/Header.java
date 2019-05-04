package com.iteleshov.revolut.rest.common;

import lombok.Getter;

import static java.util.Objects.requireNonNull;

/**
 * @author iteleshov
 * @since 1.0
 */
@Getter
public class Header {
    private String messageId;

    public Header(String messageId) {
        requireNonNull(messageId, "MessageId must be present");
        this.messageId = messageId;
    }
}