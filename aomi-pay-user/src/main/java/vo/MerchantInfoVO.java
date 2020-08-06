package vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class MerchantInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private MchtBase mchtBase;
    private MchtUser mchtUser;
    private MchtAcct mchtAcct;
    private MchtComp mchtComp;
    private MchtMedia mchtMedia;



    private String instId;
    private String sn;
    private String snModelId;
    private String serviceType;
    private Map<String,String> product;
    private String extras;
    private String channelKind;


}
