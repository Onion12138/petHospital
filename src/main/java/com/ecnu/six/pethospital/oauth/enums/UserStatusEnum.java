package com.ecnu.six.pethospital.oauth.enums;

import lombok.Getter;

/**
 * @author LEO D PEN
 * @date 2021
 * @desc
 * @since
 */
@Getter
public enum  UserStatusEnum {

    ACTIVE(1, "可用状态"),
    FORBID(0, "被禁用状态"),;

    private final Byte code;

    private final String desc;

    UserStatusEnum(Integer code, String desc) {
        this.code = code.byteValue();
        this.desc = desc;
    }
}
