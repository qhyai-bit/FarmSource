package com.briup.product_source.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
@Data
public class ManagerAnimal {
    @JsonProperty("aAnimalId")
    private String aAnimalId;
    @JsonProperty("aWeight")
    private String aWeight;
    @JsonProperty("aGender")
    private String aGender;
    @JsonProperty("aHealthy")
    private String aHealthy;
    @JsonProperty("aStatus")
    private String aStatus;
    @JsonProperty("aInoculate")
    private String aInoculate;
    @JsonProperty("aTime")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date aTime;
    @JsonProperty("aBatchId")
    private String aBatchId;
    @JsonProperty("aHurdlesId")
    private String aHurdlesId;
}