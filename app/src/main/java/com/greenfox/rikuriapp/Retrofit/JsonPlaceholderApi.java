package com.greenfox.rikuriapp.Retrofit;

import com.greenfox.rikuriapp.Retrofit.registerdtos.ResponseDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceholderApi {

    @POST("/register")
    Call<ResponseDTO> postOnRegister(@Body UserDTO input);

    @GET("/api//kingdom/building/{id}")
    Call<> getKingdom(@Path ("id") Long id);
}
