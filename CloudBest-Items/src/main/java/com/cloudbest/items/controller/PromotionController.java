package com.cloudbest.items.controller;

import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.items.entity.ItemsInfo;
import com.cloudbest.items.service.ItemsInfoService;
import com.cloudbest.items.vo.ItemsInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品详情表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@CrossOrigin
@RestController
public class PromotionController {

    @Autowired
    private ItemsInfoService cItemsInfoService;


}
