package com.iteleshov.revolut.rest;

import com.iteleshov.revolut.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author iteleshov
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private BigDecimal cash;

    public UserDto(User user) {
        this(null, user.getUsername(), user.getAmount());
    }
}
