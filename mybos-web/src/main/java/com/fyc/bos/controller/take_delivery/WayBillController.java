package com.fyc.bos.controller.take_delivery;

import com.fyc.bos.controller.BaseController;
import com.fyc.bos.entity.base.FixedArea;
import com.fyc.bos.entity.take_delivery.WayBill;
import com.fyc.bos.service.take_delivery.WayBillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
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
import java.util.UUID;

@Controller
@RequestMapping("/wayBill")
public class WayBillController extends BaseController<WayBill> {

    private WayBillService wayBillService;

    @Resource
    public void setWayBillService(WayBillService wayBillService) {
        this.wayBillService = wayBillService;
        // 给BaseAction的baseService赋值
        super.setBaseService(wayBillService);
    }

    @RequestMapping("/listByPage")
    public ResponseEntity<Map<String, Object>> listByPage(final WayBill wayBill, @RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "rows", defaultValue = "10") Integer rows){

        PageRequest pageRequest = new PageRequest(page - 1, rows);

        Specification<WayBill> specification = new Specification<WayBill>() {
            ArrayList<Predicate> predicateList= new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<WayBill> root, CriteriaQuery<?> query, CriteriaBuilder cb) {


                Predicate[] predicateArray = new Predicate[this.predicateList.size()];
                return cb.and(predicateList.toArray(predicateArray));
            }
        };

        Page<WayBill> all = wayBillService.findAll(specification, pageRequest);
        result.put("total", all.getTotalElements());
        result.put("rows", all.getContent());
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/saveWayBill")
    public ResponseEntity<Map<String,Object>> saveWayBill(WayBill wayBill){
        String wayBillNum = wayBill.getWayBillNum();
        // 判断用户是否填写了运单号，且该运单号数据库存在的
        if (wayBillNum!=null&& !wayBillNum.trim().equals("")){
            try {
                // 查询数据库是否存在该运单
                WayBill persistWayBill = wayBillService.findByWayBillNum(wayBillNum);
                if (persistWayBill!=null){
                    // 赋值运单ID,改为更新操作
                    wayBill.setId(persistWayBill.getId());
                    wayBillService.save(wayBill);
                    result.put("success",true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.put("code", 200);
                result.put("msg", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }

        }
        try {
            // 新增操作
            //设置运单号
            wayBill.setWayBillNum(UUID.randomUUID().toString());
            wayBillService.save(wayBill);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", false);
            result.put("msg", e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 根据运单号查询运单
     *
     * @throws Exception
     */
    @RequestMapping("/findByWayBillNum")
    public ResponseEntity<WayBill> findByWayBillNum(WayBill wayBill){
        // 查询数据库是否存在该运单
        WayBill persistWayBill = wayBillService.findByWayBillNum(wayBill.getWayBillNum());
        return ResponseEntity.ok(persistWayBill);
    }
}
