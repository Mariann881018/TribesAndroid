package com.greenfox.rikuriapp.Retrofit.registerdtos;


public class UserDTO {
  private String username;
  private String password;
  private String kingdom;

  public UserDTO() {
  }


  public UserDTO(String username) {
    this.username = username;
  }

  public UserDTO(String username, String password) {
    this.password = username;
    this.kingdom = password;
  }

  public UserDTO(String username, String password, String kingdom) {
    this.username = username;
    this.password = password;
    this.kingdom = kingdom;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getKingdom() {
    return kingdom;
  }

  public void setKingdom(String kingdom) {
    this.kingdom = kingdom;
  }

}
