package com.greenfox.rikuriapp.Retrofit;

public class BuildingDto {

    private BuildingType type;
    private Integer level;
    private Integer hp;

    public BuildingDto() {
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }
}
