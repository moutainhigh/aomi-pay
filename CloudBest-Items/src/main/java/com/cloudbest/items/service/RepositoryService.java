package com.cloudbest.items.service;

import com.cloudbest.items.entity.Repository;
import com.cloudbest.items.vo.RepositoryVO;

import java.util.List;

/**
 * <p>
 * 仓库表 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
public interface RepositoryService {

    Repository createNewRepository(Repository info);

    Repository updateRepository(Repository info);

    Repository offRepository(Integer repositoryId);

    void deleteRepository(Integer repositoryId);

    List<Repository> queryRepository(RepositoryVO info);

    Repository queryById(Integer id);
}
