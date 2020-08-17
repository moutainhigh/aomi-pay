package com.aomi.pay.vo;

import lombok.Data;

@Data
public class AcctVO {

    private String instId;
    private String mchtNo;//平台商户号
    private String acctNo;//银行卡号
    private String acctBankNo;//账户开行行号
    private String acctZbankCode;//账户开户支行地区号
    private String acctZbankNo;//账户开户支行号
    private String bankCardPositive;//银行卡正面照片imageCode
}
