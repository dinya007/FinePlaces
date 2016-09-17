package ru.fineplaces.da;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.fineplaces.domain.RegisterDto;

public interface AuthenticationDao {

    @POST("/authentication/register")
    Call<ResponseBody> register(RegisterDto registerDto);

    @POST("/authentication/login")
    Call<ResponseBody> login(@Query("username") String username, @Query("password") String password, @Body String body);

    @POST("/authentication/logout")
    Call<ResponseBody> logout();

}
