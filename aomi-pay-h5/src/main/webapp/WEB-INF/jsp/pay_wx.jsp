<!--
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
-->
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>聚宝</title>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <script type="text/javascript" src="/static/js/flexible.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/index.css"/>

    <script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.1/jquery.js"></script>
    <script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://gw.alipayobjects.com/as/g/h5-lib/alipayjsapi/3.1.1/alipayjsapi.inc.min.js"></script>
    <script src="https://gw.alipayobjects.com/as/g/h5-lib/alipayjsapi/3.1.1/alipayjsapi.min.js"></script>
    <script src="https://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
</head>
<body>
<div class="home">
    <div class="cont">
        <img class="store" src="/static/images/icon.png"/>
        <div class="store-name">郑州市盛隆热干面</div>
        <div class="secont-tit">
            <span class="tag">门店</span>
            <span class="tit-store-name">郑州市盛隆热干面</span>
        </div>
        <div class="amout-wap">
            <span class="zi">消费金额 ¥ </span>
            <input class="amount" type="number" id="amount"/>
        </div>
        <button id="J_btn" class="btn btn-default">支付</button>
    </div>


</div>

<script>

    var btn = document.querySelector('#J_btn');

    var userId = "";

    $(function () {
        userId = getCookie('userId');
    });

    btn.addEventListener('click', function () {
        console.log(userId);
        onBridgeReady(userId);
    });


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
                "amount": $("#amount").val(),
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
        window.location.href = ("https://qr.cloudbest.shop/success");
    }

</script>
</body>
</html>