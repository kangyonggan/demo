package com.kangyonggan.demo.util;

import com.kangyonggan.demo.constants.RedisKey;
import com.kangyonggan.demo.service.impl.RedisService;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author kangyonggan
 * @since 2019/4/2 0002
 */
@Component
public final class FileUpload {

    @Autowired
    private RedisService redisService;

    /**
     * 文件上传
     *
     * @param dir
     * @param file
     * @param prefix
     * @return
     * @throws FileUploadException
     */
    public String upload(String dir, MultipartFile file, String prefix) throws FileUploadException {
        try {
            String filePath = getFileName(prefix) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            File desc = getAbsolutePath(dir + filePath);
            file.transferTo(desc);
            return filePath;
        } catch (Exception e) {
            throw new FileUploadException("File Upload Exception", e);
        }
    }

    /**
     * 获取文件名
     *
     * @param prefix
     * @return
     */
    private String getFileName(String prefix) {
        if (StringUtils.isNotEmpty(prefix)) {
            prefix += "_";
        } else {
            prefix = "";
        }

        String nextVal = String.valueOf(redisService.incr(RedisKey.KEY_FILE_NAME));
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        return prefix + currentDate + StringUtils.leftPad(nextVal, 8, "0");
    }

    /**
     * 获取绝对路径，并创建对应的文件夹
     *
     * @param filename
     * @return
     * @throws IOException
     */
    private File getAbsolutePath(String filename) throws IOException {
        File desc = new File(filename);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }

        if (!desc.exists()) {
            desc.createNewFile();
        }

        return desc;
    }

}
