package com.iteleshov.revolut.rest.createUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestBody {
    private String username;
    private BigDecimal balance;
}