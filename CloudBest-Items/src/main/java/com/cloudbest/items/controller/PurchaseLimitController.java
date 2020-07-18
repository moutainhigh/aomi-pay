package com.cloudbest.items.controller;


import com.alibaba.fastjson.JSON;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.items.service.PurchaseLimitService;
import com.cloudbest.items.vo.PurchaseLimitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * <p>
 * 限购规则表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@CrossOrigin
@RestController
public class PurchaseLimitController {

    @Resource
    private PurchaseLimitService purchaseLimitService;

    /**
     * 添加限购规则
     * @return result
     */
    @PostMapping("/items/purchaseLimit/add")
    public Result addPruchaseLimit(@RequestBody PurchaseLimitVO purchaseLimitVO){
        log.info("添加限购规则,purchaseLimitVO:{}", JSON.toJSONString(purchaseLimitVO));

        if(purchaseLimitVO.getItemId() == null){
            return new Result(CommonErrorCode.E_901011);
        }
        if(purchaseLimitVO.getSkuId() == null){
            return new Result(CommonErrorCode.E_901012);
        }
        if(purchaseLimitVO.getPurchaseLimitVolume() == null){
            return new Result(CommonErrorCode.E_901013);
        }

        try{
            purchaseLimitService.add(purchaseLimitVO);
        }catch(Exception e){
            log.info("error:{}",e.toString());
            if(e instanceof BusinessException){
                return new Result(((BusinessException) e).getCode(),((BusinessException) e).getDesc(),false);
            }else{
                return new Result(CommonErrorCode.FAIL);
            }
        }

        return new Result(CommonErrorCode.SUCCESS);
    }


    /**
     * 修改限购规则
     * @return result
     */
    @PostMapping("/items/purchaseLimit/update")
    public Result updatePruchaseLimit(@RequestBody PurchaseLimitVO purchaseLimitVO){
        log.info("修改限购规则,purchaseLimitVO:{}", JSON.toJSONString(purchaseLimitVO));

        if(purchaseLimitVO.getId() == null){
            return new Result(CommonErrorCode.E_901017);
        }
        if(purchaseLimitVO.getPurchaseLimitVolume() == null){
            return new Result(CommonErrorCode.E_901013);
        }
        if(purchaseLimitVO.getPurchaseLimitFrequency() == null){
            return new Result(CommonErrorCode.E_901014);
        }
        try{
            purchaseLimitService.update(purchaseLimitVO);
        }catch(Exception e){
            log.info("error:{}",e.toString());
            if(e instanceof BusinessException){
                return new Result(((BusinessException) e).getCode(),((BusinessException) e).getDesc(),false);
            }else{
                return new Result(CommonErrorCode.FAIL);
            }
        }

        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 根据商品id，skuid查询限购规则
     * @return result
     */
    @PostMapping("/items/purchaseLimit/getByItemIdSkuId")
    public Result getByItemIdSkuId(@RequestBody PurchaseLimitVO purchaseLimitVO){
        log.info("根据商品id，skuid查询限购规则,purchaseLimitVO:{}", JSON.toJSONString(purchaseLimitVO));

        if(purchaseLimitVO.getItemId() == null){
            return new Result(CommonErrorCode.E_901011);
        }
        if(purchaseLimitVO.getSkuId() == null){
            return new Result(CommonErrorCode.E_901012);
        }

        PurchaseLimitVO result = new PurchaseLimitVO();
        try{
            result = purchaseLimitService.getByItemIdSkuId(purchaseLimitVO);
        }catch(Exception e){
            log.info("error:{}",e.toString());
            if(e instanceof BusinessException){
                return new Result(((BusinessException) e).getCode(),((BusinessException) e).getDesc(),false);
            }else{
                return new Result(CommonErrorCode.FAIL);
            }
        }

        return new Result(CommonErrorCode.SUCCESS,result);
    }

    /**
     * 删除限购规则
     * @return result
     */
    @PostMapping("/items/purchaseLimit/delete")
    public Result delete(@RequestBody PurchaseLimitVO purchaseLimitVO){
        log.info("删除限购规则,purchaseLimitVO:{}", JSON.toJSONString(purchaseLimitVO));

        if(purchaseLimitVO.getId() == null){
            return new Result(CommonErrorCode.E_901017);
        }

        try{
            purchaseLimitService.delete(purchaseLimitVO);
        }catch(Exception e){
            log.info("error:{}",e.toString());
            if(e instanceof BusinessException){
                return new Result(((BusinessException) e).getCode(),((BusinessException) e).getDesc(),false);
            }else{
                return new Result(CommonErrorCode.FAIL);
            }
        }

        return new Result(CommonErrorCode.SUCCESS);
    }
}
