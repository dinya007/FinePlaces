package ru.fineplaces.context;

import javax.inject.Singleton;

import dagger.Component;
import ru.fineplaces.activities.DetailProfileActivity;
import ru.fineplaces.activities.LoginActivity;
import ru.fineplaces.activities.PlaceListActivity;

@Component(modules = {AppModule.class, ServiceModule.class, DaModule.class, StateModule.class})
@Singleton
public interface ApplicationComponent {
    void inject(LoginActivity activity);
    void inject(PlaceListActivity activity);
    void inject(DetailProfileActivity activity);

}