package com.cloudbest.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.RandomUuidUtil;
import com.cloudbest.common.util.StringUtil;
import com.cloudbest.common.util.TokenUtil;
import com.cloudbest.order.entity.ItemEntity;
import com.cloudbest.order.entity.MainEntity;
import com.cloudbest.order.entity.SecondarilyEntity;
import com.cloudbest.order.feign.ItemClient;
import com.cloudbest.order.feign.PayClient;
import com.cloudbest.order.feign.UserClient;
import com.cloudbest.order.mapper.ItemMapper;
import com.cloudbest.order.mapper.MainMapper;
import com.cloudbest.order.mapper.SecondarilyMapper;
import com.cloudbest.order.otherentity.CItemsImg;
import com.cloudbest.order.otherentity.CStock;
import com.cloudbest.order.otherentity.CustomerInf;
import com.cloudbest.order.otherentity.ItemsInfo;
import com.cloudbest.order.service.OrderService;
import com.cloudbest.order.vo.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private UserClient userClient;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ItemClient itemClient;
    @Autowired
    private MainMapper mainMapper;
    @Autowired
    private SecondarilyMapper secondarilyMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private PayClient payClient;


    private static final String TOKEN_PREFIX ="order:token";

//    String Score_Url = "http://10.103.1.2:8976";
//    String Score_Url = "http://localhost:8976";
    String Score_Url = "http://172.19.73.77:8888";
    //    String Sum_Score = "/youhui/query/sumScore";
    String Sum_Score = "/manager/query/sumScore";
    //    String Add_Score = "/youhui/cloudbest/addScore";
    String Add_Score = "/cloudbest/cloudbest/addScore";
    //    String Sub_Score = "/youhui/cloudbest/subScore";
    String Sub_Score = "/manager/score/subScore";


    /**
     * 确认订单
     * 购物车页面传递的数据结构如下：
     * {101: 3, 102: 1}
     * {skuId: count}
     * @param
     * @return
     */
    @Override        //ok
    public OrderConfirmVO confirm(Map<Integer,Integer> cartMap,String token) {
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        //从购物车传数据
        // HashMap<Integer, Integer> cartMap = new HashMap<>();
        /*  cartMap.put(4,2);
            cartMap.put(5,2);
        */
        //获取用户信息
        Long userId = null;  //测试设置
        //获取用户登录信息
        try {
            userId = TokenUtil.getUserId(token);
        } catch (Exception e) {
            throw  new  RuntimeException("请先登录");//暂定异常
        }


        //       //根据用户id查询用户收货地址
//         Result addrs = userClient.queryAddr(userId);
//         JSONObject jsonObject=new JSONObject();
//         String jsonString = JSON.toJSONString(addrs.getData());
//         List<CustomerAddr> customerAddrs = JSON.parseArray(jsonString, CustomerAddr.class);
//        //查询具体的省市区地址暂未实现
//        orderConfirmVO.setAddresses(customerAddrs);

        //购物车模块存在的话
        //购物车数据提交的数据集合  可获取商品的id 根据id查找商品的价格price  商品的数量count   商品的描述信息   商品的购物券信息   商品的库存信息

        //遍历map查询订单的商品详情
        List<OrderItemVO> orderItems = cartMap.entrySet().stream().map(entrySet -> {
            //创建订单内商品对象
            OrderItemVO orderItemVO = new OrderItemVO();
            //获取商品的数量
            Integer count = entrySet.getValue();
            orderItemVO.setCount(count);

            //获取商品id
//            StringBuffer String =new StringBuffer();
//            String.append(entrySet.getKey()+",");

            Integer skuId = entrySet.getKey();

            //根据商品id名称查询图片信息
            Result skuImg = itemClient.selecImgBySkuId(skuId);
            String toJSONStringImg = JSON.toJSONString(skuImg.getData());
            CItemsImg cItemsImg = JSON.parseObject(toJSONStringImg, CItemsImg.class);
            orderItemVO.setImage(cItemsImg.getImgUrl());

            //查询库存信息
            Result stockBySkuId = itemClient.selecStockBySkuId(skuId);//接口已改
            Map<String,Object> resultData = (Map<String, Object>) stockBySkuId.getData();


            Map<String,Object> skuMap = (Map<String, Object>) resultData.get("SKU");//获取库存信息
            String sku= JSON.toJSONString(skuMap);
            CStock cStock = JSON.parseObject(sku, CStock.class);

            //根据商品id名称查询价格
            orderItemVO.setPrice(cStock.getSalePrice());
//            //根据商品id名称查询商品重量
//            orderItemVO.setWeight(cStock.getWeight());
            //根据商品id名称查询库存信息
            Integer usableStock = cStock.getUsableStock();
            if (usableStock>count){
                orderItemVO.setStatus(true);//有货
            }else {
                orderItemVO.setStatus(false);//无货
            }
            return orderItemVO;
        }).collect(Collectors.toList());

        orderConfirmVO.setOrderItems(orderItems);
        //获取购物券信息暂未实现

        //随机生成唯一令牌，防止重复提交
        String timeId = IdWorker.getTimeId().substring(0, 25);
        orderConfirmVO.setOrderToken(timeId);
        this.redisTemplate.opsForValue().set(TOKEN_PREFIX+timeId,timeId);
        return orderConfirmVO;
    }

    //提交订单开发
    @Override
    public OrderSubmitResponseVO submit(OrderSubmitVO orderSubmitVO) {

        String orderToken = orderSubmitVO.getOrderToken();

//        // 1 验证令牌防止重复提交   使用lua脚本
//        String orderToken = orderSubmitVO.getOrderToken();
//        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//        Long flag = this.redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(TOKEN_PREFIX + orderToken), orderToken);
//        if (flag == 0l) {
//            throw new RuntimeException("请不要重复提交！");//暂定异常
//        }

        // 2 验证价格

        BigDecimal totalPrice = orderSubmitVO.getTotalPrice();

        List<OrderItemVO> orderItemVOS = orderSubmitVO.getOrderItemVOS();
        if (CollectionUtils.isEmpty(orderItemVOS)){
            throw new  BusinessException(CommonErrorCode.E_901001.getCode(),"请求添加购物项！");//暂定异常
        }

        //数据库实时价格
        BigDecimal currentPrice = orderItemVOS.stream().map(orderItemVO -> {

            Result stockBySkuId = itemClient.selecStockBySkuId(orderItemVO.getSkuId());
            Map<String,Object> resultData = (Map<String, Object>) stockBySkuId.getData();


            Map<String,Object> skuMap = (Map<String, Object>) resultData.get("SKU");//获取库存信息
            String sku= JSON.toJSONString(skuMap);
            CStock cStock = JSON.parseObject(sku, CStock.class);
            return cStock.getSalePrice().multiply(new BigDecimal(orderItemVO.getCount()));

        }).reduce((a, b) -> a.add(b)).get();

        //购买的所有商品所允许的总购物券
        BigDecimal totalScore = orderItemVOS.stream().map(orderItemVO -> {
            Result stockBySkuId = itemClient.selecStockBySkuId(orderItemVO.getSkuId());
            Map<String,Object> resultData = (Map<String, Object>) stockBySkuId.getData();

            Map<String,Object> skuMap = (Map<String, Object>) resultData.get("SKU");//获取库存信息
            String sku= JSON.toJSONString(skuMap);
            CStock cStock = JSON.parseObject(sku, CStock.class);

            BigDecimal saleMultiply = cStock.getSalePrice().multiply(cStock.getScoreScale());//获取商品最大可用购物券
            return saleMultiply.multiply(new BigDecimal(orderItemVO.getCount()));
        }).reduce((a, b) -> a.add(b)).get();
        BigDecimal userIntegration = orderSubmitVO.getUseIntegration();
        int r = userIntegration.compareTo(BigDecimal.ZERO);
        OrderSubmitResponseVO orderSubmitResponseVO = null;
        if ( r == 0 ){
            //纯价格 支付
            //验价
//            if (totalPrice.compareTo(currentPrice)!=0){
//                throw new  BusinessException(CommonErrorCode.E_300123.getCode(),"请刷新页面后重试");//暂定异常
//            }
            orderSubmitResponseVO = this.common(orderSubmitVO);
        }else {

            //调取用户购物券接口
            //获取用户的购物券
            // BigDecimal userAllIntegration = BigDecimal.valueOf(80);//假定
            //BigDecimal userAllIntegration = this.sumScore(orderSubmitVO.getUserId());

//            if (userAllIntegration.compareTo(userIntegration) == -1){
//                //用户购物券不足,提醒用户，提示页面
//                throw new  BusinessException(CommonErrorCode.E_300124.getCode(),"购物券不足");//暂定异常
//            }
//            //购物券充足  验价                                                  //下单使用的购物券            //下单可使用的最多购物券
//            if(userIntegration.add(totalPrice).compareTo(currentPrice)!=0 || userIntegration.compareTo(totalScore)>0){
//
//                throw new  BusinessException(CommonErrorCode.FAIL.getCode(),"服务器异常，请刷新重试");//暂定异常
//            }
            //验库存锁库存
            orderSubmitResponseVO = this.common(orderSubmitVO);
        }
       return orderSubmitResponseVO;
    }

