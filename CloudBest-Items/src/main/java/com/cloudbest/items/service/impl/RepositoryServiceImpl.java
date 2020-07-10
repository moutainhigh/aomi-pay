package com.cloudbest.items.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.items.entity.ItemsImg;
import com.cloudbest.items.entity.Repository;
import com.cloudbest.items.enums.StatusEnum;
import com.cloudbest.items.mapper.RepositoryMapper;
import com.cloudbest.items.service.RepositoryService;
import com.cloudbest.items.vo.RepositoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private RepositoryMapper repositoryMapper;

    String errorMessage = "网络小憩~请稍后再试！";
    @Override
    public Repository createNewRepository(Repository info) {
        Repository repository = new Repository();
        repository.setAddress(info.getAddress());
        repository.setAdmin(info.getAdmin());
        repository.setCity(info.getCity());
        repository.setName(info.getName());
        repository.setTransportAmount(info.getTransportAmount());
        repository.setPhone(info.getPhone());
        repository.setStatus(1);
        repository.setSupplierId(info.getSupplierId());
        repository.setUpdateTime(new Date());
        int result = repositoryMapper.insert(repository);
        if (result==1){
            return repository;
        }else {
            throw new BusinessException(CommonErrorCode.FAIL);
        }
    }

    @Override
    public Repository updateRepository(Repository info) {
        Repository repository = repositoryMapper.selectById(info.getId());
        if (null!=repository){
            if (info.getAddress()!=null){
                repository.setAddress(info.getAddress());
            }
            if (info.getAdmin()!=null){
                repository.setAdmin(info.getAdmin());
            }
            if (info.getCity()!=null){
                repository.setCity(info.getCity());
            }
            if (info.getName()!=null){
                repository.setName(info.getName());
            }
            if (info.getTransportAmount()!=null){
                repository.setTransportAmount(info.getTransportAmount());
            }
            if (info.getPhone()!=null){
                repository.setPhone(info.getPhone());
            }
            if (info.getStatus()!=null){
                repository.setStatus(info.getStatus());
            }
            if (info.getSupplierId()!=null){
                repository.setName(info.getName());
            }
            repository.setUpdateTime(new Date());
            int result = repositoryMapper.updateById(repository);
            if (result==1){
                return info;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901003.getCode(),errorMessage);
        }
    }

    @Override
    public void deleteRepository(Integer repositoryId) {
        Repository repository = repositoryMapper.selectById(repositoryId);
        if (null!=repository){
            int result = repositoryMapper.deleteById(repositoryId);
            if (result==0){
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901003.getCode(),errorMessage);
        }
    }

    @Override
    public Repository offRepository(Integer repositoryId) {
        Repository repository = repositoryMapper.selectById(repositoryId);
        if (null!=repository){
            repository.setUpdateTime(new Date());
            repository.setStatus(9);
            int result = repositoryMapper.updateById(repository);
            if (result==1){
                return repository;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901003.getCode(),errorMessage);
        }
    }

    @Override
    public List<Repository> queryRepository(RepositoryVO info) {
        //分页参数
        int current =0;
        int size =0;
        if (info.getCurrent()!=null&&info.getSize()!=null){
            current = info.getCurrent();
            size = info.getSize();
        }else {
            current = 0;
            size = 200;
        }
        //查询状态为启用的仓库
        QueryWrapper<Repository> queryWrapper = new QueryWrapper();
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getSupplierId()!=null){
            queryWrapper.eq("supplier_id", info.getSupplierId());
        }
        if (info.getName()!=null){
            queryWrapper.eq("name", info.getName());
        }
        if (info.getStatus()!=null){
            queryWrapper.eq("status", info.getStatus());
        }else {
            queryWrapper.eq("status", StatusEnum.SHELVES.getValue());
        }        Page<Repository> page = new Page<>(current, size);
        IPage<Repository> repositories = repositoryMapper.selectPage(page, queryWrapper);
        if (repositories.getTotal()>0){
            return repositories.getRecords();
        }
        return new ArrayList<>();
    }

    @Override
    public Repository queryById(Integer id) {
        Repository repository = repositoryMapper.selectById(id);
        return repository;
    }
}
