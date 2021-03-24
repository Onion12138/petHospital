package com.ecnu.six.pethospital.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author onion
 * @date 2021/3/22 -8:18 上午
 */
@Data
@AllArgsConstructor
public class ResponseData {
    private Integer code;
    private String msg;
    private Object data;

    public static ResponseData success() {
        return new ResponseData(200, "请求成功", null);
    }

    public static ResponseData success(Object data) {
        return new ResponseData(200, "请求成功", data);
    }

    public static ResponseData fail(Object data) {
        return new ResponseData(200, "请求成功", data);
    }

    public static ResponseData fail(String msg) {
        return new ResponseData(500, msg, null);
    }


}
