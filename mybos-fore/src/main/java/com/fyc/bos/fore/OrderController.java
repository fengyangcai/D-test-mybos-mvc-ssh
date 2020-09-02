package com.fyc.bos.fore;

import com.fyc.bos.constant.Constants;
import com.fyc.bos.entity.base.Area;
import com.fyc.bos.entity.take_delivery.Order;
import com.fyc.crm.pojo.Customer;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/order")
public class OrderController {
    //高并发的安全的hashMap类
    private Map<String,Object> map = new ConcurrentHashMap();

    @RequestMapping("/saveOrder")
    public ResponseEntity<Map<String, Object>> saveOrder(HttpServletRequest request,Order order) {

        String sendAreaInfo = request.getParameter("sendAreaInfo");
        String recAreaInfo = request.getParameter("recAreaInfo");
        //获取客户的ID
        //从session中获取登录客户
        Customer loginCust = (Customer) request.getSession().getAttribute("bos-fore-customer");

        if (loginCust == null) {
            map.put("code", 400);
            map.put("msg", "下单前请先登录");
        } else {
            try {
                //订单关联客户
                order.setCustomerId(loginCust.getId());
                //接收寄件人的区域信息，转换为Area对象
                if (sendAreaInfo!=null){
                    String[] sendArray = sendAreaInfo.split("/");
                    Area sendArea = new Area();
                    sendArea.setProvince(sendArray[0]);
                    sendArea.setCity(sendArray[1]);
                    sendArea.setDistrcit(sendArray[2]);
                    //订单关联SendArea
                    order.setSendArea(sendArea);

                }
                //接收收件人的区域信息，转换为Area对象
                if(recAreaInfo!=null){
                    String[] recAreaArray = recAreaInfo.split("/");
                    Area recArea = new Area();
                    recArea.setProvince(recAreaArray[0]);
                    recArea.setCity(recAreaArray[1]);
                    recArea.setDistrcit(recAreaArray[2]);

                    //订单关联recArea
                    order.setRecArea(recArea);
                }

                //调用BOS后台系统，发送Order对象
                WebClient.create(Constants.BOS_BACK_URL+"/orderService/saveOrder").type(MediaType.APPLICATION_JSON).post(order);

                map.put("200",true);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("400",false);
                map.put("msg",e.getMessage());
            }

        }

        //使用webService远程调用第三方接口实现
       // Response post = WebClient.create(Constants.BOS_BACK_URL + "/orderService/saveOrder").type(MediaType.APPLICATION_JSON).post(Order.class);

        return ResponseEntity.ok(map);
    }
}
