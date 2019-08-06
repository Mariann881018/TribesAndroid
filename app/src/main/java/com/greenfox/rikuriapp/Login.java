package com.greenfox.rikuriapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.greenfox.rikuriapp.Retrofit.JsonPlaceholderApi;
import com.greenfox.rikuriapp.Retrofit.registerdtos.LoginResponseDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.LoginUserDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.ResponseDTO;

import java.util.List;
import java.util.Set;

import okhttp3.internal.http2.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    protected Button infoPage;
    protected EditText loginNameEditText;
    protected EditText loginPasswordEditText;
    protected LoginUserDTO userDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new AppConstants().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        loginNameEditText = (EditText) findViewById(R.id.loginNameEditTxt);
        loginPasswordEditText = (EditText) findViewById(R.id.loginPasswordEditTxt);
        final JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);


        infoPage = (Button) findViewById(R.id.btnLogin2);
        infoPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDTO = new LoginUserDTO(loginNameEditText.getText().toString(), loginPasswordEditText.getText().toString());
                login(jsonPlaceholderApi, userDTO);
            }
        });
    }

    public void login(JsonPlaceholderApi jsonPlaceholderApi, LoginUserDTO userDTO){
        Call<LoginResponseDTO> callLog = jsonPlaceholderApi.postOnLogin(userDTO);
        callLog.enqueue(new Callback<LoginResponseDTO>() {

            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                if(response.isSuccessful()){
                    int i = response.code();
                    Toast.makeText(Login.this, "You're signed in!", Toast.LENGTH_LONG).show();
                    Set<String> headers =  response.headers().toMultimap().keySet();
                    String token = response.headers().get("authorization");
                    String userName = response.body().getUserName();
                    infoPage(token, 11L, response.body().getUserName());
                }else{
                    int i = response.code();
                    Toast.makeText(Login.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }});
    }

    public void infoPage(String extraIntent, Long kingdomId, String userName ) {
        Intent intent = new Intent(this, InfoPage.class);
        intent.putExtra("token", extraIntent);
        intent.putExtra("id", kingdomId);
        intent.putExtra("username", userName);
        startActivity(intent);
    }
}
