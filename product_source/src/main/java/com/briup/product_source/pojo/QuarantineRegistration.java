package com.briup.product_source.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QuarantineRegistration {
    @JsonProperty("grId")
    private Integer grId;
    @JsonProperty("grTime")
    private String grTime;
    @JsonProperty("grImg")
    private String grImg;
    @JsonProperty("grMechanism")
    private String grMechanism;
    @JsonProperty("grBatchId")
    private String grBatchId;
    @JsonProperty("bQualified")
    private String bQualified;
}