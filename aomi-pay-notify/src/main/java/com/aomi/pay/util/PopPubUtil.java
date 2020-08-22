package com.aomi.pay.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.iot.model.v20180120.PubRequest;
import com.aliyuncs.iot.model.v20180120.PubResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aomi.pay.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.internal.websocket.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author hdq
 * @Description: 播报
 */
@Slf4j
@RefreshScope
@Component
public class PopPubUtil {

    /**
     * 区域id
     */
    private static String REGION_ID;

    /**
     * key
     */
    private static String ACCESS_KEY;

    /**
     * 秘钥
     */
    private static String ACCESS_SECRET;

    /**
     * 产品key
     */
    private static String PRODUCT_KEY;

    /**
     * 传输质量等级
     */
    private static int QOS;

    @Value("${access_key}")
    public void setAccessKey(String accessKey) {
        ACCESS_KEY = accessKey;
    }

    @Value("${region_id}")
    public void setRegionId(String regionId) {
        REGION_ID = regionId;
    }

    @Value("${access_secret}")
    public void setAccessSecret(String accessSecret) {
        ACCESS_SECRET = accessSecret;
    }

    @Value("${product_key}")
    public void setProductKey(String productKey) {
        PRODUCT_KEY = productKey;
    }

    @Value("${qos}")
    public void setQos(int qos) {
        QOS = qos;
    }

    /**
     * @author hdq
     * @date 2020/8/22
     * @description: 播报调用
     **/
    public static void send(String sn, MessageVO messageVO) {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY, ACCESS_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        PubRequest request = new PubRequest();
        request.setQos(QOS);
        request.setProductKey(PRODUCT_KEY);
        request.setTopicFullName("/" + PRODUCT_KEY + "/" + sn + "/user/get");

        request.setMessageContent(Base64.encode(JSON.toJSONString(messageVO)));

        try {
            PubResponse response = client.getAcsResponse(request);
            log.info("pub is success:{}", response.getSuccess());
        } catch (Exception e) {
            log.error("播报异常：", e);
        }

    }

    public static void main(String[] args) throws Exception {
        MessageVO messageVO = new MessageVO();
        messageVO.setMsg("支付宝到账0.01元");
        String regionId = "cn-shanghai";
        String accessKey = "LTAI4G3fFNmVMhts38EouZvp";
        String accessSecret = "P0kP3d9wIaZTqz7p4AdM5dQrfP1hDB";
        final String productKey = "a1Ed6GtHEDz";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        PubRequest request = new PubRequest();
        request.setQos(0);
        request.setProductKey(productKey);
        request.setTopicFullName("/" + productKey + "/" + "JB2020A000002" + "/user/get");
        String msgJsonString = JSON.toJSONString(JSON.toJSONString(messageVO));
        request.setMessageContent(Base64.encode(JSON.toJSONString(messageVO)));
        try {
            PubResponse response = client.getAcsResponse(request);
            log.info("pub is success:{}", response.getSuccess());
        } catch (Exception e) {
            log.error("播报异常：", e);
        }
        //send("JB2020A000002",messageVO);

    }
}
