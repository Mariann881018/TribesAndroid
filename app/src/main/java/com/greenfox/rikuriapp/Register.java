package com.greenfox.rikuriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greenfox.rikuriapp.Retrofit.JsonPlaceholderApi;
import com.greenfox.rikuriapp.Retrofit.KingdomIdDto;
import com.greenfox.rikuriapp.Retrofit.registerdtos.ResponseDTO;
import com.greenfox.rikuriapp.Retrofit.registerdtos.UserDTO;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
=======
import android.widget.Toast;
>>>>>>> development

public class Register extends AppCompatActivity {

    protected Button login;
    EditText  nameInput;
    UserDTO userDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://calm-peak-87984.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        nameInput = (EditText) findViewById(R.id.txtName);

        login = (Button) findViewById(R.id.btnReg);

        final JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
             userDTO = new UserDTO(nameInput.toString(), "pass", "kingdom");
             register(jsonPlaceholderApi, userDTO );
=======
                Toast.makeText(Register.this, "Your registration was successful!", Toast.LENGTH_LONG).show();
                login();
>>>>>>> development
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
                    login();
                }else{
                    int i = response.code();

                }
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
            }});
    }
}