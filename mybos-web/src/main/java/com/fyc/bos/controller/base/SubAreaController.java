package com.fyc.bos.controller.base;

import com.fyc.bos.controller.BaseController;
import com.fyc.bos.entity.base.SubArea;
import com.fyc.bos.service.base.SubAreaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: fyc
 * @Date: 2020/4/30 23:44
 */
@Controller
@RequestMapping("/base/subArea")
public class SubAreaController extends BaseController<SubArea> {

    private SubAreaService subAreaService;

    @Resource
    public void setSubAreaService(SubAreaService subAreaService) {
        this.subAreaService = subAreaService;
        super.setBaseService(subAreaService);
    }

    @RequestMapping("/listByPage")
    public ResponseEntity<Map<String, Object>> listByPage(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "20") Integer rows, final SubArea subArea) {

        PageRequest pageRequest = new PageRequest(page - 1, rows);
        Specification<SubArea> spc = new Specification<SubArea>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {

                ArrayList<Predicate> arrayList = new ArrayList();

                if (subArea.getStartNum() != null && !subArea.getStartNum().trim().equals("")) {

                    arrayList.add(cb.like(root.get("startNum").as(String.class), "%" + subArea.getStartNum() + "%"));
                }
                if (subArea.getEndNum()!=null&&!subArea.getEndNum().trim().equals("")){
                    arrayList.add(cb.like(root.<String>get("endNum"),"%"+subArea.getEndNum()+"%"));
                }
                if (subArea.getKeyWords()!=null&&!subArea.getKeyWords().trim().equals("")){
                    arrayList.add(cb.like(root.<String>get("keyWords"),"%"+subArea.getKeyWords()+"%"));
                }
                if (subArea.getAssitKeyWords()!=null&&!subArea.getAssitKeyWords().trim().equals("")){
                    arrayList.add(cb.like(root.<String>get("assistKeyWords"),"%"+subArea.getAssitKeyWords()+"%"));
                }
                Predicate[] predicates = new Predicate[arrayList.size()];
                return  cb.and(arrayList.toArray(predicates));
            }
        };

        Page<SubArea> pageBean = subAreaService.findAll(spc, pageRequest);

            result.put("total",pageBean.getTotalElements());
            result.put("rows",pageBean.getContent());
        return ResponseEntity.ok(result);
    }
}
