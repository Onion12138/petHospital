package com.ecnu.six.pethospital.files.controller;

import com.ecnu.six.pethospital.common.ResponseData;
import com.ecnu.six.pethospital.disease.service.FileService;
import com.ecnu.six.pethospital.files.uitils.FileUtils;
import com.ecnu.six.pethospital.oauth.annotation.LoginRequired;
import com.ecnu.six.pethospital.oauth.enums.Role;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */

@RestController
@RequestMapping("/bigFile")
public class BigFileUploadController {

    @Resource
    private FileService fileService;


    @Deprecated
    private boolean isMd5Exist(String md5, String chunkId) {
        try {
            // 分块状态是否合规
            return fileService.judgeMd5OfBigFile(md5, Integer.valueOf(chunkId));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param md5        客户端生成md5值
     * @param chunks           分块数
     * @param chunk            分块序号(从0开始上传，比如chunks为10，那么序号为[0,9])
     * @param pureName             上传文件名【不加后缀名】
     * @param ext              文件后缀名
     * @param size             文件大小
     * @param file             文件本身
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/BigFileUpload", method = RequestMethod.POST)
    @LoginRequired(role = Role.ADMIN)
    public ResponseData fileUpload(@RequestParam("md5") String md5, // 注意md5是前端去算的
                                   @RequestParam("chunks") String chunks, //分块总数
                                   @RequestParam("chunk") String chunk, // chunk id
                                   @RequestParam("pureName") String fileName, // 文件名【不加后缀的】
                                   @RequestParam("ext") String ext, // 文件后缀名
                                   @RequestParam("size") int size, // 总大小，可以不传
                                   @RequestParam("file") MultipartFile file) { // 文件
        String fileChunkName;
        try {
            if (FileUtils.isImage(file)) return ResponseData.fail("图片类文件不适用");
            int index;
            // 根文件夹位置
            String uploadFolderPath = FileUtils.getRealPath();
            String mergePath =uploadFolderPath + fileName + "/";

            //判断文件是否分块
            if (chunks != null && chunk != null) {
//                if (!isMd5Exist(md5, chunk)) {
//                    return ResponseData.fail("分块有误");
//                }
                index = Integer.parseInt(chunk);
                fileChunkName = index + ext; // chunkId + ext
                // 将文件分块保存到临时文件夹里，便于之后的合并文件
                FileUtils.saveFile(mergePath, fileChunkName, file);
                // 验证所有分块是否上传成功，成功的话进行合并
                String result = FileUtils.Uploaded(md5, chunk, chunks, uploadFolderPath, fileName, ext, fileService);
                return ResponseData.success(result);
            } else {
                return ResponseData.fail("你在分啥块啊。。。");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseData.fail("分块上传失败");
        }
    }
}
