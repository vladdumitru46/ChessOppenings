package org.example.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {

    private final String name;
    private final String userName;
    private final String email;
    private final String password;
}
