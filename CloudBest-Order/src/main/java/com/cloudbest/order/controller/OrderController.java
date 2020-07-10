package com.cloudbest.order.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.TokenUtil;
import com.cloudbest.order.entity.MainEntity;
import com.cloudbest.order.entity.SecondarilyEntity;
import com.cloudbest.order.feign.PayClient;
import com.cloudbest.order.mapper.MainMapper;
import com.cloudbest.order.mapper.SecondarilyMapper;
import com.cloudbest.order.service.OrderService;
import com.cloudbest.order.vo.AlipayBean;
import com.cloudbest.order.vo.OrderConfirmVO;
import com.cloudbest.order.vo.OrderSubmitResponseVO;
import com.cloudbest.order.vo.OrderSubmitVO;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
//@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private SecondarilyMapper secondarilyMapper;
    @Autowired
    private MainMapper mainMapper;
    @Autowired
    private PayClient payClient;
    private static Logger log = LoggerFactory.getLogger(OrderController.class);
    /**
     * 确认订单
     * 购物车页面传递的数据结构如下：
     * {101: 3, 102: 1}
     * {skuId: count}
     * @param
     * @return
     */
    //确认订单页
    @GetMapping("app/order/confirm/{token}")  //ok
    public Result confirm(@RequestBody Map<Integer,Integer> cartMap,
                          @PathVariable("token") String token){
        //接受购物车对象购物车数据
        OrderConfirmVO orderConfirmVO = null;   //cartMap
        try {
            orderConfirmVO = this.orderService.confirm(cartMap,token);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,orderConfirmVO);
    }

    /**
     * 提交订单
     * 购物车页面传递的数据结构如下：
     * {101: 3, 102: 1}
     * {skuId: count}
     * @param
     * @return
     */
    @PostMapping("app/order/submit/{token}")   //ok
    public Result submit(@RequestBody OrderSubmitVO orderSubmitVO,
                         @PathVariable("token") String token){
        log.info("===========================提交订单============================");
        try {
            TokenUtil.getUserId(token);
        } catch (Exception e) {
            throw  new  RuntimeException("请先登录");//暂定异常
        }
        String string = null;
        try {
            OrderSubmitResponseVO orderSubmitResponseVO = this.orderService.submit(orderSubmitVO);
            MainEntity mainEntity = orderSubmitResponseVO.getMainEntity();
            LocalDateTime orderTime = mainEntity.getOrderTime();
            //定时关单
            this.NettyTask(mainEntity);
            //选择支付方式
            string = this.payType(mainEntity);
        } catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,string);
    }


    @PostMapping("app/order/create/order")
    public Result crateOrder(@RequestBody OrderSubmitVO orderSubmitVO){
        try {
            OrderSubmitResponseVO orderSubmitResponseVO = this.orderService.crateOrder(orderSubmitVO);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }


    //判断支付方式方式，确定支付方式
    @PostMapping("order/select/payType")
    public String payType(@RequestBody MainEntity mainEntity){
        log.info("支付入参："+JSON.toJSONString(mainEntity));
        Integer payType = mainEntity.getPayType();
        String string = null;
        switch (payType){
            case 1:
                //调用支付接口 支付订单
                BigDecimal payAmount = mainEntity.getPayAmount();
                AlipayBean alipayBean = new AlipayBean();
                alipayBean.setSubject("云上优选 订单号："+mainEntity.getMainOrderId());//   商品的标题/交易标题/订单标题/订单关键字等。
                alipayBean.setTotalAmount(payAmount.toString());//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
                alipayBean.setOutTradeNo(mainEntity.getMainOrderId());//商户网站唯一订单号
                alipayBean.setBody("云上优选");//商品名称   暂定
                log.info("支付入参："+JSON.toJSONString(mainEntity));
                Result zfb = payClient.zfb(alipayBean);
                JSONObject object = JSONObject.fromObject(zfb);
                string = object.getString("data");
                break;
            case 2:
                //调用微信支付接口 支付订单
                break;
            case 3:
                //调用银联支付接口 支付订单
                break;
            case 4:
                //全积分支付，不调取支付接口
                break;
        }
        return string;
    }

    //判断物流方式，确定物流信息
    @PostMapping("order/select/deliveryType")
    public String dedeliveryType(@RequestParam String mainOrderId){
        log.info("物流入参："+JSON.toJSONString(mainOrderId));
        SecondarilyEntity secondarilyEntity = this.secondarilyMapper.selectOne(new LambdaQueryWrapper<SecondarilyEntity>().eq(SecondarilyEntity::getMainOrderId, mainOrderId));
        Integer deliveryCompany = secondarilyEntity.getDeliveryCompany();
        String string = null;
        switch (deliveryCompany){
                //调用顺丰速运
                //获取物流信息
                //TODO
            case 1:
                break;
            case 2:
                //调用圆通快递
                break;
            case 3:
                //调用申通快递
                break;
            case 4:
                //邮政快递
                break;
            case 5:
                //中通快递
                break;
        }
        return string;
    }
    //定时任务
    public void NettyTask(MainEntity mainEntity) {
        // 创建延迟任务实例
        HashedWheelTimer timer = new HashedWheelTimer(900  , // 时间间隔
                TimeUnit.SECONDS); // 时间轮中的槽数
        // 创建一个任务
        TimerTask task = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                MainEntity entity = mainMapper.selectOne(new LambdaQueryWrapper<MainEntity>().eq(MainEntity::getMainOrderId, mainEntity.getMainOrderId()));
                System.out.println("执行任务" + " ，执行时间：" + LocalDateTime.now());
                log.info("执行任务" + " ，执行时间：" + LocalDateTime.now()+",订单号："+entity.getMainOrderId());
                if (entity.getPayStatus().equals(1)){
                    String mainOrderId = mainEntity.getMainOrderId();
                    mainMapper.upd(mainOrderId,5);
                    //解锁库存
                    //TODO
                }
            }
        };
        // 将任务添加到延迟队列中
        timer.newTimeout(task, 0, TimeUnit.SECONDS);
    }


    //超时订单再次购买重新调取提交订单接口
    //关闭订单如果未超过15分钟，直接调取支付接口
    //超过15分钟关闭库存，重新支付调取订单提交接口
    //关闭订单  回退库存 修改订单状态为 已取消


    //继续支付  未过支付时间再做支付
    @PostMapping("app/order/pay/again/{token}")
    public Result payAgain(@RequestBody MainEntity mainEntity,
                           @PathVariable("token") String token){
        try {
            TokenUtil.getUserId(token);
        } catch (Exception e) {
            throw  new  RuntimeException("请先登录");//暂定异常
        }
        String form = null;
        try {
            form = this.payType(mainEntity);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(),businessException.getDesc(),false);//暂定异常
        }
        return new Result(CommonErrorCode.SUCCESS,form);

    }



