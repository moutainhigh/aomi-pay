package com.aomi.pay.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
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
