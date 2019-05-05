package com.iteleshov.revolut.rest.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Setter
@Getter
@JsonInclude(NON_NULL)
public abstract class AbstractResponse<T> {
    private ResponseHeader header;
    private T body;

    public AbstractResponse(ResponseHeader header) {
        this.header = header;
    }
}
