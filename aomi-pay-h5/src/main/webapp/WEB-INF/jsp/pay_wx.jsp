<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta name="viewport" content="width=device-width">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">
    <title>向商家付款</title>
    <link href="https://h5.cloudbest.shop/qr/weui.min2.css" rel="stylesheet">
    <link href="https://h5.cloudbest.shop/qr/inputAmt.css" rel="stylesheet">
    <script src="https://h5.cloudbest.shop/qr/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="https://h5.cloudbest.shop/qr/keyboardtest.js" type="text/javascript"></script>
</head>
<body>
<div class="page">

    <!-- 已完成  -->
    <div id="toast" style="display: none;">
        <div class="weui_mask_transparent"></div>
        <div class="weui_toast">
            <i class="weui_icon_toast"></i>
            <p class="weui_toast_content">已完成</p>
        </div>
    </div>

    <!-- 加载中 -->
    <div id="loadingToast" class="weui_loading_toast" style="display:none;">
        <div class="weui_mask_transparent"></div>
        <div class="weui_toast">
            <div class="weui_loading">
                <div class="weui_loading_leaf weui_loading_leaf_0"></div>
                <div class="weui_loading_leaf weui_loading_leaf_1"></div>
                <div class="weui_loading_leaf weui_loading_leaf_2"></div>
                <div class="weui_loading_leaf weui_loading_leaf_3"></div>
                <div class="weui_loading_leaf weui_loading_leaf_4"></div>
                <div class="weui_loading_leaf weui_loading_leaf_5"></div>
                <div class="weui_loading_leaf weui_loading_leaf_6"></div>
                <div class="weui_loading_leaf weui_loading_leaf_7"></div>
                <div class="weui_loading_leaf weui_loading_leaf_8"></div>
                <div class="weui_loading_leaf weui_loading_leaf_9"></div>
                <div class="weui_loading_leaf weui_loading_leaf_10"></div>
                <div class="weui_loading_leaf weui_loading_leaf_11"></div>
            </div>
            <p class="weui_toast_content">数据加载中</p>
        </div>
    </div>
</div>


<div class="storeicon">
    <img src="https://h5.cloudbest.shop/qr/img_icon.png">
</div>
<div class="storename">
    ${merchantSimpleName}
</div>
<div class="mainarea">
    <div class="iplabel">请输入金额</div>
    <div class="inputbox">
        <div class="labelname">￥</div>
        <input class="inputamount" id="transAmount" type="text" name="" placeholder="0.00" readonly="readonly">
    </div>

    <input type="hidden" id="mercId" value="826263457320001">
    <input type="hidden" id="trmNo" value="">
    <input type="hidden" id="mercIp" value="124.77.195.137">
    <input type="hidden" id="requestNo" value="">

</div>

<div class="weui_dialog_alert" id="dialog" style="display: none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">温馨提示</strong></div>
        <div id="errorMsg" class="weui_dialog_bd">对不起，该次请求交易失败</div>
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_dialog default" id="giveUp">放弃</a>
            <a href="javascript:;" class="weui_btn_dialog primary" id="redo">重试</a>
        </div>
    </div>
</div>

<script type="text/javascript">
    var gps = {};

    (function () {
        var remarkBlack = "falseFlg";
        $("#remark-aris").hide();
        $("#__w_l_h_v_c_z_e_r_o_divid").hide();
        if (remarkBlack == "trueFlg") {
            $("#remark-new").hide();
        }
        var input1 = document.getElementById("transAmount");
        console.log(new KeyBoard(input1));
        new KeyBoard(input1);
        if (input1) {
            new KeyBoard(input1);
        }

        userId = getCookie('userId');

        $("#sub").on('touchend',function(){
            onBridgeReady(userId);
        });

    })();

    function getCookie(name) {
        var prefix = name + "="
        var start = document.cookie.indexOf(prefix)

        if (start == -1) {
            return null;
        }

        var end = document.cookie.indexOf(";", start + prefix.length)
        if (end == -1) {
            end = document.cookie.length;
        }

        var value = document.cookie.substring(start + prefix.length, end)
        return unescape(value);
    }

    /*微信支付*/
    function onBridgeReady(userId) {
        $.ajax({
            type: "POST",
            url: "https://pay.cloudbest.shop/payment/jsPay",
            async: false,
            data: JSON.stringify({
                "fixedQrCode": "12345123451234512345",
                "payType": "1",
                "amount": $("#transAmount").val(),
                "userId": userId
            }),
            dataType: "json",
            contentType: 'application/json;charset=UTF-8',
            success: function (d) {
                console.log(JSON.stringify(d));
                if (d.code == "100000") {
                    var data = JSON.parse(d.data);

                    WeixinJSBridge.invoke(
                        'getBrandWCPayRequest', {
                            "appId": data.appId,
                            "timeStamp": data.timeStamp,
                            "nonceStr": data.nonceStr,
                            "package": data.package,
                            "signType": data.signType,
                            "paySign": data.paySign
                        },
                        function (res) {
                            if (res.err_msg == "get_brand_wcpay_request:ok") {
                                success();
                            } else {
                                //失败
                            }
                        });

                } else {
                    layer.msg(d.message, {icon: 5});
                }
            },
            error: function (e) {
                //var jasonData = $.parseJSON(e.responseText);
                //layer.msg(jasonData.msg, {icon: 5});
            }
        });

    }

    function success() {
        var amount = $("#transAmount").val();
        window.location.href = ("https://qr.cloudbest.shop/success?amount="+amount+"&merchantSimpleName=${merchantSimpleName}");
    }

    function remarkInput() {
        $("#remark-new").hide();
        $("#remark-aris").show();
        keBordHide();
        var alem = document.getElementById('remarkarea1');
        alem.focus();

    };

    function getAliLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                var lat = position.coords.latitude;
                var lng = position.coords.longitude;
                gps["latitude"] = lat;
                gps["longitude"] = lng;

            }, function () {
                alert('定位失败');
            }, {
                // 指示浏览器获取高精度的位置，默认为false
                enableHighAccuracy: true,
                // 指定获取地理位置的超时时间，默认不限时，单位为毫秒
                timeout: 5000,
                // 最长有效期，在重复获取地理位置时，此参数指定多久再次获取位置。
                maximumAge: 1000 * 60 * 5
            });
        } else {
            alert('浏览器不支持定位');
        }
    }

    function keBordView() {
        $("#__w_l_h_v_c_z_e_r_o_divid").show();
    }

    function keBordHide() {
        $("#__w_l_h_v_c_z_e_r_o_divid").hide();
    }
</script>
</body>
</html>