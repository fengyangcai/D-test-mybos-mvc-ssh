package com.fyc.bos.service.take_delivery;

import com.fyc.bos.entity.take_delivery.PageBean;
import com.fyc.bos.entity.take_delivery.Promotion;
import com.fyc.bos.service.BaseService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Date;

/**
 * @Author: fyc
 * @Date: 2020/5/6 20:40
 */
public interface PromotionService extends BaseService<Promotion> {

    /**
     * 分页展示促销信息
     * page:当前页码
     * pageSize:页面大小
     */
    @GET
    @Path("/queryByPage")
    @Produces(MediaType.APPLICATION_JSON)
    public PageBean<Promotion> queryByPage(@QueryParam("page")Integer page, @QueryParam("pageSize")Integer pageSize);

    void updateStatus(Date date);
}
