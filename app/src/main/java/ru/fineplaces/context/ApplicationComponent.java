package ru.fineplaces.context;

import javax.inject.Singleton;

import dagger.Component;
import ru.fineplaces.activities.LoginActivity;

@Component(modules = {AppModule.class, DIModule.class})
@Singleton
public interface ApplicationComponent {
    void inject(LoginActivity activity);

}