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
import com.greenfox.rikuriapp.Retrofit.registerdtos.ResponseDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.UserDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    protected Button login;
    private EditText  nameInput;
    private EditText passwordInput;
    private EditText kingdomInput;
    UserDTO userDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new AppConstants().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        nameInput = (EditText) findViewById(R.id.registerNameEditTxt);
        passwordInput = (EditText) findViewById(R.id.registerPasswordEditTxt);
        kingdomInput = (EditText) findViewById(R.id.txtKingdom);
        login = (Button) findViewById(R.id.btnReg);

        final JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             userDTO = new UserDTO(nameInput.getText().toString(), passwordInput.getText().toString(),
                     kingdomInput.getText().toString());
             register(jsonPlaceholderApi, userDTO );
            }
        });
    }

    public void login() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void register(JsonPlaceholderApi jsonPlaceholderApi, UserDTO userDTO){
        Call<ResponseDTO> callReg = jsonPlaceholderApi.postOnRegister(userDTO);
        callReg.enqueue(new Callback<ResponseDTO>() {

            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                if(response.isSuccessful()){
                    int i = response.code();
                    Toast.makeText(Register.this, "Your registration was successful!", Toast.LENGTH_LONG).show();
                    login();
                }else{
                    int i = response.code();
                    String error = "Status: " + i;
                    Toast.makeText(Register.this, error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
            }});
    }
}
