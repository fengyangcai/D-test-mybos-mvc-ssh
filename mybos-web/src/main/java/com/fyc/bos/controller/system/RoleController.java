package com.fyc.bos.controller.system;

import com.fyc.bos.controller.BaseController;
import com.fyc.bos.entity.system.Role;
import com.fyc.bos.entity.system.User;
import com.fyc.bos.service.system.RoleService;
import com.fyc.bos.utils.DataGridResult;
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
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

@RequestMapping("/role")
@Controller
public class RoleController extends BaseController<Role> {

    private RoleService roleService;

    @Resource
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
        super.setBaseService(roleService);
    }

    /*
    角色绑定资源方法
     */
    @RequestMapping("/bindResToRole")
    public ResponseEntity<Map<String,Object>>bindResToRole(HttpServletRequest request){
        Integer roleId = new Integer(request.getParameter("roleId"));
        String resIds = request.getParameter("resIds");

        try {
            roleService.bindResToRole(roleId,resIds);
            result.put("code",200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",400);
            result.put("msg",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return ResponseEntity.ok(result);

    }

    @RequestMapping("/listByPage")
    public ResponseEntity<DataGridResult> listByPage(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "rows", defaultValue = "100") int rows,Role role){


        PageRequest pageRequest = new PageRequest(page-1,rows);

        //分页查询方法需要的形参
        Specification<Role> specification = new Specification<Role>() {

            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> predicates = new ArrayList<>();

                Predicate[] preArray = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(preArray));
            }
        };

        Page<Role> rolePage = roleService.findAll(specification, pageRequest);

        DataGridResult dataGridResult = new DataGridResult();
        dataGridResult.setTotal(rolePage.getTotalElements());
        dataGridResult.setRows(rolePage.getContent());
        return ResponseEntity.ok(dataGridResult);


    }

    @RequestMapping("/listByPageAndRole")
   public ResponseEntity<DataGridResult> listByPageAndRole(@RequestParam(value = "userId")Integer userId,@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "rows", defaultValue = "100") int rows){

       PageRequest pageRequest = new PageRequest(page - 1, rows);
       //分页查询
       Specification<Role> spec = new Specification<Role>() {
           ArrayList<Predicate> predicates=new ArrayList<Predicate>();

           @Override
           public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

               Predicate[] preArray = new Predicate[this.predicates.size()];
               Predicate and = cb.and( predicates.toArray(preArray));
               return and;
           }
       };

      Page<Role> rolePage= roleService.findAll(spec,pageRequest,userId);

       DataGridResult dataGridResult = new DataGridResult(rolePage.getTotalElements(), rolePage.getContent());
       return ResponseEntity.ok(dataGridResult);

   }
}
