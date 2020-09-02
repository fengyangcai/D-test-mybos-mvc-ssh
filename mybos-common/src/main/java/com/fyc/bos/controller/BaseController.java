package com.fyc.bos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyc.bos.service.BaseService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: fyc
 * @Date: 2020/4/26 10:57
 */
public abstract class BaseController<T> {

    private static final ObjectMapper MAPPER = new ObjectMapper();



    protected static Map<String, Object> result = new ConcurrentHashMap<String, Object>();

    private T clazz;

    public void setClazz(T clazz) {
        this.clazz = clazz;
    }

    public T getClazz() {
        return clazz;
    }

    public BaseController() {
        //获取子类的泛型父类里面泛型
        //1.获取当前运行的Action子类
        Class clz = this.getClass();

        //2.获取Action子类的泛型父类
        Type type = clz.getGenericSuperclass();

        //3.转换类型
        ParameterizedType pt = (ParameterizedType)type;

        //4.获取泛型父类里面的泛型
        Class modelClass = (Class)pt.getActualTypeArguments()[0];

        //5.创建对象
        try {
            clazz = (T) modelClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*  ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();*/
        /*  this.clazz = (Class<T>) pt.getActualTypeArguments()[0];*/
    }

    private BaseService<T> baseService;

    public void setBaseService(BaseService<T> baseService) {
        this.baseService = baseService;
    }


    //@RequestMapping("/listByPage")
   // public abstract ResponseEntity<Map<String, Object>> listByPage(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "rows", defaultValue = "10") int rows, T model);

        //1.分页查询
      /*  Pageable pageable = new PageRequest(page - 1, rows);
        Specification<T> specification = buildSpecification();


        Page<T> pageBean = baseService.findAll(specification, pageable);
        List<T> list = pageBean.getContent();

        Long total = pageBean.getTotalElements();
        //取出数据
        result.put("total", total);
        result.put("rows", list);

        //这种方式是传回去的是json对象，对IE8以下的版本的浏览器支持不好一般来说，传json的字符串。
        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        //return ResponseEntity.ok(result);
        //writeJson(result,response);
        return ResponseEntity.ok(result);*/


   // protected abstract Specification<T> buildSpecification();

    //如果是字符串返回则是这个字符串的jsp文件。如果加上 @ResponseBody则是返回的是字符串文本。
    @RequestMapping("/list")
    @ResponseBody
    public String getlist(HttpServletRequest request, HttpServletResponse response) {
        String string = null;
        try {
            List<T> list = baseService.findAll();
            //String jsonString = JSON.toJSONString(list);

            string = MAPPER.writeValueAsString(list);
        } catch (Exception e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            System.out.println("系统异常：："+e.getMessage());
        }
        return string;
        //return jsonString;
    }

    //返回的是json的对象application/json;charset=UTF-8
    /**
     * 添加数据
     */
    @RequestMapping("/save")
    public void save(HttpServletResponse response, T model) throws IOException {
        System.out.println("获取model数据");
        //System.out.println(model.toString());
        baseService.save(model);
        try {//logger.info("开始保存的数据： "+model.toString());
            result.put("code", 200);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg",e.getMessage());
        }
        writeJson(result, response);
    }

    @RequestMapping("/list2")
    public ResponseEntity<List<T>> getlist2() {

        //String jsonString = JSON.toJSONString(list);

        String string = null;
        List<T> list;
        try {
           list = baseService.findAll();
            string = MAPPER.writeValueAsString(list);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(200).body(list);

    }

    /*删除数据*/
    @RequestMapping("/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ids = request.getParameter("ids");
        try {
            baseService.delete(ids);
            result.put("code", 200);
        } catch (Exception e) {
            result.put("code", 400);
            result.put("msg", e.getMessage());
        }
        writeJson(result, response);

    }
    /*根据id查询数据*/
    @RequestMapping("/findById")
    public void findById(HttpServletResponse response,@RequestParam(value = "uuid") Integer uuid){

        T base = baseService.findById(uuid);
        try {
            writeJson(base,response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    protected void writeJson(Object o, HttpServletResponse response) throws IOException {
        String valueAsString = MAPPER.writeValueAsString(o);
        //设置输出内容格式和编码
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(valueAsString);
    }


}
