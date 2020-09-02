package com.fyc.bos.fore;

import com.fyc.bos.constant.Constants;
import com.fyc.bos.entity.take_delivery.PageBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/promotion")
public class PromotionController {
        private static Map<String,Object> map =new HashMap<String,Object>();

    @RequestMapping("/queryByPage")
    public ResponseEntity<Map<String,Object>> queryByPage(@RequestParam(value = "pageIndex",defaultValue = "0")Integer page,@RequestParam(value = "pageSize",defaultValue = "4")Integer pageSize){
        //远程调用bos后台的系统
        PageBean pageBean = WebClient.create(Constants.BOS_BACK_URL + "/promotionService/queryByPage?page="+page+"&pageSize="+pageSize).accept(MediaType.APPLICATION_JSON).get(PageBean.class);
        map.put("content",pageBean.getContent());
        map.put("total",pageBean.getTotalCount());

       return  ResponseEntity.ok(map);

    }
}
