package com.fyc.bos.service.take_delivery.impl;

import com.fyc.bos.dao.take_delivery.PromotionDao;
import com.fyc.bos.entity.take_delivery.Promotion;
import com.fyc.bos.service.impl.BaseServiceImpl;
import com.fyc.bos.service.take_delivery.PromotionService;
import com.fyc.bos.entity.take_delivery.PageBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: fyc
 * @Date: 2020/5/6 20:40
 */
@Service
@Transactional
public class PromotionServiceImpl extends BaseServiceImpl<Promotion> implements PromotionService {

    private PromotionDao promotionDao;

    @Resource
    public void setPromotionDao(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
        super.setBaseDao(promotionDao);
    }

    @Override
    public PageBean<Promotion> queryByPage(Integer page, Integer pageSize) {

        Page<Promotion> all = promotionDao.findAll(new PageRequest(page, pageSize));

        long totalElements = all.getTotalElements();
        List<Promotion> content = all.getContent();

        return new PageBean<Promotion>(content, totalElements);
    }

    @Override
    public void updateStatus(Date date) {
        promotionDao.updateStatus(date);
    }
}
