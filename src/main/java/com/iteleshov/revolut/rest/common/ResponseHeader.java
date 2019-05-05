package com.iteleshov.revolut.rest.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author iteleshov
 * @since 1.0
 */
@Getter
@Setter
@JsonInclude(NON_NULL)
public class ResponseHeader {
    private String messageId;
    private ResponseStatus status;
    private String errorMessage;

    public ResponseHeader(String messageId, ResponseStatus status) {
        this.messageId = messageId;
        this.status = status;
    }
}
