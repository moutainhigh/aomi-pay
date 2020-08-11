package com.aomi.pay.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author
 * @Date 2020/8/1 17:28
 * @Version 1.0
 */
@Data
public class MchtMedia {

    private String certFront;
    private String certReverse;
    private String handheld;
    private String bankCardPositive;
    private String license;
    private String orgImage;
    private String taxImage;
    private String openAccoLic;
    private String doorHead;
    private String cashier;
    private String shopPanoram;
    private String priLicAgree;
    private String agenCardFront;
    private String agenIdCardBackPic;
    private String agentCardId;
    private String agentProtocol;

    private List<String> industryLicenses;
    private List<String> extraPics;


}
