package com.fyc.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Author: fyc
 * @Date: 2020/4/26 10:17
 */
@NoRepositoryBean
public interface BaseDao<T> extends JpaRepository<T,Integer>, JpaSpecificationExecutor<T> {
}
