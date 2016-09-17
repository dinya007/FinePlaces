package ru.fineplaces.service.impl;

import java.io.IOException;

import retrofit2.Response;
import ru.fineplaces.da.AuthenticationDao;
import ru.fineplaces.domain.RegisterDto;
import ru.fineplaces.service.AuthenticationService;


public class AuthenticationServiceImpl implements AuthenticationService {

    AuthenticationDao authenticationDao;

    public AuthenticationServiceImpl(AuthenticationDao authenticationDao) {
        this.authenticationDao = authenticationDao;
    }

    @Override
    public boolean register(String name, String email, String password) {
        try {
            return isReturnCode200(authenticationDao.register(new RegisterDto(name, email, password)).execute());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        try {
            return isReturnCode200(authenticationDao.login(username, password, "").execute());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean logout() {
        try {
            return isReturnCode200(authenticationDao.logout().execute());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isReturnCode200(Response<?> response) {
        return response.code() == 200;
    }
}
