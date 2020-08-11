package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.model.GetBankByKeywordsRequest;
import com.aomi.pay.service.DictionaryBankBizService;
import com.aomi.pay.service.DictionaryBankService;
import com.aomi.pay.vo.BaseResponse;
import com.aomi.pay.vo.DictionaryBankVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 银行编码数据字典接口Controller
 *
 * @author : hdq
 * @date 2020/8/11
 */
@Slf4j
@CrossOrigin
@RestController
@SuppressWarnings(value={"unchecked","rawtypes"})
@Api(value = "DictionaryBankController", tags = "银行编码数据字典接口管理")
@RequestMapping("/bank")
public class DictionaryBankController {

    @Resource
    private DictionaryBankService dictionaryBankService;

    @Resource
    private DictionaryBankBizService dictionaryBankBizService;

    @ApiOperation(value = "获取所有银行编码信息")
    @PostMapping("/getAllBank")
    public BaseResponse getAllBank() {
        log.info("--------获取所有银行编码信息开始--------");
        List<DictionaryBankVO> dictionaryBankList = dictionaryBankService.getAllBank();
        log.info("--------获取所有银行编码信息结束--------");
        return new BaseResponse(CommonErrorCode.SUCCESS, dictionaryBankList);
    }

    @ApiOperation(value = "根据关键字模糊匹配银行信息")
    @PostMapping("/getBankByKeywords")
    public BaseResponse getBankByKeywords(@RequestBody GetBankByKeywordsRequest req) throws Exception {
        log.info("--------根据关键字模糊匹配银行信息开始--------");
        List<DictionaryBankVO> dictionaryBankList = dictionaryBankBizService.getBankByKeywords(req.getKeywords());
        log.info("--------根据关键字模糊匹配银行信息结束--------");
        return new BaseResponse(CommonErrorCode.SUCCESS, dictionaryBankList);
    }
}
