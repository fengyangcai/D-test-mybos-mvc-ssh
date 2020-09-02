package com.fyc.bos.controller.pdf;


import com.fyc.bos.entity.base.Area;
import com.fyc.bos.service.base.AreaService;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pdf")
public class PdfExportController {

    @Resource
    private AreaService areaService;

    //这种方法真的垃圾。样式设置一堆设置太麻烦，还是前端来控制来得实在
    @RequestMapping("/pdfExport")
    public  void pdfExport(HttpServletRequest request, HttpServletResponse response) throws Exception {


        Specification<Area> specification = new Specification<Area>() {

            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> arrayList = new ArrayList<>();

               /* if(area.getProvince()!=null && !area.getProvince().trim().equals("")){
                    arrayList.add( cb.like(root.get("province").as(String.class), "%"+area.getProvince()+"%") );
                }

                if(area.getCity()!=null && !area.getCity().trim().equals("")){
                    arrayList.add( cb.like(root.get("city").as(String.class), "%"+area.getCity()+"%") );
                }

                if(area.getDistrcit()!=null && !area.getDistrcit().trim().equals("")){
                    arrayList.add( cb.like(root.get("district").as(String.class), "%"+area.getDistrcit()+"%") );
                }*/
                Predicate[] predicates = new Predicate[arrayList.size()];
                return cb.and(arrayList.toArray(predicates));
            }
        };
        //查询添加符合的数据
        List<Area> areaList = areaService.findAll(specification);

        //设置弹出框（content-disposition)
        response.setHeader("content-disposition","attachment;filename=area.pdf");

        //2.导入pdf
        //2.1 创建Document
        Document document = new Document();
        //2.2 指定输入位置（响应）
        PdfWriter.getInstance(document,response.getOutputStream());
        //2.3 打开
        document.open();
        //2.4 写出表格
        Table table = new Table(7, areaList.size());
        //换成自己电脑有的字体
        BaseFont bfHei = BaseFont.createFont("C:\\Windows\\Fonts\\stxihei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_CACHED);
        Font font = new Font(bfHei, 12);
        for(Area area :areaList){
            table.addCell(new Paragraph(area.getAreacode(), font));
            table.addCell(new Paragraph(area.getProvince(), font));
            table.addCell(new Paragraph(area.getCity(), font));
            table.addCell(new Paragraph(area.getDistrcit(), font));
            table.addCell(new Paragraph(area.getPostcode(), font));
            table.addCell(new Paragraph(area.getShortcode(), font));
            table.addCell(new Paragraph(area.getCitycode(), font));
        }
        document.add(table);
        //2.5 关闭
        document.close();



    }
}
