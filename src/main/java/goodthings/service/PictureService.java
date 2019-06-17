package goodthings.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import goodthings.bean.GoodsCategory;
import goodthings.dao.PictureDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class PictureService {
    @Autowired
    private PictureDao pictureDao;
    @Value("${upload.url}")
    private String uploadUrl;
    @Value("${upload.filepath}")
    private String uploadPath;

    private static Set<String> formatSet = Sets.newHashSet();

    static {
        formatSet.add(".jpg");
        formatSet.add(".png");
    }

    private synchronized String generateImageId(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()) + new Random().nextInt(899) + 100;//随机生成100到900之间的数
    }
    private String getFormat(String fileName) {
        String format =  fileName.substring(fileName.lastIndexOf("."));
        if(!formatSet.contains(format)) {
            throw new RuntimeException("请使用格式为.jpg或.png的图片");
        }
        return format;
    }

    public String uploadPicture(MultipartFile file, int categoryId) {
        if(!file.isEmpty()){
            try {
                // 文件保存路径
                String subDir = GoodsCategory.getName(categoryId);
                String fullDir = FilenameUtils.concat(uploadPath, subDir);
                FileUtils.forceMkdir(new File(fullDir));
                String fileName = generateImageId()+getFormat(file.getOriginalFilename());
                String fileFullName = FilenameUtils.concat(fullDir, fileName);
                // 转存文件
                byte[] bytes = file.getBytes();
                file.transferTo(new File(fileFullName));
                String httpPath = uploadUrl + subDir + "/" + fileName;
                pictureDao.insertPicture(categoryId, httpPath, bytes);
                return httpPath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