//DateUtil.betweenMs(com.cloudbest.common.util.DateUtil.getCurrDate(),promotion.getEndTime())

    public OrderSubmitResponseVO common(OrderSubmitVO orderSubmitVO){

        List<OrderItemVO> orderItemVOS = orderSubmitVO.getOrderItemVOS();
        String orderToken = orderSubmitVO.getOrderToken();

//        // 3 验证库存    锁定库存
//        List<SkuLockVO> skuLockVOS = orderItemVOS.stream().map(orderItemVO -> {
//            SkuLockVO skuLockVO = new SkuLockVO();
//            skuLockVO.setSkuId(orderItemVO.getSkuId());
//            skuLockVO.setNum(orderItemVO.getCount());
//            skuLockVO.setOrderToken(orderToken);
//            return skuLockVO;
//        }).collect(Collectors.toList());
//
//        Result checkAndLockStock = this.itemClient.checkAndLockStock(skuLockVOS);
//        if (checkAndLockStock.getCode()==1){
//            throw new RuntimeException(checkAndLockStock.getMessage());//暂定异常
//        }

        // 4 生成订单
        OrderSubmitResponseVO orderSubmitResponseVO = null;
        try {
            orderSubmitResponseVO = this.crateOrder(orderSubmitVO);
        } catch (Exception e) {
            System.out.println("订单创建失败！服务器异常！");
            throw new RuntimeException("订单创建失败！服务器异常！");//暂定异常
        }
        // 5 删除购物车中的记录 mq
        //发送消息队列删除购物车中信息
        //6 定时关闭订单

        return orderSubmitResponseVO;
    }

