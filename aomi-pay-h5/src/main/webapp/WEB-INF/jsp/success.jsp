<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>支付成功</title>

    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
</head>
<body>
<div class="home">
	<div class="cont">
		<div class="img">
			<img src="https://h5.cloudbest.shop/qr/pay-success.png">
			<p>支付成功</p>
		</div>
		<div class="money">${amount}元</div>
		<div class="store-name">
			<div class="name">商户名：</div>
			<div class="name2">${merchantSimpleName}</div>
		</div>
	</div>
</div>
</body>
</html>
<style type="text/css">
	*{
		padding: 0;
		margin: 0;
	}
	p,span{
		padding: 0;
		margin: 0;
	}
	.home{
		width: 7.5rem;
		height: auto;
		margin: 0 auto;
	}
	.cont{
		width: 7.5rem;
		height: auto;
		text-align: center;
		margin-top: 1.94rem;
	}
.cont .img{
    width: 100%;
	height: 100%;
	text-align: center;
}
	
	.cont .img img{
		width:2.56rem;
		height: 2.56rem;
	}
	.cont .img p{
		font-size: 0.36rem;
       color: #FF8089;
	   margin-top: -0.2rem;
	   margin-bottom: 0.48rem;
	}
	.money{
		width: 7.5rem;
		height: auto;
		color: #343434 ;
		font-size: 0.72rem;
		margin-bottom: 0.1rem;
	}
		.store-name {
			width: 7.5rem;
			 display: flex;
			 align-items: center;
			 justify-content: center;
			margin-top: 0.1rem;
 		}
	.store-name .name{
		color: #343434;
	
		text-align: center;
		font-size: 0.28rem;
	}
	.store-name .name2{
	
		font-size: 0.28rem;
		text-align: center;
		color: rgba(52,52,52,0.7);
	}
</style>
<script type="text/javascript">
	;(function flexible (window, document) {
	  var docEl = document.documentElement
	  var dpr = window.devicePixelRatio || 1
	  // adjust body font size
	  function setBodyFontSize () {
	    if (document.body) {
	      // document.body.style.fontSize = (12 * dpr) + 'px'
	    }
	    else {
	      document.addEventListener('DOMContentLoaded', setBodyFontSize)
	    }
	  }
	  setBodyFontSize();
	  // set 1rem = viewWidth / 10
	  function setRemUnit () {
	    var clien = docEl.clientWidth > 750 ? 750 : docEl.clientWidth;
	    var rem = clien / 7.5;
	    docEl.style.fontSize = rem + 'px'
	  }
	
	  setRemUnit()
	
	  // reset rem unit on page resize
	  window.addEventListener('resize', setRemUnit)
	  window.addEventListener('pageshow', function (e) {
	    if (e.persisted) {
	      setRemUnit()
	    }
	  })
	  // detect 0.5px supports
	  if (dpr >= 2) {
	    var fakeBody = document.createElement('body')
	    var testElement = document.createElement('div')
	    testElement.style.border = '.5px solid transparent'
	    fakeBody.appendChild(testElement)
	    docEl.appendChild(fakeBody)
	    if (testElement.offsetHeight === 1) {
	      docEl.classList.add('hairlines')
	    }
	    docEl.removeChild(fakeBody)
	  }
	}(window, document))
</script>