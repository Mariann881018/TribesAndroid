package com.greenfox.rikuriapp.Retrofit;

import com.greenfox.rikuriapp.Retrofit.registerdtos.LoginResponseDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.LoginUserDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.ResponseDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceholderApi {

    @POST("register")
    Call<ResponseDTO> postOnRegister(@Body UserDTO input);

    @POST("login")
    Call<LoginResponseDTO> postOnLogin(@Body LoginUserDTO input);


    @HTTP(method = "POST", path= "api/kingdom/resources", hasBody = true)
    Call<List<ResourceDto>> callResources(@Body KingdomIdDto kingdomId,
                                          @Header("authorization") String key);

    @HTTP(method = "POST", path= "api/kingdom/buildings", hasBody = true)
    Call<List<BuildingDto>> callBuildings(@Body KingdomIdDto kingdomId,
                                          @Header("authorization") String key);

    @PUT("api/kingdom/building/{id}")
    Call<BuildingDto> callUpgradeBuilding(@Body KingdomIdDto kingdomId,
                                          @Header("authorization") String key,
                                          @Path("id") Long buildingId);

    @PUT("api/kingdom/troop/{level}")
    Call<TroopDto> callUpgradeTroop(@Body KingdomIdDto kingdomId,
                                       @Header("authorization") String key,
                                       @Path("level") Integer level);

    @HTTP(method = "POST", path= "api/kingdom/troops", hasBody = true)
    Call<List<TroopDto>> callTroops(@Body KingdomIdDto kingdomId,
                                    @Header("authorization") String key);

    @HTTP(method = "POST", path = "api/kingdom/building", hasBody = true)
    Call<NewBuildingDto> callNewBuilding(@Body KingdomIdDto kingdomId,
                                         @Header("authorization") String key);

    @GET("admin")
    Call<Void> callAdmin();
}
