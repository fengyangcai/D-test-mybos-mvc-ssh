package com.fyc.bos.controller.take_delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: fyc
 * @Date: 2020/5/7 4:51
 */
@Controller
@RequestMapping("/image")
public class ImageController {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    protected static Map<String, Object> result = new HashMap<String, Object>();

    @RequestMapping("/uploadImage")
    public ResponseEntity<Map<String, Object>> saveImage(HttpServletRequest request, HttpServletResponse response, @RequestParam("titleImgFile") MultipartFile titleImgFile) throws IOException {
        try {
            //获取绝对路径
            // String servletPath = request.getRealPath("/upload");
            String realPath = request.getSession().getServletContext().getRealPath("/upload");
            //生成唯一的随机文件名称
            String uuid = UUID.randomUUID().toString();
            String originalFilename = titleImgFile.getOriginalFilename();//这个获取的是文件完整名称
            //获取文件的后缀名
            //使用阿里的common工具类获取文件的扩展名
            //或者 String extension=originalFilename.substring(originalFilename.lastIndexOf("."));
            String extName = FilenameUtils.getExtension(originalFilename);//获取到后缀名了
            String fileName = uuid + extName;
            titleImgFile.transferTo(new File(realPath+"/"+fileName));

            //成功
            result.put("error",0);
            //上传后的文件路径
            result.put("url",request.getContextPath()+"/upload/"+fileName);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("error", 1);
            result.put("message", e.getMessage());

        }
        return ResponseEntity.ok(result);
    }

    /*
     * 管理图片的存放地址
     * */
    @RequestMapping("manager")
    public ResponseEntity<List<Hashtable>> manager(HttpServletRequest request) {
        //获取upload的绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        //1.读取目录
        File uploadFile = new File(realPath);
        //图片的扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
        //2.把upload目录的所有文件解析出来
        //遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if (uploadFile.listFiles() != null) {
            //File[] files = file.listFiles();
            for (File file : uploadFile.listFiles()) {
                Hashtable<String, Object> hashtable = new Hashtable<>();
                String fileName = file.getName();
                //如果是一个文件夹
                if (file.isDirectory()) {
                    hashtable.put("is_dir", true);
                    hashtable.put("has_file", (file.listFiles() != null));
                    hashtable.put("filesize", 0L);
                    hashtable.put("is_photo", false);
                    hashtable.put("filetype", "");
                    //如果是一个文件
                } else if (file.isFile()) {
                    //如果是一个文件
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hashtable.put("is_dir", false);
                    hashtable.put("has_file", false);
                    hashtable.put("filesize", file.length());
                    hashtable.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hashtable.put("filetype", fileExt);
                }
                hashtable.put("filename", fileName);
                //lastModified()返回此抽象路径名表示的文件上次修改的时间。
                hashtable.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hashtable);
            }
        }
        return ResponseEntity.ok(fileList);
    }

}
