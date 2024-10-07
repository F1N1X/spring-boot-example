package com.stevecode.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
