package ru.fineplaces.service.impl;

import ru.fineplaces.service.AuthenticationService;


public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public String register(String name, String email, String password) {
        return "It works!";
    }
}
