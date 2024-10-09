package com.stevecode.customer;

record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
