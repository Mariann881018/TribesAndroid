package com.greenfox.rikuriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenfox.rikuriapp.Retrofit.JsonPlaceholderApi;
import com.greenfox.rikuriapp.Retrofit.KingdomIdDto;
import com.greenfox.rikuriapp.Retrofit.ResourceDto;
import com.greenfox.rikuriapp.Retrofit.registerdtos.ResponseDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.UserDTO;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view_result);
       final Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://calm-peak-87984.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        KingdomIdDto kingdomIdDto = new KingdomIdDto();
        kingdomIdDto.setKingdomId(2L);

        UserDTO userDTO = new UserDTO("elvis", "password", "kingdom");
        String json = gson.toJson(userDTO);
        RequestBody requestBody = RequestBody.create(MediaType.parse("applicaion/json"),json);

        Call<ResponseDTO> callReg = jsonPlaceholderApi.postOnRegister(userDTO);


     //   Call<List<ResourceDto>> call = jsonPlaceholderApi.callResources(kingdomIdDto);

     /*    call.enqueue(new Callback<List<ResourceDto>>() {
            @Override
            public void onResponse(Call<List<ResourceDto>> call, Response<List<ResourceDto>> response) {
                if (response.isSuccessful()) {
                    textView.setText("code" + response.code());

                    List<ResourceDto> resourceDtos = response.body();
                    for (ResourceDto r : resourceDtos) {
                        String res = "";
                        res += "type:" + r.getType() + "\n";
                        res += "type:" + r.getAmount() + "\n";

                        textView.append(res);
                    }
                }else {
                    textView.setText(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ResourceDto>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    */


     callReg.enqueue(new Callback<ResponseDTO>() {

          @Override
          public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
              if(response.isSuccessful()){
                  int i = response.code();
                  textView.setText(Integer.toString(i));
                  textView.append(response.body().getUsername());
              }else{
                  int i = response.code();
                  textView.setText(Integer.toString(i));
                  response.errorBody()
                  textView.append();
              }

          }

          @Override
          public void onFailure(Call<ResponseDTO> call, Throwable t) {
            textView.setText(t.getMessage());
          }});
    }
}
