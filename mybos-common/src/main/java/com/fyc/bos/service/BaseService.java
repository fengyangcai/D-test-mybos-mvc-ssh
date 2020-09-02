package com.fyc.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @Author: fyc
 * @Date: 2020/4/26 10:35
 */
public interface BaseService<T> {
    public List<T> findAll() ;

    public Page<T> findAll(Specification<T> spec, Pageable pageable);


    public void save(T model);

    public T findById(Integer uuid);

    public void delete(String ids);

    public void save(List<T> list);

    public List<T> findAll(Specification<T> spec);
}
