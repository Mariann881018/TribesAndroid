package com.greenfox.rikuriapp.Retrofit;

public class ResourceDto {

    private ResourceType type;
    private Long amount;

    public ResourceDto() {
    }

    public ResourceDto(ResourceType type, Long amount) {
        this.type = type;
        this.amount = amount;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
