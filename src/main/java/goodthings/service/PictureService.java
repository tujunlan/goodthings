package goodthings.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import goodthings.bean.GoodsCategory;
import goodthings.dao.PictureDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private String address = "D:/image";

    private static final long MAXSIZE = 1048576;

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

    private File createFile(int categoryId, String fileName) {
        //目录结构 例如 D:/image/HUNAN/123.jpg
        String category = GoodsCategory.getName(categoryId);
        String relativePath = category + File.pathSeparator + fileName;
        String fileFullName = FilenameUtils.concat(address, relativePath);
        File file = new File(fileFullName);
        if (!file.getParentFile().exists()) {
            //创建多级目录
            if (!file.getParentFile().mkdirs()) {
                throw new RuntimeException("创建文件失败");
            }
        }
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    return file;
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("创建文件失败");
            }
        }
        return file;
    }
    public String uploadPicture(HttpServletRequest request,CommonsMultipartFile file, int categoryId) {
        if(!file.isEmpty()){
            try {
                // 文件保存路径
                String category = GoodsCategory.getName(categoryId);
                String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"+category;
                String fileName = generateImageId()+getFormat(file.getOriginalFilename());
                String fileFullName = FilenameUtils.concat(filePath, fileName);
                // 转存文件
                file.transferTo(new File(fileFullName));
                pictureDao.insertPicture(categoryId, filePath, file.getBytes());
                return filePath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
