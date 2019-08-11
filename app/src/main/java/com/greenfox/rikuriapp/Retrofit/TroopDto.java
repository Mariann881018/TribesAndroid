package com.greenfox.rikuriapp.Retrofit;

public class TroopDto {

    private Integer level;
    private Integer hp;
    private Integer attack;
    private Integer defense;

    public TroopDto() {
    }

    public TroopDto(Integer level, Integer hp, Integer attack, Integer defense) {
        this.level = level;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
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

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }
}
