package com.fyc.bos.service.system;

import com.fyc.bos.entity.system.Resource;
import com.fyc.bos.entity.system.User;
import com.fyc.bos.exception.IncorrectPasswordException;
import com.fyc.bos.exception.NoAccountException;
import com.fyc.bos.service.BaseService;

import java.util.List;

public interface UserService extends BaseService<User> {
    void bindRoleToUser(Integer userId, String roleIds);

    User login(User user) throws NoAccountException, IncorrectPasswordException;

    List<Resource> findMyMenus(Integer id);
}