//    //获取用户积分
//    public BigDecimal sumScore(Long customerId) {
//        BigDecimal score = BigDecimal.ZERO;
//        String url = "http://10.103.1.2:8976/youhui/query/sumScore";
//        MultiValueMap<String, Long> requestEntity  = new LinkedMultiValueMap<String, Long>();
//        requestEntity.add("merchantNo", customerId);
//        RestTemplate restTemplate=new RestTemplate();
//        String result = restTemplate.postForObject(url, requestEntity, String.class);
//        JSONObject jsonObject =  JSONObject.fromObject(result);
//        if (!jsonObject.getBoolean("success")){
//            throw new BusinessException(999999,"查询购物券失败");
//        }
//        score = new BigDecimal(jsonObject.getDouble("data")).setScale(2, BigDecimal.ROUND_HALF_EVEN);
//        return score;
//    }

    @Override
    public String crateAliPay() {
        log.info("=======================crateAliPay========================");
        String form = "";
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setSubject("云上优选");//   商品的标题/交易标题/订单标题/订单关键字等。
        alipayBean.setTotalAmount(String.valueOf(0.01));//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
        alipayBean.setOutTradeNo(IdWorker.getMillisecond()+ RandomUuidUtil.generateNumString(8));//商户网站唯一订单号
//        alipayBean.setExpireTime("15m");//商户网站唯一订单号
        alipayBean.setBody("云上优选");//商品名称   暂定

        Result zfb = payClient.zfb(alipayBean);
        JSONObject object = JSONObject.fromObject(zfb);
        form = object.getString("data");
        return form;
    }


    //创建订单
    @Override
    @Transactional
    public OrderSubmitResponseVO crateOrder(OrderSubmitVO orderSubmitVO) {

        OrderSubmitResponseVO orderSubmitResponseVO = new OrderSubmitResponseVO();
        //新增主订单

        String timeId = System.currentTimeMillis()+ RandomUuidUtil.generateNumString(5);
      //  this.redisTemplate.opsForValue().set(TOKEN_PREFIX+timeId,timeId);

        MainEntity mainEntity = new MainEntity();
        //主订单号
        mainEntity.setMainOrderId(timeId);
        //获取用户信息
        Long userId = orderSubmitVO.getUserId();
        //用户id
        mainEntity.setUserId(userId);
        //订单总额
        mainEntity.setTotalAmount(orderSubmitVO.getTotalPrice());
        //应付总额
        mainEntity.setPayAmount(orderSubmitVO.getTotalPrice());//根据积分计算后，加上运费后计算得到的总额，暂未计算
        //订单消耗积分
        mainEntity.setCostScore(orderSubmitVO.getUseIntegration());
        //支付方式
        mainEntity.setPayType(orderSubmitVO.getPayType());
        //订单状态
        mainEntity.setPayStatus(1);//状态码1代表未支付，新建订单为未支付状态
        //支付时间
        mainEntity.setPayTime(null);//还未支付 设置为暂无
        //下单时间
        mainEntity.setOrderTime(LocalDateTime.now());
        //订单备注
        mainEntity.setNote(orderSubmitVO.getNote());//未设置  用户备注

        mainMapper.insert(mainEntity);//存入库中
        orderSubmitResponseVO.setMainEntity(mainEntity);


        //一个主订单对应多个子订单
        //TODO

        //新增子订单
        SecondarilyEntity secondarilyEntity = new SecondarilyEntity();
        //主订单号
        secondarilyEntity.setMainOrderId(mainEntity.getMainOrderId());
        //判断库存位置，添加子订单  暂未实现
        //子订单号
        if (StringUtil.isNotBlank(orderSubmitVO.getAncillaryOrderId())){
            secondarilyEntity.setAncillaryOrderId(orderSubmitVO.getAncillaryOrderId());//判断商家库存及在不同位置或    所属不用商家给买家发货
        }else {
            secondarilyEntity.setAncillaryOrderId(mainEntity.getMainOrderId());//判断商家库存及在不同位置或    所属不用商家给买家发货
        }
        //收货人姓名
        secondarilyEntity.setReceiverName(orderSubmitVO.getUserName());
        //收货人电话
        //根据用户id查询用户电话
        Result result = this.userClient.selectUserById(userId);
        String toJSONString = JSON.toJSONString(result.getData());
        CustomerInf customerInf = JSON.parseObject(toJSONString, CustomerInf.class);
        String mobilePhone = customerInf.getMobilePhone();
        secondarilyEntity.setReceiverPhone(mobilePhone);

        //收货人邮箱
        secondarilyEntity.setReceiverEmail(null);
        //收货址地
        String address = orderSubmitVO.getAddress();
        secondarilyEntity.setReceiverDetailAddress(address);
        //物流方式  卖家提供
        secondarilyEntity.setDeliveryCompany(orderSubmitVO.getDeliveryCompany());
        //运费  根据物流公司提供
        secondarilyEntity.setDeliveryAmount(orderSubmitVO.getDeliveryAmount());
        //物流单号
        secondarilyEntity.setDeliveryId(null);
        //发货时间
        secondarilyEntity.setDeliveryTime(null);//还未发货暂无
        //物流状态
        secondarilyEntity.setDeliveryStatus(null);//未发货
        //收货时间
        secondarilyEntity.setReceiveTime(null);//未收货  暂无
        secondarilyMapper.insert(secondarilyEntity);//存入库中
        orderSubmitResponseVO.setSecondarilyEntity(secondarilyEntity);


        //新增订单详情
        List<OrderItemVO> orderItemVOS = orderSubmitVO.getOrderItemVOS();

        if (!CollectionUtils.isEmpty(orderItemVOS)){
            orderItemVOS.forEach(itemVO->{
                Integer skuId = itemVO.getSkuId();

                Result stockBySkuId = itemClient.selecStockBySkuId(skuId);
                Map<String,Object> resultData = (Map<String, Object>) stockBySkuId.getData();

                Map<String,Object> skuMap = (Map<String, Object>) resultData.get("SKU");//获取库存信息
                String sku= JSON.toJSONString(skuMap);
                CStock cStock = JSON.parseObject(sku, CStock.class);


                cStock.getRepositoryId();//获取仓库id判断

                ItemEntity itemEntity = new ItemEntity();
                itemEntity.setOrderId(timeId);//订单号

                Map<String,Object> spuMap = (Map<String, Object>) resultData.get("SPU");//获取商品信息
                String spu = JSON.toJSONString(spuMap);
                ItemsInfo itemsInfo = JSON.parseObject(spu, ItemsInfo.class);

                itemEntity.setProductId(skuId);//商品id
                itemEntity.setItemId(cStock.getItemId());//商品id
                itemEntity.setProductName(itemsInfo.getName());//商品名
                itemEntity.setProductPrice(cStock.getSalePrice());//商品单价
                itemEntity.setProductQuantity(itemVO.getCount());//购买数量

                itemMapper.insert(itemEntity);
            });
        }
        return orderSubmitResponseVO;
    }

