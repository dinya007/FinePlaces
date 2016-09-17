package ru.fineplaces.service;

public interface AuthenticationService {

    boolean register(String name, String email, String password);

    boolean login(String username, String password);

    boolean logout();

}
