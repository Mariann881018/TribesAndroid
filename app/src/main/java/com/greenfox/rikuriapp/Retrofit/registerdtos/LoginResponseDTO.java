package com.greenfox.rikuriapp.Retrofit.registerdtos;

public class LoginResponseDTO {
    private String status;
    private String userName;
    private String password;

    public LoginResponseDTO(){}

    public LoginResponseDTO(String status, String userName, String password) {
        this.status = status;
        this.userName = userName;
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
