package com.aomi.pay.vo;


import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class Data {

    private MchtBase mchtBase;

    private MchtUser mchtUser;

    private String instId;

    private MchtComp mchtComp;

    @JsonProperty("up-appId")
    private String upAppId;

    private MchtAcct mchtAcct;

    @JsonProperty("key-version")
    private String keyVersion;

    private String sn;

    private MchtMedia mchtMedia;

}
