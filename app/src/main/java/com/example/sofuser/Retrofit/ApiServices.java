package com.example.sofuser.Retrofit;


import com.example.sofuser.Model.Root;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {


    @GET("users?")
    Observable <Root> getApiCall(@Query("order") String order,
                                 @Query("sort") String sort,
                                 @Query("site") String site);



}
