<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="zh">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <meta name="referrer" content="never">
    <title>收款</title>
    <style type="text/css">

    </style>
</head>
<body>
<div class="header header1">
    <i class="back"></i>
    <h2>商户收款</h2>
</div>
<div class="main regconpany">
    <div class="mainTop">
        <div class="swiper-container navswiper">
            <div class="swiper-wrapper">
                <div class="swiper-slide active">有限公司</div>
                <div class="swiper-slide">股份公司</div>
                <div class="swiper-slide">合伙企业</div>
                <div class="swiper-slide">外商独资</div>
                <div class="swiper-slide">个人独资</div>
                <div class="swiper-slide">国有独资</div>
            </div>
        </div>
        <div class="swiper-container gallery-thumbs">
            <div class="swiper-wrapper">
                <div class="swiper-slide"></div>
                <div class="swiper-slide"></div>
                <div class="swiper-slide"></div>
                <div class="swiper-slide"></div>
                <div class="swiper-slide"></div>
                <div class="swiper-slide"></div>
            </div>
        </div>
    </div>

    <div class="RegContentBox RegPage">
        <div class="swiper-container" id="regContentBox">
            <div class="swiper-wrapper"></div>
        </div>
    </div>
    <div class="phoneIcon"></div>
</div>
<!-- <div class="regfooter">
    <button><a href="">在线咨询</a></button>
    <button><a href="">立即申请</a></button>
</div> -->
<%--<script src="${ctx}/static/mui/mui.min.js"></script>
<script src="${ctx}/static/js/rem.js"></script>
<script src="${ctx}/static/js/swiper.min.js"></script>
<script type="text/javascript">
    (function(){
        var swiper = null,swiper2 = null,ContentSwiper = null;

        var regProcessHtml = '<div class="regProcess homeblock"><h2 class="icon1Title">申请流程</h2><div class="reg-lc-box"><i class="reg-lc"></i></div></div>';

        var materialHtmlText = {
            titile : '所需材料',
            list :['3-5个公司名称。','法人及股东身份证照片或复印件。','公司经营范围<span>（有需特种许可经营项目，需相关 部门报审盖章，特种行业，许可证办理）。</span>','股东出资额（股份比例）。','住所使用证明。']
        }

        var materialHtml = '<div class="material homeblock"><h2 class="icon1Title">所需材料</h2><ul class="materialList">';
        $.each(materialHtmlText.list, function(i,d) {
            materialHtml += '<li>'+d+'</li>';
        });
        materialHtml += '</ul></div>';


        var CertificatesHtmlText = {
            title : '你所获得',
//				imglist:['img/regcompany/img1.png','img/regcompany/img2.png','img/regcompany/img3.png','img/regcompany/img4.png'],
            textlist:['营业执照正本','营业执照副本','公司章程','公司印章及印鉴留存卡']
        };
        var CertificatesHtml = '<div class="Certificates homeblock"><h2 class="icon1Title">你所获得</h2><ul class="ImgList">';
        $.each(CertificatesHtmlText.textlist, function(i,d) {
            CertificatesHtml += '<li><i></i><span>'+d+'</span></li>';
        });
        CertificatesHtml += '</ul><i class="CertificatesImg"></i></div>';

        var advantageHtmlText = {
            title : '六大优势',
            titletext:['资金安全','专业团队','提升效率','降低成本','保障隐私','安全送达'],
            text:['收费价格透明合理，未服务自由退款','专业资深顾问，售前售后全程一对一服务','缩短70%的财务处理时间','根据客户实际情况制定方案，帮助企业降低注册、财务成本','财务数据加密、客户信息全程保密','材料证件包装精美，安全到手']
        };
        var advantageHtml = '<div class="advantage homeblock"><h2 class="icon1Title">六大优势</h2><ul>';
        $.each(advantageHtmlText.text, function(i,d) {
            advantageHtml += '<li><i></i><p>'+advantageHtmlText.titletext[i]+'</p><span>'+d+'</span></li>';
        });
        advantageHtml += '</ul></div>';
        var sid = '<div class="swiper-slide">'+regProcessHtml + materialHtml + CertificatesHtml + advantageHtml +'</div>';
        for(var i = 1; i < 7;i++){
            $('#regContentBox .swiper-wrapper').append(sid);
        }

         swiper = new Swiper('.navswiper', {
              slidesPerView: 5,
              freeMode: true,
              on:{
                tap: function(event){
                    $('.mainTop .navswiper .swiper-slide').removeClass('active');
                    $(swiper.clickedSlide).addClass('active');
                    var tindex = swiper.clickedIndex;
                    swiper2.slideTo(this.clickedIndex,1000,false);
                    ContentSwiper.slideTo(this.clickedIndex,1000,false);
                },
            },
        });
        swiper2 = new Swiper('.gallery-thumbs', {
                spaceBetween: 50,
              allowTouchMove:false
        });

        ContentSwiper = new Swiper('#regContentBox',{
              allowTouchMove:false
        });
        ContentSwiper.update();
    })()


</script>
</body>
</html>