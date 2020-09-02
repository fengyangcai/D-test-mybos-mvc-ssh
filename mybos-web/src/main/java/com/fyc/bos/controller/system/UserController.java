package com.fyc.bos.controller.system;

import com.fyc.bos.controller.BaseController;
import com.fyc.bos.entity.system.User;
import com.fyc.bos.service.system.UserService;
import com.fyc.bos.utils.DataGridResult;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
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
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User> {
    private static Logger logger = Logger.getLogger(UserController.class);
    private UserService userService;

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
        super.setBaseService(userService);
    }


    @RequestMapping("/findMyMenus")
    public ResponseEntity<List<com.fyc.bos.entity.system.Resource>> findMyMenus(HttpServletRequest request){
        //从session找到登陆的用户
        User user = (User) request.getSession().getAttribute("USERBOSSESSION");
        //查询登录用户的菜单资源
       List<com.fyc.bos.entity.system.Resource> resourceList= userService.findMyMenus(user.getId());
        return ResponseEntity.ok(resourceList);

    }

  /*
  登陆
   */
    @RequestMapping("/login")
    public ResponseEntity<Map<String,Object>> login(HttpServletRequest request, @RequestParam(value = "validCode")String validCode, User user){
        //判断验证码是否正确
        Object validCodeString = request.getSession().getAttribute("key");

        Subject subject = SecurityUtils.getSubject();
        //对用户输入的密码进行md5加密
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), user.getUsername(), 2);

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), md5Hash.toString());

        if (!validCodeString.equals(validCode)){
            result.put("code",400);
            result.put("msg","验证码不正确");
            System.out.println(validCodeString+"------------");


        }else {

            try {
                subject.login(token);
                User loginUser = (User) subject.getPrincipal();

                //保存用户信息到会话，用于在页面展示，而不用于认证操作
                request.getSession().setAttribute("USERBOSSESSION",loginUser);
                result.put("code",200);
            } catch (UnknownAccountException e) {
                result.put("code",400);
                result.put("msg","用户名不存在");
            } catch (IncorrectCredentialsException e) {
                //用户名不存在
                result.put("code", 408);
                result.put("msg", "密码错误");

            }catch (Exception e){
                result.put("code",500);
                result.put("msg","系统异常");
            }

        }

        return ResponseEntity.ok(result);

    }


    @RequestMapping("/listByPage")
    public ResponseEntity<DataGridResult> listByPage(@RequestParam(value = "page",defaultValue = "0")Integer page,@RequestParam(value = "rows",defaultValue = "20")Integer rows,User user){

        PageRequest pageRequest = new PageRequest(page - 1, rows);

        Specification<User> spec = new Specification<User>() {

        ArrayList<Predicate> predicateArrayList = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                //查询的条件

                Predicate[] predicates = new Predicate[predicateArrayList.size()];
                return cb.and(predicateArrayList.toArray(predicates));
            }
        };

        Page<User> userPage = userService.findAll(spec, pageRequest);
        DataGridResult dataGridResult = new DataGridResult(userPage.getTotalElements(), userPage.getContent());
        return ResponseEntity.ok(dataGridResult);


    }

    @RequestMapping("/bindRoleToUser")
    public ResponseEntity<Map<String ,Object>> bindRoleToUser(@RequestParam(value = "userId")Integer userId, @RequestParam(value = "roleIds")String roleIds, @RequestParam(value = "page",defaultValue = "0")Integer page, @RequestParam(value = "rows",defaultValue = "100")Integer rows){
        logger.info("-----------------------"+userId +"--"+roleIds+"page:"+page+",rows: "+rows);
        try {
            userService.bindRoleToUser(userId,roleIds);
            result.put("code",200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",400);
            result.put("message",e.getMessage());

        }
        return ResponseEntity.ok(result);

    }

    @RequestMapping("/logout")
    public String logout(){

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";

        }


}
