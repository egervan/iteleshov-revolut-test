package com.iteleshov.revolut.rest.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * @author iteleshov
 * @since 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(NON_NULL)
public abstract class AbstractResponse {
    private ResponseStatus status;
    private String messageId;
    private String errorMessage;

    public AbstractResponse(ResponseStatus status, String messageId) {
        this.status = status;
        this.messageId = messageId;
    }
}
