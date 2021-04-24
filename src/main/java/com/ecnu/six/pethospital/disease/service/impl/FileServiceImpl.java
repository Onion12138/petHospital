package com.ecnu.six.pethospital.disease.service.impl;

import com.ecnu.six.pethospital.disease.service.FileService;
import com.ecnu.six.pethospital.oauth.utils.Pair;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author onion
 * @date 2021/4/7 -1:16 下午
 */
@Service("fileService")
public class FileServiceImpl implements FileService {
    @Value("${qiniu.access-key}")
    private String accessKey;
    @Value("${qiniu.secret-key}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("2592000")
    private long expireInSeconds;

    private static ConcurrentHashMap<String, Pair<Integer, Integer>> bigFileCache;

    static {
        bigFileCache = new ConcurrentHashMap<>();
        bigFileCache = null;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        InputStream fileInputStream = null;
        try {
            fileInputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        String key = LocalDateTime.now().toString() + "/" + file.getOriginalFilename();
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(fileInputStream, key, upToken, null, null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            throw new RuntimeException("上传失败");
        }
        return getFileUrl(key, accessKey, secretKey, expireInSeconds);
    }

    private String getFileUrl(String filename, String accessKey, String secretKey, Long expireInSeconds){
        String domainOfBucket = "http://ecnuonion.club";
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(filename, "utf-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.privateDownloadUrl(publicUrl, expireInSeconds);
    }

    @Override
    public String uploadFile(File file, String fileName) throws IOException {
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), fileName,
                ContentType.APPLICATION_OCTET_STREAM.toString() ,new FileInputStream(file));
        return uploadFile(multipartFile);
//        return doUpload(stream, fileName);
    }


    private String doUpload(InputStream stream, String fileName) throws IOException {
        String key = LocalDateTime.now().toString() + "/" + fileName;
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        byte[] byteData = IOUtils.toByteArray(stream);
        try {
            Response response = uploadManager.put(byteData, key, upToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            throw new RuntimeException("上传失败");
        }
        return getFileUrl(key, accessKey, secretKey, expireInSeconds);
    }

    @Override
    @Deprecated
    public boolean judgeMd5OfBigFile(String md5, Integer want2Add) {
        Pair<Integer, Integer> pair = bigFileCache.getOrDefault(md5, null);
        if (null == pair) {
            return false;
        }
        if (pair.getLeft() >= want2Add || pair.getRight() < want2Add) {
            return false;
        }
        return true;
    }
}
