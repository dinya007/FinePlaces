package ru.fineplaces.context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.fineplaces.da.AuthenticationDao;
import ru.fineplaces.service.AuthenticationService;
import ru.fineplaces.service.impl.AuthenticationServiceImpl;

@Module
public class ServiceModule {


    @Provides
    @Singleton
    public AuthenticationService authenticationService(AuthenticationDao authenticationDao) {
        return new AuthenticationServiceImpl(authenticationDao);
    }


}
