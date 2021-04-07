package com.ecnu.six.pethospital.disease.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author onion
 * @date 2021/4/7 -1:15 下午
 */
public interface FileService {
    String uploadFile(MultipartFile file);
}
