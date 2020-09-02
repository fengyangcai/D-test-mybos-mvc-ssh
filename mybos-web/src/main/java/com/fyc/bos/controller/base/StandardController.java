package com.fyc.bos.controller.base;


import com.fyc.bos.controller.BaseController;
import com.fyc.bos.entity.base.Standard;
import com.fyc.bos.service.base.StandardService;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: fyc
 * @Date: 2020/4/25 6:41
 */
@Controller
@RequestMapping("/base/standard")
public class StandardController extends BaseController<Standard> {
    private static Logger logger = Logger.getLogger(StandardController.class);

    private StandardService standardService;

    @Resource
    public void setStandardService(StandardService standardService) {
        this.standardService = standardService;
        super.setBaseService(standardService);
    }

    @RequestMapping("/listByPage")
    public ResponseEntity<Map<String, Object>> listByPage(int page, int rows, Standard standard) {
        PageRequest pageRequest = new PageRequest(page-1, rows);
        Specification<Standard> spc = buildSpecification(standard);
        Page<Standard> pageBean = standardService.findAll(spc, pageRequest);
        List<Standard> list = pageBean.getContent();
        result.put("total",pageBean.getTotalElements());
        result.put("rows",list);

        return ResponseEntity.ok(result);

    }

    private Specification<Standard> buildSpecification( final Standard standard) {
        logger.info("standard的分页查找开始");

        Specification<Standard> spec = new Specification<Standard>() {

            @Override
            public Predicate toPredicate(Root<Standard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> preList = new ArrayList<Predicate>();

                // 名称
                if (standard.getName()!= null &&!standard.getName().trim().equals("")) {
                    // .as(String.class): 因为like查询必须是字符串类型的查询，所以必须强制声明为String类型
                    preList.add(cb.like(root.get("name").as(String.class), "%" + standard.getName() + "%"));
                }

                // 最小重量
                if (standard.getMinWeight()!= null && !standard.getMinWeight().equals("")) {
                    preList.add(cb.equal(root.get("minWeight"),standard.getMinWeight()));
                }

                // 最小长度
                if (standard.getMinLength() != null && !standard.getMinLength().equals("")) {
                    preList.add(cb.equal(root.get("minLength"), standard.getMinLength()));
                }

                Predicate[] preArray = new Predicate[preList.size()];
                return cb.and(preList.toArray(preArray));
            }
        };
        return spec;
    }
}
