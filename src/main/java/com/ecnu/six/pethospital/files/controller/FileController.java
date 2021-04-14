package com.ecnu.six.pethospital.files.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.disease.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author onion
 * @date 2021/4/7 -1:14 下午
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam MultipartFile file) {
        String uri = fileService.uploadFile(file);
        return ResponseData.success(uri);
    }
}
