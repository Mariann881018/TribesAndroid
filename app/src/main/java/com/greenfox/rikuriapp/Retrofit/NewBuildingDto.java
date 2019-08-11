package com.greenfox.rikuriapp.Retrofit;

public class NewBuildingDto {

    private BuildingType type;
    private Long kingdomId;

    public NewBuildingDto() {
    }

    public NewBuildingDto(BuildingType type) {
        this.type = type;
    }

    public NewBuildingDto(Long kingdomId) {
        this.kingdomId = kingdomId;
    }

    public NewBuildingDto(BuildingType type, Long kingdomId) {
        this.type = type;
        this.kingdomId = kingdomId;
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public Long getKingdomId() {
        return kingdomId;
    }

    public void setKingdomId(Long kingdomId) {
        this.kingdomId = kingdomId;
    }
}
