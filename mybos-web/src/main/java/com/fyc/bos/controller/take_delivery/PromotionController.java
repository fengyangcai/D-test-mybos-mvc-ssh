package com.fyc.bos.controller.take_delivery;

import com.fyc.bos.controller.BaseController;
import com.fyc.bos.entity.take_delivery.Promotion;
import com.fyc.bos.service.take_delivery.PromotionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: fyc
 * @Date: 2020/5/6 20:45
 */
@Controller
@RequestMapping("/promotion")
public class PromotionController extends BaseController<Promotion> {

    private PromotionService promotionService;

    @Resource
    public void setPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
        super.setBaseService(promotionService);
    }

    @RequestMapping("/savePromotion")
    public ResponseEntity<Void> savaPromotion(HttpServletRequest request, HttpServletResponse response, @RequestParam("titleImgFile") MultipartFile titleImgFile) throws IOException {
        try {
            //获取所有的promotion的参数
            Promotion promotion = new Promotion();
            String id = request.getParameter("id");
            if (id != null && !id.trim().equals("")) {
                promotion.setId(Integer.valueOf(id));
            }
            promotion.setTitle(request.getParameter("title"));
            promotion.setActiveScope(request.getParameter("activeScope"));
            String startDateStr = request.getParameter("startDate");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String endDateStr = request.getParameter("endDate");

            Date startDate = simpleDateFormat.parse(startDateStr);
            Date endDate = simpleDateFormat.parse(endDateStr);
            promotion.setStartDate(startDate);
            promotion.setEndDate(endDate);
            promotion.setDescrption(request.getParameter("description"));
            //先判断用户是否选择了图片
            if (titleImgFile.getSize()!=0) {
                //处理图片：保存到项目的跟目录下的upload下
                //获取到的是绝对路径D:\idea-project。。。
                String realPath = request.getSession().getServletContext().getRealPath("/upload");
                //生成随机文件名称
                String uuid = UUID.randomUUID().toString();
                //获取这个文件的名称//05_small.jpg
                String originalFilename = titleImgFile.getOriginalFilename();
                //获取后缀名
                String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileName = uuid + fileType;
                //这个方法不行FileUtils.copyFile(titleImgFile,new File(realPath+"/"+fileName));
                //访问的路径
                titleImgFile.transferTo(new File(realPath + "/" + fileName));
                //项目的相对路径的获取
                //获取项目名称/mybos-web
                String contextPath = request.getContextPath();
                //获取获取协议
                String scheme = request.getScheme();
                // localhost 获取ip
                String serverName = request.getServerName();
                //获取端口
                int serverPort = request.getServerPort();
                String filePath = scheme + "://" + serverName + ":" + serverPort + contextPath + "/upload/" + fileName;
                promotion.setTitleImg(filePath);
            } else {
                //用户编辑时没有选择图片，用回以前的图片
                Promotion promotion1 = promotionService.findById(Integer.valueOf(id));
                promotion.setTitleImg(promotion1.getTitleImg());
            }
            //设置信息状态 ：1：有效 0：失效
            promotion.setStatus("1");
            promotionService.save(promotion);
        } catch (Exception e) {
            e.printStackTrace();
            //返回500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(null);
    }

    @RequestMapping("/listByPage")
    public ResponseEntity<Map<String, Object>> listByPage(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(value = "rows", defaultValue = "20") Integer rows, final Promotion promotion) {

        PageRequest pageRequest = new PageRequest(page-1,rows);

        //创建Specification
        Specification<Promotion> specification = new Specification<Promotion>() {
                ArrayList<Predicate> specList = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<Promotion> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (promotion.getTitle() != null && !promotion.getTitle().trim().equals("")) {

                   specList.add(cb.like(root.<String>get("title").as(String.class),"%"+promotion.getTitle()+"%"));
                }

                Predicate[] preArry = new Predicate[specList.size()];
                Predicate predicate = cb.and(specList.toArray(preArry));
                return predicate;
            }
        };
        Page<Promotion> pageBean = promotionService.findAll(specification, pageRequest);
      //  List<Promotion> all = promotionService.findAll();
        long totalElements = pageBean.getTotalElements();
        List<Promotion> content = pageBean.getContent();
        result.put("rows",content);
        result.put("total",totalElements);
        return ResponseEntity.ok(result);
    }


}
