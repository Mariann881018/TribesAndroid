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
import com.greenfox.rikuriapp.Retrofit.registerdtos.LoginUserDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.ResponseDTO;

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
                .baseUrl("https://calm-peak-87984.herokuapp.com")
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
        Call<ResponseDTO> callLog = jsonPlaceholderApi.postOnLogin(userDTO);
        callLog.enqueue(new Callback<ResponseDTO>() {

            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                if(response.isSuccessful()){
                    int i = response.code();
                    Toast.makeText(Login.this, "You're signed in!", Toast.LENGTH_LONG).show();
                    infoPage();
                }else{
                    int i = response.code();
                    Toast.makeText(Login.this, Integer.toString(i), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }});
    }

    public void infoPage() {
        Intent intent = new Intent(this, InfoPage.class);
        startActivity(intent);
    }
}
