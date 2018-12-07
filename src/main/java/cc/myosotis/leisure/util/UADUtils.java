package cc.myosotis.leisure.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * upload and download utils
 */

public class UADUtils {

    public static String upload(MultipartFile multipartFile, String savePath, boolean isNewFileName, Callback callback) {

        String fileName = null;
        // 使用原文件名
        fileName = multipartFile.getOriginalFilename();
        // 生成新文件名
        if (isNewFileName) {
            assert fileName != null : "UploadUtils.upload fileName can not be null";
            fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
            // 去除UUID.randomUUID()生成的符号-
            fileName = fileName.replace("-", "");
        }
        // 文件=路径/文件名
        savePath = savePath + "/" + fileName;
        // 实例化文件对象
        File dest = new File(savePath);
        // 创建不存在的路径
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        //保存文件
        try {
            multipartFile.transferTo(dest);
            return fileName;
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}