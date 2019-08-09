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
import com.greenfox.rikuriapp.Retrofit.JsonPlaceholderApi;
import com.greenfox.rikuriapp.Retrofit.registerdtos.LoginResponseDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.LoginUserDTO;

import java.io.IOException;
import java.util.Set;

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


        infoPage = (Button) findViewById(R.id.infoPage);
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
                    infoPage(token, 74L, response.body().getUserName());
                }else{
                    int i = response.code();
                    String resp = null;
                    try {
                        resp = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String[] array = resp.split("\"");
                    String message = array[7];
                    message.replace("\"", "" );

                    Toast.makeText(Login.this, Integer.toString(i) + ": " + message, Toast.LENGTH_LONG).show();
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
