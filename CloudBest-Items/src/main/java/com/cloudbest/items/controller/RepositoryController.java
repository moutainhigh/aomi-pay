package com.cloudbest.items.controller;



import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.items.entity.Repository;
import com.cloudbest.items.service.RepositoryService;
import com.cloudbest.items.vo.ItemsInfoVO;
import com.cloudbest.items.vo.RepositoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@CrossOrigin
@RestController
public class RepositoryController {

    @Autowired
    private RepositoryService cRepositoryService;

    /**
     * 添加仓库信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/repository/add/insertRepository")
    public Result insertRepository(HttpServletRequest request, @RequestBody Repository info){
        Repository repository = new Repository();

        try{
            repository = cRepositoryService.createNewRepository(info);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,repository);
    }

    /**
     * 修改仓库信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/repository/update/updateRepository")
    public Result updateRepository(HttpServletRequest request,@RequestBody Repository info){
        Repository repository = new Repository();
        try{
            repository = cRepositoryService.updateRepository(info);;
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,repository);
    }


    /**
     * 删除仓库信息（逻辑删除）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/repository/delete/offRepository")
    public Result offRepository(HttpServletRequest request,@RequestParam(value = "repositoryId", required = true) Integer repositoryId){

        try{
            cRepositoryService.offRepository(repositoryId);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 删除仓库信息（物理删除）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/repository/delete/deleteRepository")
    public Result deleteRepository(HttpServletRequest request, @RequestBody RepositoryVO info){

        try{
            cRepositoryService.deleteRepository(info.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 查询仓库信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/repository/query/queryRepository")
    public Result queryRepository(HttpServletRequest request, @RequestBody RepositoryVO info){
        List<Repository> repositoryList = new ArrayList<>();
        try{
            repositoryList = cRepositoryService.queryRepository(info);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,repositoryList);
    }

    //用户收藏品查询
    @RequestMapping(method = RequestMethod.POST, value = "/items/repository/query/queryById")
    public Result getItemInfoById( @RequestBody RepositoryVO info){
        Repository repository = new Repository();
        try{
            repository = cRepositoryService.queryById(info.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,info);
    }
}
