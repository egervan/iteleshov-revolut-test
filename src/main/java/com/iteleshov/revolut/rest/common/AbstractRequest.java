package com.iteleshov.revolut.rest.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class AbstractRequest<T> {
    private Header header;
    private T body;

    public AbstractRequest(@NotNull(message = "Header must be present") Header header,
                           @NotNull(message = "Body must be present") T body) {
        this.header = header;
        this.body = body;
    }
}