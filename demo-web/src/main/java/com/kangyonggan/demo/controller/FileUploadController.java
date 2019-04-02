package com.kangyonggan.demo.controller;

import com.kangyonggan.demo.annotation.PermissionLogin;
import com.kangyonggan.demo.dto.Response;
import com.kangyonggan.demo.util.FileUpload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author kangyonggan
 * @since 2019/4/2 0002
 */
@RestController
@RequestMapping("file/upload")
@Api(tags = "FileUploadController", description = "文件上传接口")
public class FileUploadController extends BaseController {

    @Value("${app.file-root-path}")
    private String fileRootPath;

    @Autowired
    private FileUpload fileUpload;

    @PostMapping
    @ApiOperation("文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件（放在FormData中）", required = true)
    })
    @PermissionLogin
    public Response upload(@RequestParam("file") MultipartFile file) throws FileUploadException {
        Response response = successResponse();
        String filePath = "/common/";
        String fileName = fileUpload.upload(fileRootPath + filePath, file, "AVATAR");

        response.put("fileName", "/upload" + filePath + fileName);
        return response;
    }

}
