package com.greenfox.rikuriapp.Retrofit.registerdtos;

public class ResponseDTO {
  private Long id;
  private String username;
  private Long kingdomID;

  public ResponseDTO() {
  }

  public ResponseDTO(Long id, String username, Long kingdomID) {
    this.id = id;
    this.username = username;
    this.kingdomID = kingdomID;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Long getKingdomID() {
    return kingdomID;
  }

  public void setKingdomID(Long kingdomID) {
    this.kingdomID = kingdomID;
  }
}