//public static class TimedTask {
//	public static void main(String[] args) {
//		Runnable runnable = new Runnable() {
//			public void run() {
//				// 把run方法里的内容换成你要执行的内容
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				System.out.println("当前的系统时间为：" + LocalDateTime.now());
//			}
//		};
//		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//		//public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);
//		//command--执行的任务,initialDelay--延迟开始,period--间隔时间,unit--时间单位
//		service.scheduleAtFixedRate(runnable, 0, 15, TimeUnit.SECONDS);
//        System.out.println("当前的系统时间为：" + LocalDateTime.now());
//	}
//}

    @PostMapping("app/order/create/alipayKey")
    public Result alipayKey(){
        String form = "";
        try {
            form = this.orderService.crateAliPay();
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,form);
    }
}
//



//    @PostMapping("order/pay/success")
//    public Result paySuccess(AlipayBean alipayBean,Integer status){
//        System.out.println("=======================支付成功==========================");
//        //写入支付成功时间
//
//        alipayBean.getOutTradeNo();
//        //this.mainMapper.update()
//
//        //订单状态的修改
//        //扣除购物券
//        //判断是否需要扣除相应的用户购物券
//        //添加商品销量
//        try {
//            orderService.paySuccess(alipayBean,status);
//        }catch (BusinessException businessException){
//            return new Result(businessException.getCode(),businessException.getDesc(),false);
//        }
//        return new Result(CommonErrorCode.SUCCESS);
//
//    }