//    //支付成功修改订单状态
//    @Override
//    public void paySuccess(AlipayBean alipayBean ,Integer status) {
//
//        //修改订单状态
//        String outTradeNo = alipayBean.getOutTradeNo();
//        status =2;
//        this.mainMapper.upd(outTradeNo ,status);
//        MainEntity mainEntity = this.mainService.selectMainOrderById(outTradeNo);
//
//        //添加商品销量  sku销量
//        //this.itemController.secltItemsById()
//        //this.itemClient.updateSaleVolume()
//
//        //扣除用户积分
//        BigDecimal subScore = BigDecimal.ZERO;
//        if (null != String.valueOf(mainEntity.getCostScore())&&!String.valueOf(mainEntity.getCostScore()).equals("null")){
//            subScore = mainEntity.getCostScore().setScale(2, BigDecimal.ROUND_HALF_EVEN);
//        }
//        if (subScore != BigDecimal.ZERO){
//            String url = Score_Url+Sub_Score;
//            MultiValueMap<String, Object> requestEntity  = new LinkedMultiValueMap<String, Object>();
//            requestEntity.add("merchantNo", mainEntity.getUserId( ));
//            requestEntity.add("orderNo", outTradeNo);
//            requestEntity.add("score", mainEntity.getCostScore().setScale(2, BigDecimal.ROUND_HALF_EVEN));
//            //购物消费类型消费的购物券
//            requestEntity.add("transactionType", 1);
//            RestTemplate restTemplate=new RestTemplate();
//            String subScoreResult = restTemplate.postForObject(url, requestEntity, String.class);
//            JSONObject subScoreObject =  JSONObject.fromObject(subScoreResult);
//            if (subScoreObject.getInt("code")!=100000){
//                throw new RuntimeException("扣除购物券失败");
//                //return "扣除购物券失败";
//            }
//        }
//    }



//    @Override
//    public String crateAliPay() {
//        log.info("=======================crateAliPay========================");
//        String form = "";
//        AlipayBean alipayBean = new AlipayBean();
//        alipayBean.setSubject("云上优选");//   商品的标题/交易标题/订单标题/订单关键字等。
//        alipayBean.setTotalAmount(String.valueOf(0.01));//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
//        alipayBean.setOutTradeNo(IdWorker.getMillisecond()+ RandomUuidUtil.generateNumString(8));//商户网站唯一订单号
////        alipayBean.setExpireTime("15m");//商户网站唯一订单号
//        alipayBean.setBody("云上优选");//商品名称   暂定
//
//        Result zfb = payClient.zfb(alipayBean);
//        JSONObject object = JSONObject.fromObject(zfb);
//        form = object.getString("data");
//        return form;
//    }

}

























