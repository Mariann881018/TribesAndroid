package com.greenfox.rikuriapp.Retrofit;

import com.greenfox.rikuriapp.Retrofit.registerdtos.ResponseDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceholderApi {

    @POST("/register")
    Call<ResponseDTO> postOnRegister(@Body UserDTO input);
}
