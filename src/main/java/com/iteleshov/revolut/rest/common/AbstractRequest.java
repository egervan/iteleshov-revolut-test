package com.iteleshov.revolut.rest.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

/**
 * @author iteleshov
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class AbstractRequest<T> {
    private Header header;
    private T body;

    public AbstractRequest(Header header, T body) {
        requireNonNull(header, "Header must be present");
        requireNonNull(body, "Body must be present");
        this.header = header;
        this.body = body;
    }
}