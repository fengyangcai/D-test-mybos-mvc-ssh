package com.fyc.bos.dao.base;

import com.fyc.bos.dao.BaseDao;
import com.fyc.bos.entity.base.Area;

/**
 * @Author: fyc
 * @Date: 2020/4/29 14:39
 */
public interface AreaDao extends BaseDao<Area> {

    public Area findByProvinceAndCityAndDistrcit(String province,String city,String distrcit);
}
