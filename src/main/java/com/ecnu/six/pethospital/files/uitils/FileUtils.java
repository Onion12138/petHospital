package com.ecnu.six.pethospital.files.uitils;

import com.ecnu.six.pethospital.disease.service.FileService;
import com.ecnu.six.pethospital.files.UploadInfo;
import com.ecnu.six.pethospital.files.controller.BigFileUploadController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Slf4j
public class FileUtils {

    private final static ConcurrentHashMap<String, Set<UploadInfo>> uploadInfoMap = new ConcurrentHashMap<>();


    /**
     * 看看是不是图片文件，是的话，还分块啥啊
     * @param tempFile
     * @return
     * @throws Exception
     */
    public static boolean isImage(Object tempFile) {
        try {
            return null != ImageIO.createImageInputStream(tempFile);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是不是所有块已经上传，是则删了所有的相关chunk信息
     * @param md5
     * @param chunks
     * @return
     */
    public static boolean isAllUploaded(@NotNull final String md5,
                                        @NotNull final String chunks) {
        int size = uploadInfoMap.getOrDefault(md5, new HashSet<>()).size();

        boolean ifAllDone = (size == Integer.parseInt(chunks));
        if (ifAllDone) {
            uploadInfoMap.remove(md5);
        }
        return ifAllDone;
    }

    /**
     * 如果完成上传则
     * @param md5         MD5
     * @param chunk       文件分块序号
     * @param chunks      文件分块总数
     * @param fileName    文件名
     * @param ext         文件后缀名【区分是不是符合条件的视频文件，暂时没必要做】
     * @param fileService fileService
     */
    public static String Uploaded(@NotNull final String md5,
                                @NotNull final String chunk,
                                @NotNull final String chunks,
                                @NotNull final String uploadFolderPath,
                                @NotNull final String fileName,
                                @NotNull final String ext,
                                @NotNull final FileService fileService)
            throws Exception {
        Set<UploadInfo> uploadInfos = uploadInfoMap.getOrDefault(md5, new HashSet<>());
        uploadInfos.add(new UploadInfo(md5, chunks, chunk, uploadFolderPath, fileName, ext));
        uploadInfoMap.put(md5, uploadInfos);
        // 每次调用一下
        boolean allUploaded = isAllUploaded(md5, chunks);
        int chunksNumber = Integer.parseInt(chunks);

        if (allUploaded) {
            mergeFile(chunksNumber, ext, fileName, uploadFolderPath);
            return fileService.uploadFile(new FileInputStream(new java.io.File(uploadFolderPath + fileName + ext)), fileName);
        }
        return String.valueOf(Integer.parseInt(chunk) + 1);
    }

    /**
     * @param chunksNumber
     * @param ext
     * @param fileName 合成真实文件
     * @param uploadFolderPath
     * @throws Exception
     */
    public static void mergeFile(final int chunksNumber,
                                 @NotNull final String ext,
                                 @NotNull final String fileName,
                                 @NotNull final String uploadFolderPath)
            throws Exception {
        /*合并输入流*/
        String mergePath = uploadFolderPath + fileName + "/";
        SequenceInputStream s ;
        InputStream s1 = new FileInputStream(mergePath + 0 + ext);
        InputStream s2 = new FileInputStream(mergePath + 1 + ext);
        s = new SequenceInputStream(s1, s2);
        for (int i = 2; i < chunksNumber; i++) {
            InputStream s3 = new FileInputStream(mergePath + i + ext);
            s = new SequenceInputStream(s, s3);
        }
        //通过输出流向文件写入数据
        saveStreamToFile(s, uploadFolderPath + fileName + ext);

        //删除保存分块文件的文件夹
        deleteTempFolder(mergePath);
    }

    /**
     * 删除指定临时文件夹
     * @param folderPath 文件夹路径
     * @return 是否删除成功
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean deleteTempFolder(@NotNull final String folderPath) {
        java.io.File dir = new java.io.File(folderPath);
        java.io.File[] files = dir.listFiles();
        if (files != null) {
            for (java.io.File file : files) {
                try {
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return dir.delete();
    }

    /**
     * 从stream中保存文件
     *
     * @param inputStream inputStream
     * @param filePath    保存路径
     * @throws Exception 异常 抛异常代表失败了
     */
    public static void saveStreamToFile(@NotNull final InputStream inputStream,
                                        @NotNull final String filePath)
            throws Exception {
        /*创建输出流，写入数据，合并分块*/
        OutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush(); // 同步不断刷盘
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }

    private static final java.io.File uploadDirectory = new java.io.File(getRealPath());
    /**
     * 写分块文件
     * @param mergePath
     * @param chunkFileName
     * @param file
     * @return
     * @throws Exception
     */
    public static boolean saveFile(@NotNull final String mergePath,
                                   @NotNull final String chunkFileName,
                                   @NotNull final MultipartFile file)
            throws Exception {
        byte[] data = readInputStream(file.getInputStream());
        //new一个文件对象用来保存文件，默认保存当前工程根目录
        java.io.File uploadFile = new java.io.File(mergePath + chunkFileName);
        //判断文件夹是否存在，不存在就创建一个
        java.io.File fileDirectory = new java.io.File(mergePath);
        synchronized (uploadDirectory){
            if(!uploadDirectory.exists()){
                if(!uploadDirectory.mkdir()){
                    throw new Exception("保存文件的父文件夹创建失败！路径为：" + mergePath);
                }
            }
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new Exception("文件夹创建失败！路径为：" + mergePath);
                }
            }
        }

        //创建输出流
        try (FileOutputStream outStream = new FileOutputStream(uploadFile)) {//写入数据
            outStream.write(data);
            outStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return uploadFile.exists();
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * 获得绝对路径
     * @return
     */
    public static String getRealPath() {
        return  BigFileUploadController.class.getResource("/").getFile();
    }
}
