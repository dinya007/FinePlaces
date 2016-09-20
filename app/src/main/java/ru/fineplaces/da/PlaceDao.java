package ru.fineplaces.da;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.fineplaces.domain.PlaceDto;

public interface PlaceDao {

    @GET("/secure/places/all-for-owner")
    Call<List<PlaceDto>> getAll();

    @POST("/secure/places/save")
    Call<PlaceDto> save(@Body PlaceDto placeDto);

}
