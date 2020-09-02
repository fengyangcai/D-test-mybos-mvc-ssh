package com.fyc.bos.controller.base;

import com.fyc.bos.constant.Constants;
import com.fyc.bos.controller.BaseController;
import com.fyc.bos.entity.base.FixedArea;
import com.fyc.bos.service.base.FixedAreaService;
import com.fyc.crm.pojo.Customer;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: fyc
 * @Date: 2020/4/30 17:13
 */
@Controller
@RequestMapping("/base/fixedArea")
public class FixedAreaController extends BaseController<FixedArea> {

    private FixedAreaService fixedAreaService;

    @Resource
    public void setFixedAreaService(FixedAreaService fixedAreaService) {
        this.fixedAreaService = fixedAreaService;
        super.setBaseService(fixedAreaService);
    }

    @RequestMapping("/associateCustomersToFixedArea")
    public ResponseEntity<Map<String,Object>> associateCustomersToFixedArea(@RequestParam(value = "fixedAreaId",required = true)String fixedAreaId,@RequestParam(value = "custIds",required = true)String custIds){

        try {
            WebClient.create(Constants.CRM_URL+"/customerService/associateCustomersToFixedArea?fixedAreaId="+fixedAreaId+"&custIds="+custIds).put(null);
            result.put("code",200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",500);
            result.put("msg", e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

   // 获取绑定的客户
    @RequestMapping("/findByHasAssociateCustomers")
    public ResponseEntity<List<Customer>> findByHasAssociateCustomers(@RequestParam(name = "fixedAreaId", required = true) Integer fixedAreaId) {
        List<Customer> customerList = (List<Customer>) WebClient.create(Constants.CRM_URL+"/customerService/findByHasAssociateCustomers?fixedAreaId="+fixedAreaId).accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        return ResponseEntity.ok(customerList);
    }

   // 获取没有绑定客户的数据
    @RequestMapping("/findByNoAssociateCustomers")
    public ResponseEntity<List<Customer>> findByNoAssociateCustomers() {
        List<Customer> customerList = (List<Customer>) WebClient.create(Constants.CRM_URL + "/customerService/findByNoAssociateCustomers").accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        return ResponseEntity.ok(customerList);
    }


    @RequestMapping("/listByPage")
    public ResponseEntity<Map<String, Object>> listByPage(final FixedArea fixedArea, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "10") Integer rows) {

        PageRequest pageRequest = new PageRequest(page - 1, rows);

        Specification<FixedArea> specification = new Specification<FixedArea>() {
            ArrayList<Predicate> arrayList = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                if (fixedArea.getFixedAreaName() != null && !fixedArea.getFixedAreaName().trim().equals("")) {
                    arrayList.add(cb.like(root.get("fixedAreaName").as(String.class), "%" + fixedArea.getFixedAreaName() + "%"));
                }
                if (fixedArea.getTelephone() != null && !fixedArea.getTelephone().trim().equals("")) {
                    arrayList.add(cb.like(root.get("telephone").as(String.class), "%" + fixedArea.getTelephone() + "%"));
                }

                Predicate[] preArray = new Predicate[arrayList.size()];
                Predicate predicate = cb.and(arrayList.toArray(preArray));
                return predicate;
            }


        };
        Page<FixedArea> fixedAreas = fixedAreaService.findAll(specification, pageRequest);
        result.put("total", fixedAreas.getTotalElements());
        result.put("rows", fixedAreas.getContent());

        return ResponseEntity.ok(result);
    }

}
