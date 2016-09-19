package ru.fineplaces.context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.fineplaces.da.AuthenticationDao;
import ru.fineplaces.da.PlaceDao;
import ru.fineplaces.service.AuthenticationService;
import ru.fineplaces.service.PlaceService;
import ru.fineplaces.service.impl.AuthenticationServiceImpl;
import ru.fineplaces.service.impl.PlaceServiceImpl;

@Module
public class ServiceModule {


    @Provides
    @Singleton
    public AuthenticationService authenticationService(AuthenticationDao authenticationDao) {
        return new AuthenticationServiceImpl(authenticationDao);
    }


    @Provides
    @Singleton
    public PlaceService placeService(PlaceDao placeDao) {
        return new PlaceServiceImpl(placeDao);
    }

}
