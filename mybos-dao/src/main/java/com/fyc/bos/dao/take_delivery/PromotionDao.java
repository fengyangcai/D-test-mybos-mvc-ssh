package com.fyc.bos.dao.take_delivery;

import com.fyc.bos.dao.BaseDao;
import com.fyc.bos.entity.take_delivery.Promotion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * @Author: fyc
 * @Date: 2020/5/6 20:34
 */
public interface PromotionDao extends BaseDao<Promotion> {
    @Query("update Promotion set  status='0' where endDate < ? and status ='1'")
    @Modifying
   public  void updateStatus(Date date);
}
