package ru.fineplaces.context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.fineplaces.service.AuthenticationService;
import ru.fineplaces.service.impl.AuthenticationServiceImpl;

@Module
public class DIModule {

    String BASE_URL = "http://localhost:8080";


    @Provides
    @Singleton
    public AuthenticationService authenticationService() {
        return new AuthenticationServiceImpl();
    }

//    @Provides
//    @Singleton
//    public AuthenticationService retrofit() {
//        CookieHandler cookieHandler = new CookieManager();
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .cookieJar(new JavaNetCookieJar(cookieHandler))
//                .connectTimeout(2, TimeUnit.SECONDS)
//                .writeTimeout(2, TimeUnit.SECONDS)
//                .readTimeout(2, TimeUnit.SECONDS)
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(client)
//                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        return retrofit.create(AuthenticationServiceImpl.class);
//    }

}
