package com.fyc.bos.controller.system;

import com.fyc.bos.controller.BaseController;
import com.fyc.bos.entity.system.Resource;
import com.fyc.bos.service.system.ResourceService;
import com.fyc.bos.utils.DataGridResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resources;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/resource")
@Controller
public class ResourceController extends BaseController<Resource> {

    private ResourceService resourceService;

    @javax.annotation.Resource
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
        super.setBaseService(resourceService);
    }
    @RequestMapping("/listByPage")
    public ResponseEntity<DataGridResult> listByPage(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "rows", defaultValue = "10") int rows,Resource resource){

        DataGridResult dataGridResult = new DataGridResult();
        try {
            List<Resource> resources = resourceService.findAll();
            dataGridResult.setTotal(new Long(resources.size()));
            dataGridResult.setRows(resources);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }

        return ResponseEntity.ok(dataGridResult);
    }

    @RequestMapping("/saveResource")
    public void printResource(HttpServletResponse response, Resource resource) throws IOException {

        System.out.println(resource.toString());
        try {//logger.info("开始保存的数据： "+model.toString());
            resourceService.save(resource);
            result.put("code", 200);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg",e.getMessage());
        }
        writeJson(result, response);

    }
    @RequestMapping("/listByPageAndBindRes")
    public ResponseEntity<DataGridResult> listByPageAndBindRes(Integer roleId,Integer page,Integer rows){
        //分页查询
        PageRequest pageRequest = new PageRequest(page - 1, rows);

        Specification<Resource> spc = new Specification<Resource>() {
            List<Predicate> preList = new ArrayList<Predicate>();
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {

                Predicate[] preArray = new Predicate[preList.size()];
                return cb.and(preList.toArray(preArray));
            }


        };
        Page<Resource> pageBean = resourceService.findAll(spc, pageRequest,roleId);
        //2.取出数据
        List<Resource> list = pageBean.getContent();
        long totalElements = pageBean.getTotalElements();

        //3.把数据转换为json
        DataGridResult dataGridResult = new DataGridResult(totalElements, list);


        return ResponseEntity.ok(dataGridResult);

    }

}