//    //远程调用接口测试
//    @GetMapping("order/test")
//    public Result test(){         //ok
//        Result customerAddrs = userClient.queryAddr(47043787L);
//        String jsonString = JSON.toJSONString(customerAddrs.getData());
//        List<CustomerAddr> customerAddrs1 = JSON.parseArray(jsonString, CustomerAddr.class);
//        return new Result(CommonErrorCode.SUCCESS,customerAddrs1);
//    }
//
//    @GetMapping("order/test/1")   //ok
//    public Result test1(){
//        Result result = itemClient.selecImgBySkuId(4);
//        String s = JSON.toJSONString(result.getData());
//        CItemsImg cItemsImg = JSON.parseObject(s, CItemsImg.class);
//        return new Result(CommonErrorCode.SUCCESS,cItemsImg);
//    }
//
//    @GetMapping("order/test/2")
//    public Result test3(){         //ok
//        Result result = userClient.selectUserById(63312302L);
//        String s = JSON.toJSONString(result.getData());
//        CustomerInf customerInf = JSON.parseObject(s, CustomerInf.class);
//        return new Result(CommonErrorCode.SUCCESS,customerInf);
//    }
//
//
//    @GetMapping("order/test/3")    //ok
//    public Result test4(){   //查询库存信息和商品信息
//        Result result = itemClient.selecStockBySkuId(4);
//
//        Map<String,Object> resultData = (Map<String, Object>) result.getData();
//
//        Map<String,Object> skuMap = (Map<String, Object>) resultData.get("SKU");
//        String sku= JSON.toJSONString(skuMap);
//        CStock stock = JSON.parseObject(sku, CStock.class);
//        BigDecimal salePrice = stock.getSalePrice();
//        System.out.println(salePrice);
//
//        Map<String,Object> spuMap = (Map<String, Object>) resultData.get("SPU");
//        String spu = JSON.toJSONString(spuMap);
//        ItemsInfo itemsInfo = JSON.parseObject(spu, ItemsInfo.class);
//        String name = itemsInfo.getName();
//        System.out.println(name);
//
//        return new Result(CommonErrorCode.SUCCESS,result);
//
//    }


//    /**
//     * 提交订单
//     * 购物车页面传递的数据结构如下：
//     * {101: 3, 102: 1}
//     * {skuId: count}
//     * @param
//     * @return
//     */
//    @PostMapping("app/order/submit/{token}")   //ok
//    public Result submit(@RequestBody OrderSubmitVO orderSubmitVO,
//                         @PathVariable("token") String token){
//
//        log.info("=======================submit========================");
//        String form = null;
//        try {
//            OrderSubmitResponseVO orderSubmitResponseVO = this.orderService.submit(orderSubmitVO, token);
//            MainEntity mainEntity = orderSubmitResponseVO.getMainEntity();
//
//            //调用支付接口 支付订单
//            BigDecimal payAmount = mainEntity.getPayAmount();
//            AlipayBean alipayBean = new AlipayBean();
//            alipayBean.setSubject("云上优选 订单号："+mainEntity.getMainOrderId());//   商品的标题/交易标题/订单标题/订单关键字等。
//            alipayBean.setTotalAmount(payAmount.toString());//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
//            alipayBean.setOutTradeNo(mainEntity.getMainOrderId());//商户网站唯一订单号
//            alipayBean.setBody("云上优选");//商品名称   暂定
//
//            Result zfb = payClient.zfb(alipayBean);
//            JSONObject object = JSONObject.fromObject(zfb);
//            form = object.getString("data");
//        } catch (BusinessException businessException){
//            return new Result(businessException.getCode(),businessException.getDesc(),false);
//        }
//        return new Result(CommonErrorCode.SUCCESS,form);
//    }

//    @PostMapping("app/order/repay/{token}")   //ok
//    public Result repay(@RequestBody OrderMainVO entity,
//                         @PathVariable("token") String token){
//
//        log.info("=======================repay========================");
//        String form = null;
//        try {
//            //MainEntity mainEntity = mainService.selectMainOrderById(entity.getMainOrderId());
//            //调用支付接口 支付订单
//            BigDecimal payAmount = entity.getPayAmount();
//            AlipayBean alipayBean = new AlipayBean();
//            alipayBean.setSubject("云上优选 订单号："+entity.getMainOrderId());//   商品的标题/交易标题/订单标题/订单关键字等。
//            alipayBean.setTotalAmount(payAmount.toString());//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
//            alipayBean.setOutTradeNo(entity.getMainOrderId());//商户网站唯一订单号
//            alipayBean.setBody("云上优选");//商品名称   暂定
//
//            Result zfb = payClient.zfb(alipayBean);
//            JSONObject object = JSONObject.fromObject(zfb);
//            form = object.getString("data");
//        } catch (BusinessException businessException){
//            return new Result(businessException.getCode(),businessException.getDesc(),false);
//        }
//        return new Result(CommonErrorCode.SUCCESS,form);
//    }
































