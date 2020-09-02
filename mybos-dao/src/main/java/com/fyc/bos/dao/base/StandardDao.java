package com.fyc.bos.dao.base;

import com.fyc.bos.dao.BaseDao;
import com.fyc.bos.entity.base.Standard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: fyc
 * @Date: 2020/4/25 6:28
 */
public interface StandardDao extends BaseDao<Standard> {
    //public void sava(Standard standard);
    //一、1）基于方法命名规则匹配查询
    //需求：查询name为“10-20斤”的数据
    public List<Standard> findByNameIs(String name);

    public  List<Standard> findByNameAndMinWeight(String name, String minWeight);

    public List<Standard> findByNameLike(String name);

    @Query("from  Standard where name = ?")
    public  List<Standard> findName(String name);

    @Query("update Standard set minLength =? where id = ?")
    @Transactional
    @Modifying
    public  void updateMinLength(String minLength, int id);


}
