package com.ecnu.six.pethospital.files;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UploadInfo {

    private String md5;
    private String chunks;
    private String chunk;
    private String path; // 同文件下的这个都是一样的【一个文件夹】
    private String fileName; //【这个也应该是一样的】
    private String ext;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadInfo that = (UploadInfo) o;
        return Objects.equals(md5, that.md5) &&
                Objects.equals(chunks, that.chunks) &&
                Objects.equals(chunk, that.chunk) &&
                Objects.equals(path, that.path) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(ext, that.ext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(md5, chunks, chunk, path, fileName, ext);
    }
}
