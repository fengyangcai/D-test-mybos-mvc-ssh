package com.fyc.bos.fore;

import com.fyc.bos.constant.Constants;
import com.fyc.bos.controller.BaseController;
import com.fyc.bos.utils.MailUtils;
import com.fyc.bos.utils.SendSmsByaliUtils;
import com.fyc.bos.utils.YanZhengMa;
import com.fyc.crm.pojo.Customer;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: fyc
 * @Date: 2020/5/5 1:27
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController<Customer> {
    //注入RedisTemplate
    @Resource
    private RedisTemplate<String,String>redisTemplate;

    @RequestMapping("/regist")
    @ResponseBody
    public Map<String, Object> regist(HttpServletRequest request, @RequestParam("validCode") String validCode, Customer customer) {
        //判断手机校验是否合法
        //取出session的验证码
        String validCode1 = (String) request.getSession().getAttribute("validCode");
        System.out.println(validCode + "--" + validCode1);
        if (!validCode.equals(validCode1)) {
            result.put("success", false);
            result.put("msg", "手机验证校验失败");
            return result;
        }
        //判断数据库是否存在了这个手机号码
        Customer cust = WebClient.create(Constants.CRM_URL + "/customerService/checkTelephone?telephone=" + customer.getTelephone()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if (cust!=null){
            //该手机已经被注册了
            result.put("success",false);
            result.put("msg","该手机已经注册，请更换");
            return result;
        }
        //到这里证明可以被注册的手机号码正确，保存客户
        WebClient.create(Constants.CRM_URL + "/customerService").type(MediaType.APPLICATION_JSON).post(customer);
        //生成激活码写给邮件的链接
        String activeCode = UUID.randomUUID().toString();
        //把激活码保存到哪里？
        //方案一：保存session域，有效时间比较短，不合适
        //方案二：保存数据库，做法：在客户表添加一个激活码字段，比较麻烦，需要维护一些字段
        //方案三：保存redis缓存数据库。如何保存？
        //把激活码保存到redis
            redisTemplate.opsForValue().set("BOS-FORE"+customer.getTelephone(),activeCode, 48,TimeUnit.HOURS);

        try {
            String mailTitle = "BOS系统的激活邮件";
            String mailContent = "尊敬的用户，<br/>恭喜你在我们平台注册用户，请通过以下链接完成最后一步激活操作。<br>"
                    + "<a href='http://localhost:9092/customer/activeCustomer?telephone='"+customer.getTelephone()+"&validCode="+validCode+">激活链接</a>";
            MailUtils.sendMail(customer.getEmail(), mailTitle, mailContent);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping("/sendSms")
    public ResponseEntity<Map<String, Object>> validCode(HttpServletRequest request, HttpServletResponse response) {
        String telephone = request.getParameter("telephone");

        String code4 = YanZhengMa.getCode4();

        HttpSession session = request.getSession();
        //保存验证码到session会话
        //保存到session会话中，如果是多台机器的集群的话，保存到非关系型数据库。这里是单机的就保存到session中了
        session.setAttribute("validCode", code4);
        System.out.println("验证码为：" + code4);
        //调用工具
        try {
            Boolean aBoolean = SendSmsByaliUtils.sendSms(telephone, "德馨人力", "SMS_176531295", code4);
            if (aBoolean) {
                result.put("success", true);

            } else {
                result.put("success", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/activeCustomer")
    public ResponseEntity<String> activeCustomer(String telephone,String validCode){
        String res="";
        ////判断激活码是否合法的
        //从redis取出激活码
        String redisActiveCode = redisTemplate.opsForValue().get("BOS-FORE" + telephone);

        if (validCode==null||redisActiveCode==null||!redisActiveCode.equals(validCode)){
             res="激活码无效";
        }else {
            //判断是否已经激活
          Customer customer= (Customer) WebClient.create(Constants.CRM_URL+"/customerService/checkTelephone?telephone="+telephone).accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
            if (customer.getType()==null||!customer.getType().equals("1")){
                WebClient.create(Constants.CRM_URL + "/customerService?telephone="+telephone).put(null);
                res="激活成功";
            }else {
                res="该客户已经激活过了！";
            }
        }

        return ResponseEntity.ok(res);
    }

   /* @RequestMapping("/saveCustomer")
    public  ResponseEntity<Map<String,Object>> save(Customer customer){
        WebClient.create(Constants.CRM_URL + "/customerService").type(MediaType.APPLICATION_JSON).post(customer);
        result.put("code",200);
        result.put("msg","保存客户成功");
        return ResponseEntity.ok(result);
    }*/
    @RequestMapping("/login")
    public ResponseEntity<Map<String,Object>> login(HttpServletRequest request,HttpServletResponse response,Customer customer){
        //调用CRM查询
        //查询客户手机是否存在
        Customer cst = WebClient.create(Constants.CRM_URL + "/customerService/checkTelephone?telephone=" + customer.getTelephone()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if (cst==null){
            result.put("code",400);
            result.put("msg","账户不存在");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else {
            //判断密码是否正确
            if (!customer.getPassword().equals(cst.getPassword())){
                result.put("code",400);
                result.put("msg","密码不正确");
                return ResponseEntity.ok(result);
            }else {
                //是否激活
                if (cst.getType()==null||!cst.getType().equals("1")){
                    result.put("code",400);
                    result.put("msg","账户没有激活，请先激活");
                }else {
                    //登录成功
                    result.put("code",200);
                    //把登录信息保存到session域
                    request.getSession().setAttribute("bos-fore-customer",cst);
                }
            }
        }
        System.out.println("登陆成功！");
        return ResponseEntity.ok(result);
    }
}
