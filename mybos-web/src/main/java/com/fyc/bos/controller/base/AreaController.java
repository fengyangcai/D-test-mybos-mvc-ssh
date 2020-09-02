package com.fyc.bos.controller.base;

import com.fyc.bos.controller.BaseController;
import com.fyc.bos.entity.base.Area;
import com.fyc.bos.service.base.AreaService;
import com.fyc.bos.utils.ImportExcelUtil;
import com.fyc.bos.utils.PinYin4jUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: fyc
 * @Date: 2020/4/29 14:44
 */
@Controller
@RequestMapping("/base/area")
public class AreaController extends BaseController<Area> {
    //在需要使用日志的地方加上这句代码即可上面的Lombak的插件使用@Slf4j就直接使用log
    private static Logger logger = Logger.getLogger(AreaController.class);

    private AreaService areaService;
    @Resource
    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
        super.setBaseService(areaService);
    }
    @RequestMapping("/excelImport")
    public ResponseEntity<Map<String, Object>> excelImport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            MultipartHttpServletRequest multipartResolver= (MultipartHttpServletRequest) request;
            InputStream inputStream =null;
            List<List<Object>> listob =null;
            MultipartFile file = multipartResolver.getFile("areaFile");
            if (file.isEmpty()){
                throw new Exception("文件不存在！");
            }
            inputStream =file.getInputStream();
            listob= new ImportExcelUtil().getBankListByExcel(inputStream,file.getOriginalFilename());
            //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
            for (int i = 0; i < listob.size(); i++) {
                 //获取列的数据
                List<Object> list = listob.get(i);
                Area area =new Area();
                area.setAreacode(String.valueOf(list.get(0)));
                String province =String.valueOf(list.get(1));
                area.setProvince(province);
                String city =String.valueOf(list.get(2));
                area.setCity(city);
                String district=String.valueOf(list.get(3));
                area.setDistrcit(district);
                area.setPostcode(String.valueOf(list.get(4)));
                //使用Pinyin4j来生成区域简码和城市编码
                //生成区域简码
                province = province.substring(0,province.length()-1);
                city = city.substring(0,city.length()-1);
                district = district.substring(0,district.length()-1);
                //province+city+district
                String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
                String shortcode = PinYin4jUtils.stringArrayToString(headByString);
                area.setShortcode(shortcode);

                //生成城市编码
                String citycode = PinYin4jUtils.hanziToPinyin(city, "");
                area.setCitycode(citycode);

                list.add(area);

               areaService.save(area);
            }
            result.put("code",200);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            //result.put("code",400);
            e.printStackTrace();
        }
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     *
     */
    @RequestMapping("/excelExport")
    public void excelExport( HttpServletResponse response,Area area) throws IOException {
        logger.info("*******logger开始到处excel");

        //根据条件查询区域的数据
        List<Area> list = getList(area);
        //2.根据区域数据写出excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("area");
        //创建表头
        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("编号");
        header.createCell(1).setCellValue("省份");
        header.createCell(2).setCellValue("城市");
        header.createCell(3).setCellValue("区（县)");
        header.createCell(4).setCellValue("邮编");
        header.createCell(5).setCellValue("区域简码");
        header.createCell(6).setCellValue("城市编码");
        for (int i = 0; i < list.size(); i++) {
            logger.info(list.size()+"--:"+list.toString());
            Area area1 = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(area1.getAreacode());
            row.createCell(1).setCellValue(area1.getProvince());
            row.createCell(2).setCellValue(area1.getCity());
            row.createCell(3).setCellValue(area1.getDistrcit());
            row.createCell(4).setCellValue(area1.getPostcode());
            row.createCell(5).setCellValue(area1.getShortcode());
            row.createCell(6).setCellValue(area1.getCitycode());
        }
        //3.把excel文件写出给用户（浏览器，响应，等用于文件下载）
        response.setHeader("content-disposition","attachment;filename=area.xls");
        workbook.write(response.getOutputStream());
    }



    @RequestMapping("/listByPage")
    public ResponseEntity<Map<String, Object>> listByPage(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "rows", defaultValue = "10")int rows, final Area area) {

        PageRequest pageRequest = new PageRequest(page-1,rows);

        Specification<Area> specification = new Specification<Area>() {

            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> arrayList = new ArrayList<>();

                if(area.getProvince()!=null && !area.getProvince().trim().equals("")){
                    arrayList.add( cb.like(root.get("province").as(String.class), "%"+area.getProvince()+"%") );
                }

                if(area.getCity()!=null && !area.getCity().trim().equals("")){
                    arrayList.add( cb.like(root.get("city").as(String.class), "%"+area.getCity()+"%") );
                }

                if(area.getDistrcit()!=null && !area.getDistrcit().trim().equals("")){
                    arrayList.add( cb.like(root.get("district").as(String.class), "%"+area.getDistrcit()+"%") );
                }
                Predicate[] predicates = new Predicate[arrayList.size()];
                return cb.and(arrayList.toArray(predicates));
            }
        };

        Page<Area> pageBean = areaService.findAll(specification, pageRequest);
         result.put("total",pageBean.getTotalElements());
         result.put("rows",pageBean.getContent());


        return ResponseEntity.ok(result);
    }

    private List<Area> getList( final Area area){

        Specification<Area> spc = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> arrayList = new ArrayList<>();

                if(area.getProvince()!=null && !area.getProvince().trim().equals("")){
                    arrayList.add( cb.like(root.get("province").as(String.class), "%"+area.getProvince()+"%") );
                }

                if(area.getCity()!=null && !area.getCity().trim().equals("")){
                    arrayList.add( cb.like(root.get("city").as(String.class), "%"+area.getCity()+"%") );
                }

                if(area.getDistrcit()!=null && !area.getDistrcit().trim().equals("")){
                    arrayList.add( cb.like(root.get("district").as(String.class), "%"+area.getDistrcit()+"%") );
                }
                Predicate[] predicates = new Predicate[arrayList.size()];
                return cb.and(arrayList.toArray(predicates));
            }
        };

        return  areaService.findAll(spc);
    }
}
