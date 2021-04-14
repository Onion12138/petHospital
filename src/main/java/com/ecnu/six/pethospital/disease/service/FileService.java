package com.ecnu.six.pethospital.disease.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author onion
 * @date 2021/4/7 -1:15 下午
 */
public interface FileService {
    String uploadFile(MultipartFile file);

    String uploadFile(InputStream stream, String fileName);

    @Deprecated
    boolean judgeMd5OfBigFile(String md5, Integer idx);

}
