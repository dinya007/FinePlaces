package ru.fineplaces.context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.fineplaces.state.PlaceMap;

@Module
public class StateModule {

    @Provides
    @Singleton
    public PlaceMap placeList() {
        return new PlaceMap();
    }

}
