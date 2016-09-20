package ru.fineplaces.context;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.internal.JavaNetCookieJar;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.fineplaces.da.AuthenticationDao;
import ru.fineplaces.da.PlaceDao;

@Module
public class DaModule {

    //    String BASE_URL = "http://192.168.0.102:8080";
    String BASE_URL = "http://10.38.134.107:8080";
//    String BASE_URL = "http://10.5.33.182:8080";

    @Provides
    @Singleton
    public Retrofit retrofit() {
        CookieHandler cookieHandler = new CookieManager();

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    @Provides
    @Singleton
    public AuthenticationDao authenticationDao(Retrofit retrofit) {
        return retrofit.create(AuthenticationDao.class);
    }

    @Provides
    @Singleton
    public PlaceDao placeDao(Retrofit retrofit) {
        return retrofit.create(PlaceDao.class);
    }

}
