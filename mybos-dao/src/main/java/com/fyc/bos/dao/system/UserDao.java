package com.fyc.bos.dao.system;

import com.fyc.bos.dao.BaseDao;
import com.fyc.bos.entity.system.Resource;
import com.fyc.bos.entity.system.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    User findByUsername(String username);

    @Query("select res from User user inner join user.roles role inner join role.resources res where res.resourceType='0' and user.id=?")
   public List<Resource> findMyMenus(Integer id);

    @Query("select res.grantKey from User user inner join user.roles role inner join role.resources res where  user.id=?")
    public List<String> findMyperms(Integer id);
}
