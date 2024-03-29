package com.greenfox.rikuriapp.Retrofit;

import com.google.gson.JsonObject;
import com.greenfox.rikuriapp.Retrofit.registerdtos.LoginResponseDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.LoginUserDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.ResponseDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.UserDTO;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceholderApi {

    @POST("register")
    Call<ResponseDTO> postOnRegister(@Body UserDTO input);

    @POST("login")
    Call<LoginResponseDTO> postOnLogin(@Body LoginUserDTO input);


    @HTTP(method = "POST", path= "api/kingdom/resources", hasBody = true)
    Call<List<ResourceDto>> callResources(@Body KingdomIdDto kingdomId,
                                           @Header("authorization") String key);

    @GET("admin")
    Call<Void> callAdmin();
}
