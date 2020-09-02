package com.fyc.bos.controller.base;

import com.fyc.bos.controller.BaseController;
import com.fyc.bos.entity.base.Courier;
import com.fyc.bos.entity.base.Standard;
import com.fyc.bos.service.base.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: fyc
 * @Date: 2020/4/27 15:04*/


@Controller
@RequestMapping("/base/courier")
public class CourierController extends BaseController<Courier> {


    private CourierService courierService;

    @Resource
    public void setCourierService(CourierService courierService) {
        this.courierService = courierService;
        //把BaseController的baseService赋值
        super.setBaseService(courierService);
    }


    private Specification<Courier> buildSpecification(final Courier courier) {

        // 创建Specification对象
        Specification<Courier> spec = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> preList = new ArrayList<Predicate>();
                if (courier.getCourierNum() != null && !courier.getCourierNum().trim().equals("")) {
                    preList.add(cb.like(root.get("courierNum").as(String.class), "%" + courier.getCourierNum() + "%"));

                }
                if (courier.getName()!=null&&!courier.getName().trim().equals("")){
                    preList.add(cb.like(root.<String>get("name").as(String.class),"%"+courier.getName()+"%"));
                }
                if(courier.getTelephone()!=null && !courier.getTelephone().trim().equals("")) {
                    preList.add(cb.like(root.get("telephone").as(String.class), "%" + courier.getTelephone() + "%"));
                }
                //取派标准
                if(courier.getStandard()!=null && courier.getStandard().getId()!=null){
                    //多表查询
                    //sql: select * from t_courier c inner join t_standard s on s.id=c.standard_id where s.id=?
                    Join<Courier, Standard> join = root.join("standard", JoinType.INNER);
                    preList.add(cb.equal(join.get("id"),courier.getStandard().getId()));
                }

                    Predicate[] preArray = new Predicate[preList.size()];
                return cb.and(preList.toArray(preArray));
            }
        };
        return spec;
    }

    @RequestMapping("/listByPage")
    public ResponseEntity<Map<String, Object>> listByPage(int page, int rows, Courier courier) {
        PageRequest pageRequest = new PageRequest(page-1, rows);
        Specification<Courier> spc = buildSpecification(courier);
        Page<Courier> pageBean= courierService.findAll(spc, pageRequest);
        List<Courier> list = pageBean.getContent();
        result.put("total",pageBean.getTotalElements());
        result.put("rows",list);

        return ResponseEntity.ok(result);

    }
}

