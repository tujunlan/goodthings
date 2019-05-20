package goodthings.service;

import com.google.common.collect.Sets;
import goodthings.bean.GoodsCategory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

@Service
public class PictureService {
    private String address = "D:/image/";

    private static final long MAXSIZE = 1048576;

    private static Set<String> formatSet = Sets.newHashSet();

    static {
        formatSet.add(".jpg");
        formatSet.add(".png");
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
    private File createFile(String athleteIdCard, String format,int categoryId) {
        //目录结构 address+省份+运动员id.jpg(图片名) 例如 D:/image/HUNAN/123.jpg
        String team = GoodsCategory.getName(categoryId);
        String fileName = address + team + "/" + athleteIdCard + format;
        File file = new File(fileName);
        if(!file.getParentFile().exists()) {
            //创建多级目录
            if(!file.getParentFile().mkdirs()) {
                throw new RuntimeException("创建文件失败");
            }
        }
        if(!file.exists()) {
            try {
                if(file.createNewFile()) {
                    return file;
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("创建文件失败");
            }
        }
        return file;
    }
    public void uploadPicture(HttpServletRequest request,int categoryId) {
        String number = "123";
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list) {
                if(!item.isFormField()) {
                    String format = getFormat(item);
                    inputStream = item.getInputStream();
                    byte[] buffer = new byte[1024];
                    File file = createFile(number, format, categoryId);
                    fileOutputStream = new FileOutputStream(file);
                    int len = 0;
                    while((len = inputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, len);
                    }
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
    }
}
