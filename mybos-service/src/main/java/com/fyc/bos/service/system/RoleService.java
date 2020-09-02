package com.fyc.bos.service.system;

import com.fyc.bos.entity.system.Role;
import com.fyc.bos.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

public interface RoleService extends BaseService<Role> {
    void bindResToRole(Integer roleId, String resIds);

    Page<Role> findAll(Specification<Role> spec, PageRequest pageRequest, Integer userId);
}
