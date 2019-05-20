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
    private String getFormat(FileItem item) {
        long size = item.getSize();
        //设定上传的最大值1MB, 1024*1024
        if(size > MAXSIZE) {
            throw new RuntimeException("图片过大, 请使用小于1MB的图片上传");
        }
        String fileName = item.getName();
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
    public List<String> uploadPicture(HttpServletRequest request,int categoryId) {
        List<String> uploadPath = Lists.newArrayList();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list) {
                if(!item.isFormField()) {
                    String suffix = getFormat(item);
                    inputStream = item.getInputStream();
                    byte[] buffer = new byte[1024];
                    File file = createFile(categoryId, generateImageId() + suffix);
                    fileOutputStream = new FileOutputStream(file);
                    int len = 0;
                    while((len = inputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    String filePath = file.getPath().replace(address, "");
                    pictureDao.insertPicture(categoryId, filePath, item.get());
                    uploadPath.add(filePath);
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("服务器繁忙请稍后在试");
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                    if(fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return uploadPath;
    }
}
