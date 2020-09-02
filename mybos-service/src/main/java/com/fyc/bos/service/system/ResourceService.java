package com.fyc.bos.service.system;

import com.fyc.bos.entity.system.Resource;
import com.fyc.bos.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

public interface ResourceService extends BaseService<Resource> {
    Page<Resource> findAll(Specification<Resource> spc, PageRequest pageRequest, Integer roleId);
}
