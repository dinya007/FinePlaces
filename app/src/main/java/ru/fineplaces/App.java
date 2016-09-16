package ru.fineplaces;

import android.app.Application;

import ru.fineplaces.context.ApplicationComponent;
import ru.fineplaces.context.DaggerApplicationComponent;

public class App extends Application {

    private static ApplicationComponent applicationComponent;

    public static ApplicationComponent getComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        build();
    }

    private void build() {
        applicationComponent = DaggerApplicationComponent.builder().build();
    }
}