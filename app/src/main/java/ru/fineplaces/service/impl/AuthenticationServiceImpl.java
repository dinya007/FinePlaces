package ru.fineplaces.service.impl;

import java.io.IOException;

import okhttp3.ResponseBody;
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
            Response<ResponseBody> execute = authenticationDao.logout().execute();
            boolean returnCode302 = isReturnCode302(execute);
            System.out.println(returnCode302);
            return returnCode302;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isReturnCode302(Response<?> response) {
        return response.code() == 302;
    }

    private boolean isReturnCode200(Response<?> response) {
        return response.code() == 200;
    }
}
